public class Admin {
    private int adminID;

    public Admin(int adminID) {
        this.adminID = adminID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void manageMembers() {
        System.out.println("Admin " + adminID + " is opening the member management system...");
        System.out.println("Options available: Add, Update, Remove, or View Members.");
    }

    public void manageTrainers() {
        System.out.println("Admin " + adminID + " is opening the trainer management system...");
        System.out.println("Options available: Add, Update, Remove, or View Trainers.");
    }

    public void managePlans() {
        System.out.println("Admin " + adminID + " is reviewing membership plans...");
        System.out.println("Action: Updating plan prices and features.");
    }

    public void viewPayments() {
        System.out.println("Admin " + adminID + " is accessing the billing database...");
        System.out.println("Action: Displaying recent payment transactions.");
    }

    public void generateReports() {
        System.out.println("Admin " + adminID + " is compiling system data...");
        System.out.println("Action: Generating attendance and financial reports.");
    }
}