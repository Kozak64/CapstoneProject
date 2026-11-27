package services;

import exceptions.*;
import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GymSystemManager {

    private static final GymSystemManager instance = new GymSystemManager();

    private final List<MembershipPlan> plans = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Session> sessions = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private final List<Attendance> attendanceRecords = new ArrayList<>();

    private final Random random = new Random();
    private int idCounter = 1;
    private int membershipCounter = 1001;
    private int trainerIdCounter = 201;
    private int planIdCounter = 1;
    private int sessionIdCounter = 101;
    private int paymentIdCounter = 501;
    private int attendanceIdCounter = 1;

    private GymSystemManager() {
        loadAllData();
    }

    public static GymSystemManager getInstance() {
        return instance;
    }

    private void loadAllData() {
        plans.addAll(FileStorageService.loadPlans());
        users.addAll(FileStorageService.loadUsers(plans));

        if (plans.isEmpty() && users.isEmpty()) {
            seedDefaultData();
            saveAll();
        } else {
            sessions.addAll(FileStorageService.loadSessions(users));
            payments.addAll(FileStorageService.loadPayments(users, plans));
            attendanceRecords.addAll(FileStorageService.loadAttendance(users, sessions));
            syncCounters();
        }
    }

    private void seedDefaultData() {
        MembershipPlan basic = new MembershipPlan(planIdCounter++, "Monthly Basic", 1200.0, 1, "Gym floor access");
        MembershipPlan pro = new MembershipPlan(planIdCounter++, "Quarterly Pro", 3200.0, 3, "Gym + group classes");
        MembershipPlan elite = new MembershipPlan(planIdCounter++, "Annual Elite", 11000.0, 12, "All access + PT sessions");
        plans.add(basic);
        plans.add(pro);
        plans.add(elite);

        Admin admin = new Admin("00-1111-222", "System Admin", "admin@gym.com", "admin123", 101);
        Trainer trainer = new Trainer("25-1231-123", "Coach Alex", "trainer@gym.com", "train123", trainerIdCounter++, "Strength & Conditioning", "MWF 8AM-5PM");
        Member member = new Member("25-1234-567", "Juan Dela Cruz", "member@gym.com", "mem123", membershipCounter++, basic, LocalDate.now(), "Active");
        users.add(admin);
        users.add(trainer);
        users.add(member);

        Session s1 = new Session(sessionIdCounter++, trainer, LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0), "Strength & Conditioning", 15);
        Session s2 = new Session(sessionIdCounter++, trainer, LocalDateTime.now().plusDays(2).withHour(16).withMinute(0).withSecond(0).withNano(0), "HIIT Cardio", 15);
        sessions.add(s1);
        sessions.add(s2);
    }

    private void syncCounters() {
        for (MembershipPlan p : plans) {
            if (p.getPlanID() >= planIdCounter) planIdCounter = p.getPlanID() + 1;
        }
        for (User u : users) {
            if (u instanceof Member m && m.getMembershipNumber() >= membershipCounter) {
                membershipCounter = m.getMembershipNumber() + 1;
            } else if (u instanceof Trainer t && t.getTrainerID() >= trainerIdCounter) {
                trainerIdCounter = t.getTrainerID() + 1;
            }
            String[] parts = u.getUserID().split("-");
            if (parts.length == 3) {
                try {
                    int seq = Integer.parseInt(parts[1]);
                    if (seq >= idCounter) idCounter = seq + 1;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        for (Session s : sessions) {
            if (s.getSessionID() >= sessionIdCounter) sessionIdCounter = s.getSessionID() + 1;
        }
        for (Payment p : payments) {
            if (p.getPaymentID() >= paymentIdCounter) paymentIdCounter = p.getPaymentID() + 1;
        }
        for (Attendance a : attendanceRecords) {
            if (a.getAttendanceID() >= attendanceIdCounter) attendanceIdCounter = a.getAttendanceID() + 1;
        }
    }

    private void saveAll() {
        FileStorageService.savePlans(plans);
        FileStorageService.saveUsers(users);
        FileStorageService.saveSessions(sessions);
        FileStorageService.savePayments(payments);
        FileStorageService.saveAttendance(attendanceRecords);
    }

    // --- Authentication & Registration ---

    public String generateUserID() {
        String id;
        do {
            String year = String.format("%02d", Year.now().getValue() % 100);
            String seq = String.format("%04d", idCounter++);
            String rand = String.format("%03d", random.nextInt(1000));
            id = year + "-" + seq + "-" + rand;
        } while (userIDExists(id));
        return id;
    }

    public Member registerMember(String name, String email, String password) throws DuplicateUserException {
        if (emailExists(email)) {
            throw new DuplicateUserException("Email already registered.");
        }
        String userID = generateUserID();
        Member newMember = new Member(
                userID, name, email, password, membershipCounter++, null, LocalDate.now(), "Inactive"
        );
        users.add(newMember);
        FileStorageService.saveUsers(users);
        return newMember;
    }

    public User authenticate(String userID, String password) throws InvalidLoginException {
        for (User user : users) {
            if (user.login(userID, password)) {
                return user;
            }
        }
        throw new InvalidLoginException("Invalid User ID or password.");
    }

    public boolean userIDExists(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) return true;
        }
        return false;
    }

    public boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public User findUserById(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) return user;
        }
        return null;
    }

    // --- Queries ---

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Member> getMembers() {
        List<Member> result = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Member m) result.add(m);
        }
        return result;
    }

    public List<Trainer> getTrainers() {
        List<Trainer> result = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Trainer t) result.add(t);
        }
        return result;
    }

    public List<MembershipPlan> getPlans() {
        return new ArrayList<>(plans);
    }

    public List<Session> getSessions() {
        return new ArrayList<>(sessions);
    }

    public List<Session> getSessionsForTrainer(Trainer trainer) {
        List<Session> result = new ArrayList<>();
        if (trainer == null) return result;
        for (Session s : sessions) {
            if (s.getTrainer() != null && s.getTrainer().getUserID().equalsIgnoreCase(trainer.getUserID())) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Payment> getPayments() {
        return new ArrayList<>(payments);
    }

    public double getTotalCompletedRevenue() {
        double total = 0;
        for (Payment p : payments) {
            if ("Completed".equalsIgnoreCase(p.getStatus())) {
                total += p.getAmount();
            }
        }
        return total;
    }

    public List<Attendance> getAttendanceForMember(Member member) {
        List<Attendance> result = new ArrayList<>();
        if (member == null) return result;
        for (Attendance a : attendanceRecords) {
            if (a.getMember() != null && a.getMember().getUserID().equalsIgnoreCase(member.getUserID())) {
                result.add(a);
            }
        }
        return result;
    }

    public Attendance findAttendance(Member member, Session session) {
        if (member == null || session == null) return null;
        for (Attendance a : attendanceRecords) {
            if (a.getMember() != null && a.getSession() != null
                    && a.getMember().getUserID().equalsIgnoreCase(member.getUserID())
                    && a.getSession().getSessionID() == session.getSessionID()) {
                return a;
            }
        }
        return null;
    }

    private Session findSessionById(int sessionID) {
        for (Session s : sessions) {
            if (s.getSessionID() == sessionID) return s;
        }
        return null;
    }

    private MembershipPlan findPlanById(int planID) {
        for (MembershipPlan p : plans) {
            if (p.getPlanID() == planID) return p;
        }
        return null;
    }

    // --- Member Actions ---

    public void bookSession(Member member, int sessionID) {
        Session session = findSessionById(sessionID);
        if (session == null) {
            throw new IllegalArgumentException("Session not found.");
        }
        if (!"Active".equalsIgnoreCase(member.getStatus())) {
            throw new IllegalStateException("Cannot book session: membership is " + member.getStatus());
        }
        session.book(member);
        if (!member.getBookedSessions().contains(session)) {
            member.getBookedSessions().add(session);
        }
        FileStorageService.saveSessions(sessions);
    }

    public void cancelBooking(Member member, int sessionID) {
        Session session = findSessionById(sessionID);
        if (session == null) return;
        session.cancel(member);
        member.getBookedSessions().remove(session);
        FileStorageService.saveSessions(sessions);
    }

    public Payment processPayment(Member member, MembershipPlan plan, String method) throws InvalidPaymentException {
        return processPayment(member, plan, method, plan.getPrice());
    }

    public Payment processPayment(Member member, MembershipPlan plan, String method, double amount)
            throws InvalidPaymentException {
        if (amount <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than 0.");
        }
        Payment payment = new Payment(paymentIdCounter++, member, plan, amount, method);
        PaymentProcess decoratedPayment = new ReceiptAuditDecorator(payment);
        try {
            decoratedPayment.processPayment();
        } catch (InvalidPaymentException ex) {
            payments.add(payment);
            FileStorageService.savePayments(payments);
            throw ex;
        } catch (Exception ex) {
            throw new InvalidPaymentException(ex.getMessage());
        }
        payments.add(payment);
        FileStorageService.savePayments(payments);
        FileStorageService.saveUsers(users);
        return payment;
    }

    // --- Trainer Actions ---

    public void updateSessionDate(int sessionID, LocalDateTime newDate) {
        Session session = findSessionById(sessionID);
        if (session != null) {
            session.setSessionDate(newDate);
            FileStorageService.saveSessions(sessions);
        }
    }

    public void cancelSession(int sessionID) {
        Session session = findSessionById(sessionID);
        if (session != null) {
            session.cancelSession();
            FileStorageService.saveSessions(sessions);
        }
    }

    public void markAttendance(Member member, Session session, boolean present) {
        Attendance existing = findAttendance(member, session);
        if (existing != null) {
            existing.setPresent(present);
            existing.setCheckInTime(LocalDateTime.now());
        } else {
            attendanceRecords.add(new Attendance(attendanceIdCounter++, member, session, present));
        }
        FileStorageService.saveAttendance(attendanceRecords);
    }

    // --- Admin Actions ---

    public Member addMember(String userID, String name, String email, String password, MembershipPlan plan, String status)
            throws DuplicateUserException {
        if (userIDExists(userID)) throw new DuplicateUserException("User ID already exists.");
        if (emailExists(email)) throw new DuplicateUserException("Email already registered.");
        Member m = new Member(userID, name, email, password, membershipCounter++, plan, LocalDate.now(), status);
        users.add(m);
        FileStorageService.saveUsers(users);
        return m;
    }

    public void removeMember(String userID) {
        User target = findUserById(userID);
        if (target instanceof Member m) {
            for (Session s : sessions) {
                s.cancel(m);
            }
            users.remove(m);
            FileStorageService.saveUsers(users);
            FileStorageService.saveSessions(sessions);
        }
    }

    public void toggleMemberStatus(String userID) {
        User target = findUserById(userID);
        if (target instanceof Member m) {
            m.setStatus("Active".equalsIgnoreCase(m.getStatus()) ? "Inactive" : "Active");
            FileStorageService.saveUsers(users);
        }
    }

    public Trainer addTrainer(String userID, String name, String email, String password, String specialization, String schedule)
            throws DuplicateUserException {
        if (userIDExists(userID)) throw new DuplicateUserException("User ID already exists.");
        if (emailExists(email)) throw new DuplicateUserException("Email already registered.");
        Trainer t = new Trainer(userID, name, email, password, trainerIdCounter++, specialization, schedule);
        users.add(t);
        FileStorageService.saveUsers(users);
        return t;
    }

    public void removeTrainer(String userID) {
        User target = findUserById(userID);
        if (target instanceof Trainer t) {
            users.remove(t);
            FileStorageService.saveUsers(users);
        }
    }

    public void updateTrainerSchedule(String userID, String schedule) {
        User target = findUserById(userID);
        if (target instanceof Trainer t) {
            t.setSchedule(schedule);
            FileStorageService.saveUsers(users);
        }
    }

    public Session createSession(Trainer trainer, LocalDateTime dateTime, String type, int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than 0.");
        }
        Session s = new Session(sessionIdCounter++, trainer, dateTime, type, maxCapacity);
        sessions.add(s);
        FileStorageService.saveSessions(sessions);
        return s;
    }

    public MembershipPlan addPlan(String name, double price, int durationMonths, String description) {
        MembershipPlan p = new MembershipPlan(planIdCounter++, name, price, durationMonths, description);
        plans.add(p);
        FileStorageService.savePlans(plans);
        return p;
    }

    public void updatePlanPrice(int planID, double price) {
        MembershipPlan p = findPlanById(planID);
        if (p != null) {
            p.setPrice(price);
            FileStorageService.savePlans(plans);
        }
    }

    public void deletePlan(int planID) {
        MembershipPlan p = findPlanById(planID);
        if (p != null) {
            plans.remove(p);
            FileStorageService.savePlans(plans);
        }
    }
}
