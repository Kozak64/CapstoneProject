package models;

public class MembershipPlan {
    private int planID;
    private String planName;
    private double price;
    private int durationMonths;
    private String description;

    public MembershipPlan(int planID, String planName, double price, int durationMonths, String description) {
        this.planID = planID;
        this.planName = planName;
        setPrice(price);
        setDurationMonths(durationMonths);
        this.description = description;
    }
    public int getPlanID() {
        return planID;
    }

    public String getPlanName() {
        return planName;
    }

    public double getPrice() {
        return price;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public String getDescription() {
        return description;
    }



    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Plan price cannot be negative.");
        }
        this.price = price;
    }

    public void setDurationMonths(int durationMonths) {
        if (durationMonths <= 0) {
            throw new IllegalArgumentException("Duration must be at least 1 month.");
        }
        this.durationMonths = durationMonths;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return planName + " (₱" + String.format("%.2f", price) + " / " + durationMonths + " mo)";
    }

}
