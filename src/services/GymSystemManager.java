package services;

import exceptions.DuplicateUserException;
import exceptions.InvalidLoginException;
import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GymSystemManager {

    private static final GymSystemManager instance = new GymSystemManager();

    private final List<MembershipPlan> plans = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Session> sessions = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private final List<Attendance> attendanceRecords = new ArrayList<>();

    private int membershipCounter = 1001;
    private int trainerIdCounter = 201;
    private int planIdCounter = 1;
    private int sessionIdCounter = 101;
    private int paymentIdCounter = 501;
    private int attendanceIdCounter = 1;

    private GymSystemManager() {
        loadData();
    }

    public static GymSystemManager getInstance() {
        return instance;
    }

    private void loadData() {
        plans.addAll(FileStorageService.loadPlans());
        users.addAll(FileStorageService.loadUsers(plans));
        sessions.addAll(FileStorageService.loadSessions(users));
        payments.addAll(FileStorageService.loadPayments(users, plans));
        attendanceRecords.addAll(FileStorageService.loadAttendance(users, sessions));

        if (plans.isEmpty()) {
            plans.add(new MembershipPlan(1, "Monthly Basic", 1200.0, 1, "Gym floor access"));
            plans.add(new MembershipPlan(2, "Quarterly Pro", 3200.0, 3, "Gym + group classes"));
            plans.add(new MembershipPlan(3, "Annual Elite", 11000.0, 12, "All access + PT sessions"));
            FileStorageService.savePlans(plans);
        }

        if (users.isEmpty()) {
            Admin defaultAdmin = new Admin(
                    "00-1111-222", "System Admin", "admin@gym.com", "admin123", 101
            );
            Trainer defaultTrainer = new Trainer(
                    "25-1231-123", "Coach Alex", "alex@gym.com", "trainer123",
                    201, "Strength & Conditioning", "MWF 8AM-5PM"
            );
            users.add(defaultAdmin);
            users.add(defaultTrainer);
            FileStorageService.saveUsers(users);

            if (sessions.isEmpty()) {
                LocalDateTime tomorrow9am = LocalDate.now().plusDays(1).atTime(9, 0);
                LocalDateTime nextDay4pm = LocalDate.now().plusDays(2).atTime(16, 0);
                sessions.add(new Session(101, defaultTrainer, tomorrow9am, "Strength & Conditioning", 15));
                sessions.add(new Session(102, defaultTrainer, nextDay4pm, "HIIT Cardio", 15));
                FileStorageService.saveSessions(sessions);
            }
        }

        syncCounters();
    }

    private void syncCounters() {
        for (MembershipPlan p : plans) {
            planIdCounter = Math.max(planIdCounter, p.getPlanID() + 1);
        }
        for (User u : users) {
            if (u instanceof Member) {
                membershipCounter = Math.max(membershipCounter, ((Member) u).getMembershipNumber() + 1);
            } else if (u instanceof Trainer) {
                trainerIdCounter = Math.max(trainerIdCounter, ((Trainer) u).getTrainerID() + 1);
            }
        }
        for (Session s : sessions) {
            sessionIdCounter = Math.max(sessionIdCounter, s.getSessionID() + 1);
        }
        for (Payment p : payments) {
            paymentIdCounter = Math.max(paymentIdCounter, p.getPaymentID() + 1);
        }
        for (Attendance a : attendanceRecords) {
            attendanceIdCounter = Math.max(attendanceIdCounter, a.getAttendanceID() + 1);
        }
    }

    private void ensureUniqueUser(String userID, String email) throws DuplicateUserException {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                throw new DuplicateUserException("User ID already exists.");
            }
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new DuplicateUserException("Email already registered.");
            }
        }
    }

    public Member registerMember(String userID, String name, String email, String password) throws DuplicateUserException {
        return addMember(userID, name, email, password, null, "Inactive");
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
        return findUserById(userID) != null;
    }

    public boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public User findUserById(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return user;
            }
        }
        return null;
    }

    public MembershipPlan findPlanById(int planID) {
        for (MembershipPlan plan : plans) {
            if (plan.getPlanID() == planID) {
                return plan;
            }
        }
        return null;
    }

    public Session findSessionById(int sessionID) {
        for (Session session : sessions) {
            if (session.getSessionID() == sessionID) {
                return session;
            }
        }
        return null;
    }

    // --- Admin: Members ---

    public Member addMember(String userID, String name, String email, String password,
                            MembershipPlan plan, String status) throws DuplicateUserException {
        ensureUniqueUser(userID, email);
        Member member = new Member(
                userID, name, email, password, membershipCounter++, plan, LocalDate.now(), status
        );
        users.add(member);
        FileStorageService.saveUsers(users);
        return member;
    }

    public void toggleMemberStatus(String userID) {
        User u = findUserById(userID);
        if (u instanceof Member) {
            Member m = (Member) u;
            m.setStatus("Active".equalsIgnoreCase(m.getStatus()) ? "Inactive" : "Active");
            FileStorageService.saveUsers(users);
        }
    }

    public boolean removeMember(String userID) {
        User u = findUserById(userID);
        if (!(u instanceof Member)) {
            return false;
        }
        Member m = (Member) u;
        users.remove(m);
        for (Session s : sessions) {
            s.cancel(m);
        }
        FileStorageService.saveUsers(users);
        FileStorageService.saveSessions(sessions);
        return true;
    }

    // --- Admin: Trainers ---

    public Trainer addTrainer(String userID, String name, String email, String password,
                              String specialization, String schedule) throws DuplicateUserException {
        ensureUniqueUser(userID, email);
        Trainer trainer = new Trainer(
                userID, name, email, password, trainerIdCounter++, specialization, schedule
        );
        users.add(trainer);
        FileStorageService.saveUsers(users);
        return trainer;
    }

    public void updateTrainerSchedule(String userID, String newSchedule) {
        User u = findUserById(userID);
        if (u instanceof Trainer) {
            ((Trainer) u).setSchedule(newSchedule);
            FileStorageService.saveUsers(users);
        }
    }

    public boolean removeTrainer(String userID) {
        User u = findUserById(userID);
        if (!(u instanceof Trainer)) {
            return false;
        }
        users.remove(u);
        FileStorageService.saveUsers(users);
        return true;
    }

    // --- Admin: Plans ---

    public MembershipPlan addPlan(String name, double price, int durationMonths, String description) {
        MembershipPlan plan = new MembershipPlan(planIdCounter++, name, price, durationMonths, description);
        plans.add(plan);
        FileStorageService.savePlans(plans);
        return plan;
    }

    public void updatePlanPrice(int planID, double newPrice) {
        MembershipPlan plan = findPlanById(planID);
        if (plan != null) {
            plan.setPrice(newPrice);
            FileStorageService.savePlans(plans);
        }
    }

    public boolean deletePlan(int planID) {
        MembershipPlan plan = findPlanById(planID);
        if (plan == null) {
            return false;
        }
        plans.remove(plan);
        FileStorageService.savePlans(plans);
        return true;
    }

    // --- Sessions ---

    public Session createSession(Trainer trainer, LocalDateTime date, String type, int maxCapacity) {
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer is required.");
        }
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be at least 1.");
        }
        Session session = new Session(sessionIdCounter++, trainer, date, type, maxCapacity);
        sessions.add(session);
        FileStorageService.saveSessions(sessions);
        return session;
    }

    public void updateSessionDate(int sessionID, LocalDateTime newDate) {
        Session session = findSessionById(sessionID);
        if (session == null) {
            throw new IllegalArgumentException("Session not found.");
        }
        session.setSessionDate(newDate);
        FileStorageService.saveSessions(sessions);
    }

    public void cancelSession(int sessionID) {
        Session session = findSessionById(sessionID);
        if (session == null) {
            throw new IllegalArgumentException("Session not found.");
        }
        session.cancelSession();
        FileStorageService.saveSessions(sessions);
    }

    public void bookSession(Member member, int sessionID) {
        Session session = findSessionById(sessionID);
        if (session == null) {
            throw new IllegalArgumentException("Session not found.");
        }
        if (member.getMembershipPlan() == null) {
            throw new IllegalStateException("Please subscribe to a membership plan first.");
        }
        if (!"Active".equalsIgnoreCase(member.getStatus())) {
            throw new IllegalStateException("Your membership is not active.");
        }
        if (member.getExpiryDate() == null || member.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Your membership has expired.");
        }
        if (session.getEnrolledMembers().contains(member)) {
            throw new IllegalStateException("You are already booked in this session.");
        }
        session.book(member);
        if (!member.getBookedSessions().contains(session)) {
            member.bookSession(session);
        }
        FileStorageService.saveSessions(sessions);
    }

    public void cancelBooking(Member member, int sessionID) {
        Session session = findSessionById(sessionID);
        if (session == null) {
            throw new IllegalArgumentException("Session not found.");
        }
        if (!session.getEnrolledMembers().contains(member)) {
            throw new IllegalStateException("You are not booked in this session.");
        }
        session.cancel(member);
        member.getBookedSessions().remove(session);
        FileStorageService.saveSessions(sessions);
    }

    // --- Payments ---

    public Payment processPayment(Member member, MembershipPlan plan, String method) {
        if (member == null || plan == null) {
            throw new IllegalArgumentException("Member and plan are required.");
        }
        Payment payment = new Payment(paymentIdCounter++, member, plan, plan.getPrice(), method);
        payment.processPayment();
        payments.add(payment);
        FileStorageService.savePayments(payments);
        FileStorageService.saveUsers(users);
        return payment;
    }

    // --- Attendance ---

    public Attendance markAttendance(Member member, Session session, boolean present) {
        if (member == null) {
            throw new IllegalArgumentException("Member is required.");
        }
        for (Attendance existing : attendanceRecords) {
            boolean sameMember = existing.getMember() != null
                    && existing.getMember().getUserID().equals(member.getUserID());
            boolean sameSession = (existing.getSession() == null && session == null)
                    || (existing.getSession() != null && session != null
                    && existing.getSession().getSessionID() == session.getSessionID());
            if (sameMember && sameSession) {
                existing.setPresent(present);
                existing.setCheckInTime(LocalDateTime.now());
                FileStorageService.saveAttendance(attendanceRecords);
                return existing;
            }
        }

        Attendance record = new Attendance(attendanceIdCounter++, member, session, present);
        attendanceRecords.add(record);
        FileStorageService.saveAttendance(attendanceRecords);
        return record;
    }

    // --- Queries ---

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Member> getMembers() {
        List<Member> result = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Member) {
                result.add((Member) u);
            }
        }
        return result;
    }

    public List<Trainer> getTrainers() {
        List<Trainer> result = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Trainer) {
                result.add((Trainer) u);
            }
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
        if (trainer == null) {
            return getSessions();
        }
        List<Session> result = new ArrayList<>();
        for (Session s : sessions) {
            if (s.getTrainer() != null && s.getTrainer().getUserID().equals(trainer.getUserID())) {
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

    public List<Attendance> getAttendanceRecords() {
        return new ArrayList<>(attendanceRecords);
    }

    public List<Attendance> getAttendanceForMember(Member member) {
        List<Attendance> result = new ArrayList<>();
        if (member == null) {
            return result;
        }
        for (Attendance a : attendanceRecords) {
            if (a.getMember() != null && a.getMember().getUserID().equals(member.getUserID())) {
                result.add(a);
            }
        }
        return result;
    }

    public Attendance findAttendance(Member member, Session session) {
        if (member == null) {
            return null;
        }
        for (Attendance a : attendanceRecords) {
            boolean sameMember = a.getMember() != null
                    && a.getMember().getUserID().equals(member.getUserID());
            boolean sameSession = (a.getSession() == null && session == null)
                    || (a.getSession() != null && session != null
                    && a.getSession().getSessionID() == session.getSessionID());
            if (sameMember && sameSession) {
                return a;
            }
        }
        return null;
    }
}
