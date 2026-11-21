
package services;

import models.*;
import exceptions.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GymSystemManager {

    private static final GymSystemManager instance = new GymSystemManager();

    private final List<User> users = new ArrayList<>();
    private final Random random = new Random();
    private int idCounter = 1;
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

    public String generateUserID() {
        String id;
        do {
            String year = String.format("%02d", Year.now().getValue() % 100);
            String seq = String.format("%04d", idCounter++);
            String rand = String.format("%03d", random.nextInt(1000));
            id = year + "-" + seq + "-" + rand;
        } while (userIDExists(id));
        return id;
    }

    // Register a new Member with auto-generated User ID
    public Member registerMember(String name, String email, String password) throws DuplicateUserException {
        if (emailExists(email)) {
            throw new DuplicateUserException("Email already registered.");
        }

        String userID = generateUserID();
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
