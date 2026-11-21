package models;

import java.time.LocalDateTime;

public class Attendance {
    private int attendanceID;
    private Member member;
    private Session session;        // nullable if it's a general gym check-in
    private LocalDateTime checkInTime;
    private boolean present;

    public Attendance(int attendanceID, Member member, Session session, boolean present) {
        this.attendanceID = attendanceID;
        this.member = member;
        this.session = session;
        this.checkInTime = LocalDateTime.now();
        this.present = present;
    }

    public int getAttendanceID() { return attendanceID; }
    public Member getMember() { return member; }
    public Session getSession() { return session; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public boolean isPresent() { return present; }

    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public String toString() {
        String sessionName = (session != null) ? session.getType() : "General Gym Check-In";
        return member.getName() + " | " + sessionName + " | "
                + (present ? "Present" : "Absent") + " | " + checkInTime;
    }
}