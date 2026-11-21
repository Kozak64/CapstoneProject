
package services;

import models.*;
import exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GymSystemManager {

    private static final GymSystemManager instance = new GymSystemManager();

    private final List<User> users = new ArrayList<>();
    private int membershipCounter = 1001;

    private GymSystemManager() {
        loadDefaultUsers();
    }

    public static GymSystemManager getInstance() {
        return instance;
    }

    private void loadDefaultUsers() {
        MembershipPlan goldPlan = new MembershipPlan(
                1, "Gold", 1500.0, 1, "Full gym + classes"
        );

        users.add(new Admin(
                "00-1111-222", "System Admin", "admin@gym.com", "admin123", 101
        ));
    }

    // Register a new Member
    public Member registerMember(String userID, String name, String email, String password) throws DuplicateUserException {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                throw new DuplicateUserException("User ID already exists.");
            }

            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new DuplicateUserException("Email already registered.");
            }
        }

        Member newMember = new Member(
                userID, name, email, password, membershipCounter++, null, LocalDate.now(), "Inactive"
        );
        users.add(newMember);
        return newMember;
    }

    public User authenticate(String userID, String password) throws InvalidLoginException {
        for (User user : users) {
            if (user.login(userID, password)) {
                return user;
            }
        }
        throw new InvalidLoginException("Invalid User ID or password.");
    }

    public boolean userIDExists(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return true;
            }
        }
        return false;
    }

    public boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}
