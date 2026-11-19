package models;

public class Trainer extends User{
    private int trainerID;
    private String specialization;
    private String schedule;

    public Trainer(String userId, String name, String email, String password, int trainerID, String specialization, String schedule) {
        super(userId, name, email, password, "Trainer");
        this.trainerID = trainerID;
        this.specialization = specialization;
        this.schedule = schedule;
    }

    public int getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void viewSessions() {
        System.out.println("models.Trainer " + trainerID + " is viewing upcoming sessions...");
    }

    public void updateSessions() {
        System.out.println("models.Trainer " + trainerID + " is updating their session schedule...");
    }

    public void markAttendance(
            Member member) {
        System.out.println("models.Trainer " + trainerID + " is marking attendance for member...");
    }

    @Override
    public String getDashBoardTitle() {
        return "Trainer Portal - "+getName()+" ("+specialization+")";
    }
}