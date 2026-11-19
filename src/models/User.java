package models;

public abstract class User{
    private String userID;
    private String name;
    private String email;
    private String password;
    private String role;


    public User(String userID, String name, String email, String password, String role){
        setUserID(userID);
        setName(name);
        setEmail(email);
        setPassword(password);
        this.role = role;
    }

    public String getUserID(){
        return userID;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }


    public void setUserID(String userID) {
        if (userID == null || !userID.trim().matches("\\d{2}-\\d{4}-\\d{3}")) {
            throw new IllegalArgumentException("User ID must follow the format XX-XXXX-XXX (e.g., 25-1824-135).");
        }
        this.userID = userID.trim();
    }

    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email.trim();
    }
    public void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        this.password = password;
    }



    public boolean login(String userID, String password) {
        return this.userID.equals(userID) && this.password.equals(password);
    }

    public void logout(){
        System.out.println(name + "( "+role+" ) logged out.");
    }

    public abstract String getDashBoardTitle();

    @Override
    public String toString() {
        return "[" +role+"] "+name+" ("+email+" )";
    }
}
