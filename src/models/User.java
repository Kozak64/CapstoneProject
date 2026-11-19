package models;

public abstract class User{
    private int userID;
    private String name;
    private String email;
    private String password;
    private String role;


    public User(int userID, String name, String email, String password, String role){
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getUserID(){
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



    public boolean login(String email, String password){
        return this.email.equals(email) && this.password.equals(password);
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
