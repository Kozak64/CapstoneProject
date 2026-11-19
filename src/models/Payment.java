package models;

import java.time.LocalDate;

public class Payment implements PaymentProcess {
    private int paymentID;
    private Member member;
    private MembershipPlan plan;
    private double amount;
    private LocalDate paymentDate;
    private String method;
    private String status;

    public Payment(int paymentID, Member member, MembershipPlan plan,
                   double amount, String method) {
        this.paymentID = paymentID;
        this.member = member;
        this.plan = plan;
        setAmount(amount);
        this.paymentDate = LocalDate.now();
        this.method = method;
        this.status = "Pending";
    }

    public int getPaymentID() { return paymentID; }
    public Member getMember() { return member; }
    public MembershipPlan getPlan() { return plan; }
    public double getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than 0.");
        }
        this.amount = amount;
    }

    @Override
    public void processPayment() {
        if (plan != null && amount < plan.getPrice()) {
            this.status = "Failed";
            throw new IllegalArgumentException("Insufficient payment for plan: " + plan.getPlanName());
        }
        this.status = "Completed";
        if (member != null && plan != null) {
            member.renewPlan(plan);
        }
    }

    @Override
    public String toString() {
        return "Payment #" + paymentID + " | " + member.getName()
                + " | ₱" + String.format("%.2f", amount)
                + " | " + method + " | " + status;
    }
}