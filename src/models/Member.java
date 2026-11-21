package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

public class Member extends User{
    private int membershipNumber;
    private MembershipPlan membershipPlan;
    private LocalDate joinDate;
    private LocalDate expiryDate;
    private String status;
    private List<Session> bookedSessions;

    public Member(String userId, String name, String email, String password, int membershipNumber, MembershipPlan membershipPlan, LocalDate joinDate, String status){
        super(userId, name, email, password, "Member");
        this.membershipNumber = membershipNumber;
        this.membershipPlan = membershipPlan;
        this.joinDate = joinDate;
        this.expiryDate = (membershipPlan != null)
                ? joinDate.plusMonths(membershipPlan.getDurationMonths())
                : null;
        this.status = status;
        this.bookedSessions = new ArrayList<>();
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

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public String getStatus(){
        return status;
    }

    public List<Session> getBookedSessions(){
        return bookedSessions;
    }





    public void setMembershipPlan(MembershipPlan membershipPlan){
        this.membershipPlan = membershipPlan;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate;}





    public void bookSession(Session session) {
        if (!"Active".equalsIgnoreCase(status)) {
            throw new IllegalStateException("Cannot book session: membership is " + status);
        }
        bookedSessions.add(session);
    }
    public void renewPlan(MembershipPlan newPlan) {
        this.membershipPlan = newPlan;
        LocalDate baseDate = (expiryDate != null && expiryDate.isAfter(LocalDate.now()))
                ? expiryDate
                : LocalDate.now();
        this.expiryDate = baseDate.plusMonths(newPlan.getDurationMonths());
        this.status = "Active";
    }
    @Override
    public String toString() {
        return getName() + " (#" + membershipNumber + " - " + status + ")";
    }

    @Override
    public String getDashBoardTitle() {
        return "Member Portal - " + getName();
    }
}
