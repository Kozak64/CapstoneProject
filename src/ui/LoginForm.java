
package ui;

import models.*;
import javax.swing.*;
import java.awt.*;
import services.GymSystemManager;
import exceptions.InvalidLoginException;

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

    private final GymSystemManager manager =
            GymSystemManager.getInstance();

    public LoginForm() {
        setTitle("Gym Membership System - Sign In");
        setContentPane(LoginMenu);
        setSize(500, 520);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(loginButton);

        applyTheme();

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
                    "Invalid User ID format. Use XX-XXXX-XXX.",
                    "Invalid Format",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User matchedUser = manager.authenticate(userID, password);

            if (matchedUser instanceof Member member) {
                new MemberDashboard(member).setVisible(true);
            } else if (matchedUser instanceof Trainer trainer) {
                new TrainerDashboard(trainer).setVisible(true);
            } else if (matchedUser instanceof Admin admin) {
                new AdminDashboard(admin).setVisible(true);
            }

            dispose();

        } catch (InvalidLoginException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            passwordField1.setText("");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }

}
