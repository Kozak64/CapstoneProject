
package ui;

import models.*;


import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoginForm extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel LoginMenu;
    private JPanel cardPanel;
    private JLabel userIDLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;

    // Temporary test users until service/GymSystemManager is created
    private final List<User> users = new ArrayList<>();

    public LoginForm() {
        setTitle("Gym Membership System - Sign In");
        setContentPane(LoginMenu);
        setSize(500, 520);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(loginButton);

        applyTheme();
        loadDummyUsers();

        loginButton.addActionListener(e -> handleLogin());

        registerButton.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            dispose();
        });
    }

    private void applyTheme() {
        LoginMenu.setBackground(UITheme.BG_GRAY);
        UITheme.styleCard(cardPanel, 24, 28);

        titleLabel.setForeground(UITheme.TEXT_DARK);
        subtitleLabel.setForeground(UITheme.TEXT_MUTED);
        userIDLabel.setForeground(UITheme.TEXT_DARK);
        passwordLabel.setForeground(UITheme.TEXT_DARK);
        passwordLabel.setEnabled(true);

        UITheme.styleInput(textField1);
        UITheme.styleInput(passwordField1);

        UITheme.stylePrimaryButton(loginButton);
        UITheme.styleSecondaryButton(registerButton);
    }

    private void loadDummyUsers() { //temporary for demo sake
        MembershipPlan goldPlan = new MembershipPlan(1, "Gold", 1500.0, 1, "Full gym + classes");
        users.add(new Admin("25-1824-135", "System Admin", "admin@gym.com", "admin123", 101));
        users.add(new Trainer("25-1231-123", "Coach Alex", "trainer@gym.com", "train123", 201, "Strength", "MWF 8AM-5PM"));
        users.add(new Member("25-1234-567", "Juan Dela Cruz", "member@gym.com", "mem123", 1001, goldPlan, LocalDate.now(), "Active"));
    }

    private void handleLogin() {
        String userID = textField1.getText().trim();
        String password = new String(passwordField1.getPassword());
        if (userID.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both User ID and password.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!userID.matches("\\d{2}-\\d{4}-\\d{3}")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid User ID format. Use XX-XXXX-XXX (e.g., 25-1824-135).",
                    "Invalid Format",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        User matchedUser = null;
        for (User u : users) {
            if (u.login(userID, password)) {
                matchedUser = u;
                break;
            }
        }
        if (matchedUser == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid User ID or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField1.setText("");
            return;
        }

        // Open the dashboard matching the User subclass
        if (matchedUser instanceof Member member) {
            new MemberDashboard(member).setVisible(true);
        } else if (matchedUser instanceof Trainer trainer) {
            new TrainerDashboard(trainer).setVisible(true);
        } else if (matchedUser instanceof Admin admin) {
            new AdminDashboard(admin).setVisible(true);
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }

}
