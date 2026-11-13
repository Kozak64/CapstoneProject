public class Trainer {
    private int trainerID;
    private String specialization;
    private String schedule;

    public Trainer(int trainerID, String specialization, String schedule) {
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
        System.out.println("Trainer " + trainerID + " is viewing upcoming sessions...");
    }

    public void updateSessions() {
        System.out.println("Trainer " + trainerID + " is updating their session schedule...");
    }

    public void markAttendance(Member member) {
        System.out.println("Trainer " + trainerID + " is marking attendance for member...");
    }
}