public class Member {
    private int memberID;
    private MembershipPlan membershipPlan; // Assuming MembershipPlan is another class you will create
    private String joinDate;
    private String status;

    public Member(int memberID, MembershipPlan membershipPlan, String joinDate, String status) {
        this.memberID = memberID;
        this.membershipPlan = membershipPlan;
        this.joinDate = joinDate;
        this.status = status;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public MembershipPlan getMembershipPlan() {
        return membershipPlan;
    }

    public void setMembershipPlan(MembershipPlan membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void registerMembership() {
        System.out.println("Member " + memberID + " is registering their membership...");
    }

    public void bookSession(Session session) { // Assuming Session is another class
        System.out.println("Member " + memberID + " is booking a session...");
    }

    public void trackAttendance() {
        System.out.println("Tracking attendance for Member " + memberID + "...");
    }

    public void renewPlan() {
        System.out.println("Member " + memberID + " is renewing their plan...");
    }
}