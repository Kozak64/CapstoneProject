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

    public boolean login(String email, String password){
        return this.email.equals(email) && this.password.equals(password);
    }

    public void logout(){
        System.out.println(name + " logged out.");
    }
}
