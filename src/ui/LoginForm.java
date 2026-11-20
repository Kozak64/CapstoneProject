
package ui;

import models.*;


import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private void applyTheme(){
        Color bgGray = new Color(241, 245, 249);
        Color cardBorder = new Color(203, 213, 225);
        Color textDark = new Color(15, 23, 42);
        Color textMuted = new Color(100, 116, 139);
        Color primaryBlue = new Color(37, 99, 235);

        LoginMenu.setBackground(bgGray);

        // White card with subtle 1px border + inner padding
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(cardBorder, 1, true),
                new EmptyBorder(24, 28, 24, 28)
        ));

        titleLabel.setForeground(textDark);
        subtitleLabel.setForeground(textMuted);
        userIDLabel.setForeground(textDark);
        passwordLabel.setForeground(textDark);

        // Input fields with inner text padding so text doesn't touch the border test
        CompoundBorder inputBorder = new CompoundBorder(
                new LineBorder(cardBorder, 1),
                new EmptyBorder(6, 10, 6, 10)
        );
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);
        textField1.setFont(inputFont);
        textField1.setBorder(inputBorder);
        passwordField1.setFont(inputFont);
        passwordField1.setBorder(inputBorder);


        // Primary Login button (Royal Blue)
        styleButton(loginButton, primaryBlue, Color.WHITE, false);

        // Secondary Register button (Clean Outlined)
        styleButton(registerButton, Color.WHITE, textDark, true);
        registerButton.setBorder(new LineBorder(cardBorder, 1));
    }

    private void styleButton(JButton btn, Color bg, Color fg, boolean bordered){
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(bordered);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    private void loadDummyUsers() { //temporary for demo sake
        MembershipPlan goldPlan = new MembershipPlan(1, "Gold", 1500.0, 1, "Full gym + classes");
        users.add(new Admin("25-1824-135", "System Admin", "admin@gym.com", "admin123", 101));
        users.add(new Trainer("25-1231-123", "Coach Alex", "trainer@gym.com", "train123", 201, "Strength", "MWF 8AM-5PM"));
        users.add(new Member("25-1231-123", "Juan Dela Cruz", "member@gym.com", "mem123", 1001, goldPlan, LocalDate.now(), "Active"));
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
      //  if (matchedUser instanceof Member member) {
      //      new MemberDashboard(member).setVisible(true);
//        } else if (matchedUser instanceof Trainer trainer) {
//            new TrainerDashboard(trainer).setVisible(true);
//        } else if (matchedUser instanceof Admin admin) {
//            new AdminDashboard(admin).setVisible(true);
//        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}