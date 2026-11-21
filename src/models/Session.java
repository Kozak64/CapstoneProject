package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private int sessionID;
    private Trainer trainer;
    private List<Member> enrolledMembers;
    private LocalDateTime sessionDate;
    private String type;
    private int maxCapacity;
    private String status;

    public Session(int sessionID, Trainer trainer, LocalDateTime sessionDate,
                   String type, int maxCapacity) {
        this.sessionID = sessionID;
        this.trainer = trainer;
        this.sessionDate = sessionDate;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.enrolledMembers = new ArrayList<>();
        this.status = "Scheduled";
    }

    public int getSessionID() { return sessionID; }
    public Trainer getTrainer() { return trainer; }
    public List<Member> getEnrolledMembers() { return enrolledMembers; }
    public LocalDateTime getSessionDate() { return sessionDate; }
    public String getType() { return type; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getStatus() { return status; }

    public void setSessionDate(LocalDateTime sessionDate) { this.sessionDate = sessionDate; }
    public void setStatus(String status) { this.status = status; }

    public void book(Member member) {
        if ("Cancelled".equalsIgnoreCase(status)) {
            throw new IllegalStateException("Cannot book a cancelled session.");
        }
        if (enrolledMembers.size() >= maxCapacity) {
            throw new IllegalStateException("Session is already full.");
        }
        if (!enrolledMembers.contains(member)) {
            enrolledMembers.add(member);
        }
    }

    public void cancel(Member member) {
        enrolledMembers.remove(member);
    }

    public void cancelSession() {
        this.status = "Cancelled";
    }

    public Attendance markAttendance(Member member, boolean present) {
        return new Attendance(0, member, this, present);
    }

    @Override
    public String toString() {
        return type + " (" + enrolledMembers.size() + "/" + maxCapacity + ") - " + sessionDate;
    }
}
