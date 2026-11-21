package services;

import models.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FileStorageService {

    private static final File DATA_DIR = new File("data");

    private static final String PLANS_FILE = "plans.csv";
    private static final String USERS_FILE = "users.csv";
    private static final String SESSIONS_FILE = "sessions.csv";
    private static final String PAYMENTS_FILE = "payments.csv";
    private static final String ATTENDANCE_FILE = "attendance.csv";

    private static final String PLANS_HEADER = "planID,planName,price,durationMonths,description";
    private static final String USERS_HEADER = "role,userID,name,email,password,extra1,extra2,extra3";
    private static final String SESSIONS_HEADER = "sessionID,trainerUserID,sessionDate,type,maxCapacity,status,enrolledMemberIDs";
    private static final String PAYMENTS_HEADER = "paymentID,memberUserID,planID,amount,paymentDate,method,status";
    private static final String ATTENDANCE_HEADER = "attendanceID,memberUserID,sessionID,checkInTime,present";

    static {
        DATA_DIR.mkdirs();
    }

    private FileStorageService() {
    }

    public static void savePlans(List<MembershipPlan> plans) {
        List<String> lines = new ArrayList<>();
        for (MembershipPlan p : plans) {
            lines.add(toCsv(p.getPlanID(), p.getPlanName(), p.getPrice(), p.getDurationMonths(), p.getDescription()));
        }
        writeLines(PLANS_FILE, PLANS_HEADER, lines);
    }

    public static List<MembershipPlan> loadPlans() {
        List<MembershipPlan> plans = new ArrayList<>();
        for (List<String> row : readRows(PLANS_FILE, PLANS_HEADER)) {
            try {
                plans.add(new MembershipPlan(
                        Integer.parseInt(get(row, 0)), get(row, 1), Double.parseDouble(get(row, 2)),
                        Integer.parseInt(get(row, 3)), get(row, 4)));
            } catch (RuntimeException e) {
                warnSkipped(PLANS_FILE, row, e);
            }
        }
        return plans;
    }

    public static void saveUsers(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            String extra1 = "", extra2 = "", extra3 = "";
            if (u instanceof Admin) {
                extra1 = String.valueOf(((Admin) u).getAdminID());
            } else if (u instanceof Trainer) {
                Trainer t = (Trainer) u;
                extra1 = String.valueOf(t.getTrainerID());
                extra2 = t.getSpecialization();
                extra3 = t.getSchedule();
            } else if (u instanceof Member) {
                Member m = (Member) u;
                extra1 = String.valueOf(m.getMembershipNumber());
                extra2 = m.getMembershipPlan() == null ? "" : String.valueOf(m.getMembershipPlan().getPlanID());
                extra3 = str(m.getJoinDate()) + "|" + str(m.getExpiryDate()) + "|" + str(m.getStatus());
            }
            lines.add(toCsv(u.getRole(), u.getUserID(), u.getName(), u.getEmail(), u.getPassword(), extra1, extra2, extra3));
        }
        writeLines(USERS_FILE, USERS_HEADER, lines);
    }

    public static List<User> loadUsers(List<MembershipPlan> plans) {
        Map<Integer, MembershipPlan> planById = new HashMap<>();
        for (MembershipPlan p : plans) {
            planById.put(p.getPlanID(), p);
        }

        List<User> users = new ArrayList<>();
        for (List<String> row : readRows(USERS_FILE, USERS_HEADER)) {
            try {
                String role = get(row, 0);
                String userID = get(row, 1), name = get(row, 2), email = get(row, 3), password = get(row, 4);
                String extra1 = get(row, 5), extra2 = get(row, 6), extra3 = get(row, 7);

                if ("Admin".equalsIgnoreCase(role)) {
                    users.add(new Admin(userID, name, email, password, Integer.parseInt(extra1)));
                } else if ("Trainer".equalsIgnoreCase(role)) {
                    users.add(new Trainer(userID, name, email, password, Integer.parseInt(extra1), extra2, extra3));
                } else if ("Member".equalsIgnoreCase(role)) {
                    String[] parts = extra3.split("\\|", -1);
                    String joinStr = parts.length > 0 ? parts[0] : "";
                    String expiryStr = parts.length > 1 ? parts[1] : "";
                    String status = parts.length > 2 && !parts[2].isEmpty() ? parts[2] : "Inactive";

                    MembershipPlan plan = extra2.isEmpty() ? null : planById.get(Integer.parseInt(extra2));
                    LocalDate joinDate = joinStr.isEmpty() ? LocalDate.now() : LocalDate.parse(joinStr);

                    Member member = new Member(userID, name, email, password,
                            Integer.parseInt(extra1), plan, joinDate, status);
                    member.setExpiryDate(expiryStr.isEmpty() ? null : LocalDate.parse(expiryStr));
                    users.add(member);
                } else {
                    throw new IllegalArgumentException("Unknown role: " + role);
                }
            } catch (RuntimeException e) {
                warnSkipped(USERS_FILE, row, e);
            }
        }
        return users;
    }

    public static void saveSessions(List<Session> sessions) {
        List<String> lines = new ArrayList<>();
        for (Session s : sessions) {
            List<String> memberIDs = new ArrayList<>();
            for (Member m : s.getEnrolledMembers()) {
                memberIDs.add(m.getUserID());
            }
            String trainerID = s.getTrainer() == null ? "" : s.getTrainer().getUserID();
            lines.add(toCsv(s.getSessionID(), trainerID, str(s.getSessionDate()), s.getType(),
                    s.getMaxCapacity(), s.getStatus(), String.join(";", memberIDs)));
        }
        writeLines(SESSIONS_FILE, SESSIONS_HEADER, lines);
    }

    public static List<Session> loadSessions(List<User> users) {
        Map<String, User> userById = indexUsers(users);

        List<Session> sessions = new ArrayList<>();
        for (List<String> row : readRows(SESSIONS_FILE, SESSIONS_HEADER)) {
            try {
                User trainerUser = userById.get(get(row, 1));
                Trainer trainer = trainerUser instanceof Trainer ? (Trainer) trainerUser : null;

                Session session = new Session(Integer.parseInt(get(row, 0)), trainer,
                        LocalDateTime.parse(get(row, 2)), get(row, 3), Integer.parseInt(get(row, 4)));
                if (!get(row, 5).isEmpty()) {
                    session.setStatus(get(row, 5));
                }

                for (String memberID : get(row, 6).split(";")) {
                    User u = userById.get(memberID.trim());
                    if (u instanceof Member) {
                        session.getEnrolledMembers().add((Member) u);
                        ((Member) u).getBookedSessions().add(session);
                    }
                }
                sessions.add(session);
            } catch (RuntimeException e) {
                warnSkipped(SESSIONS_FILE, row, e);
            }
        }
        return sessions;
    }

    public static void savePayments(List<Payment> payments) {
        List<String> lines = new ArrayList<>();
        for (Payment p : payments) {
            String memberID = p.getMember() == null ? "" : p.getMember().getUserID();
            String planID = p.getPlan() == null ? "" : String.valueOf(p.getPlan().getPlanID());
            lines.add(toCsv(p.getPaymentID(), memberID, planID, p.getAmount(),
                    str(p.getPaymentDate()), p.getMethod(), p.getStatus()));
        }
        writeLines(PAYMENTS_FILE, PAYMENTS_HEADER, lines);
    }

    public static List<Payment> loadPayments(List<User> users, List<MembershipPlan> plans) {
        Map<String, User> userById = indexUsers(users);
        Map<Integer, MembershipPlan> planById = new HashMap<>();
        for (MembershipPlan p : plans) {
            planById.put(p.getPlanID(), p);
        }

        List<Payment> payments = new ArrayList<>();
        for (List<String> row : readRows(PAYMENTS_FILE, PAYMENTS_HEADER)) {
            try {
                User u = userById.get(get(row, 1));
                if (!(u instanceof Member)) {
                    throw new IllegalArgumentException("Unknown member: " + get(row, 1));
                }
                MembershipPlan plan = get(row, 2).isEmpty() ? null : planById.get(Integer.parseInt(get(row, 2)));

                Payment payment = new Payment(Integer.parseInt(get(row, 0)), (Member) u, plan,
                        Double.parseDouble(get(row, 3)), get(row, 5));
                if (!get(row, 4).isEmpty()) {
                    payment.setPaymentDate(LocalDate.parse(get(row, 4)));
                }
                if (!get(row, 6).isEmpty()) {
                    payment.setStatus(get(row, 6));
                }
                payments.add(payment);
            } catch (RuntimeException e) {
                warnSkipped(PAYMENTS_FILE, row, e);
            }
        }
        return payments;
    }

    public static void saveAttendance(List<Attendance> records) {
        List<String> lines = new ArrayList<>();
        for (Attendance a : records) {
            String memberID = a.getMember() == null ? "" : a.getMember().getUserID();
            String sessionID = a.getSession() == null ? "" : String.valueOf(a.getSession().getSessionID());
            lines.add(toCsv(a.getAttendanceID(), memberID, sessionID, str(a.getCheckInTime()), a.isPresent()));
        }
        writeLines(ATTENDANCE_FILE, ATTENDANCE_HEADER, lines);
    }

    public static List<Attendance> loadAttendance(List<User> users, List<Session> sessions) {
        Map<String, User> userById = indexUsers(users);
        Map<Integer, Session> sessionById = new HashMap<>();
        for (Session s : sessions) {
            sessionById.put(s.getSessionID(), s);
        }

        List<Attendance> records = new ArrayList<>();
        for (List<String> row : readRows(ATTENDANCE_FILE, ATTENDANCE_HEADER)) {
            try {
                User u = userById.get(get(row, 1));
                if (!(u instanceof Member)) {
                    throw new IllegalArgumentException("Unknown member: " + get(row, 1));
                }
                Session session = get(row, 2).isEmpty() ? null : sessionById.get(Integer.parseInt(get(row, 2)));

                Attendance record = new Attendance(Integer.parseInt(get(row, 0)), (Member) u, session,
                        Boolean.parseBoolean(get(row, 4)));
                if (!get(row, 3).isEmpty()) {
                    record.setCheckInTime(LocalDateTime.parse(get(row, 3)));
                }
                records.add(record);
            } catch (RuntimeException e) {
                warnSkipped(ATTENDANCE_FILE, row, e);
            }
        }
        return records;
    }

    private static void writeLines(String fileName, String header, List<String> lines) {
        DATA_DIR.mkdirs();
        File file = new File(DATA_DIR, fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            writer.write(header);
            writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save " + file.getPath() + ": " + e.getMessage());
        }
    }

    private static List<List<String>> readRows(String fileName, String header) {
        List<List<String>> rows = new ArrayList<>();
        File file = new File(DATA_DIR, fileName);
        if (!file.exists()) {
            return rows;
        }
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().equals(header)) {
                    continue;
                }
                rows.add(parseCsvLine(line));
            }
        } catch (IOException e) {
            System.err.println("Could not read " + file.getPath() + ": " + e.getMessage());
        }
        return rows;
    }

    private static String toCsv(Object... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            String v = values[i] == null ? "" : String.valueOf(values[i]);
            v = v.replace("\r", " ").replace("\n", " ");
            if (v.contains(",") || v.contains("\"")) {
                v = "\"" + v.replace("\"", "\"\"") + "\"";
            }
            sb.append(v);
        }
        return sb.toString();
    }

    private static List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else if (c == '"') {
                    inQuotes = false;
                } else {
                    cur.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        fields.add(cur.toString());
        return fields;
    }

    private static String get(List<String> row, int index) {
        return index < row.size() ? row.get(index).trim() : "";
    }

    private static String str(Object value) {
        return value == null ? "" : value.toString();
    }

    private static Map<String, User> indexUsers(List<User> users) {
        Map<String, User> map = new HashMap<>();
        for (User u : users) {
            map.put(u.getUserID(), u);
        }
        return map;
    }

    private static void warnSkipped(String fileName, List<String> row, Exception e) {
        System.err.println("Skipping bad row in " + fileName + " " + row + ": " + e.getMessage());
    }
}
