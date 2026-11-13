import java.time.LocalDate;

public class Member extends User{
    private int membershipNumber;
    private MembershipPlan membershipPlan;
    private LocalDate joinDate;
    private String status;

    public Member(int userId, String name, String email, String password, int membershipNumber, MembershipPlan membershipPlan, localDate joinDate, String status){
        super(userId, name, email, password, "Member");
        this.membershipNumber = membershipNumber;
        this.membershipPlan = membershipPlan;
        this.LocalDate = joinDate;
        this.status = status;
    }

    public int getMembershipNumber(){
        return membershipNumber;
    }

    public MembershipPlan getMembershipPlan(){
        return membershipPlan;
    }

    public LocalDate getJoinDate(){
        return joinDate;
    }

    public String getStatus(){
        return status;
    }
}
