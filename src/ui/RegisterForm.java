package ui;

import javax.swing.*;
import java.awt.*;
import services.GymSystemManager;
import exceptions.DuplicateUserException;
import models.*;

public class RegisterForm extends JFrame {
    private JPanel RegisterPanel;
    private JPanel cardPanel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel userIDLabel;
    private JButton registerButton;
    private JTextField nameTextField;
    private JPasswordField passwordTextField;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel confirmLabel;
    private JTextField emailTextField;
    private JPasswordField confirmTextField;
    private JLabel CustomIDLabel;
    private JTextField CustomIDTF;
    private JLabel logoLabel;
    private JLabel brandLabel;
    private JLabel footerLabel;

    private final GymSystemManager manager =
            GymSystemManager.getInstance();

    public RegisterForm() {
        setContentPane(RegisterPanel);
        UITheme.configureWindow(this, "Hussle Fitness - Gym Membership System (Register)");
        getRootPane().setDefaultButton(registerButton);

        if (CustomIDLabel != null) CustomIDLabel.setVisible(false);
        if (CustomIDTF != null) CustomIDTF.setVisible(false);

        registerButton.addActionListener(e -> handleRegistration());
    }

    private void createUIComponents() {
        RegisterPanel = new UITheme.BackgroundPanel(UITheme.loadImage(getClass(), UITheme.LOGIN_BG_ASSET));
        logoLabel = UITheme.createHeaderLogoLabel(getClass(), UITheme.LOGO_HORIZONTAL_ASSET, 34);
        cardPanel = new UITheme.GlassCardPanel();
        nameTextField = new UITheme.DarkTextField("Enter full name", 38);
        emailTextField = new UITheme.DarkTextField(
                "you@example.com",
                new UITheme.MailIcon(UITheme.TEXT_PLACEHOLDER),
                new UITheme.MailIcon(UITheme.ELECTRIC_BLUE_LIGHT),
                38
        );
        CustomIDTF = new UITheme.DarkTextField("xx-xxxx-xxx", 38);
        passwordTextField = new UITheme.DarkPasswordField("Enter password", 38);
        confirmTextField = new UITheme.DarkPasswordField("Confirm password", 38);
        registerButton = new UITheme.GradientButton("CREATE ACCOUNT \u2192");
    }

    private void handleRegistration() {
        String name = nameTextField.getText().trim();
        String email = emailTextField.getText().trim();

        String password = new String(passwordTextField.getPassword());
        String confirmPassword = new String(confirmTextField.getPassword());

        if (name.isEmpty() || email.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {

            UITheme.showMessageDialog(this,
                    "Please fill in all fields.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (name.length() < 2) {
            UITheme.showMessageDialog(this,
                    "Name must contain at least 2 characters.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            UITheme.showMessageDialog(this,
                    "Please enter a valid email address.", "Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String domain = email.substring(email.indexOf('@') + 1).toLowerCase();
        java.util.Set<String> allowedDomains = java.util.Set.of(
                "gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "icloud.com", "cit.edu"
        );
        if (!allowedDomains.contains(domain)) {
            UITheme.showMessageDialog(this,
                    "Please use a supported email handle (e.g., @gmail.com, @yahoo.com, @outlook.com, @hotmail.com, @icloud.com, @cit.edu).",
                    "Invalid Email Domain", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 8) {
            UITheme.showMessageDialog(this,
                    "Password invalid. Password should be 8 characters long", "Invalid Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            UITheme.showMessageDialog(this, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            confirmTextField.setText("");
            return;
        }

        try {
            Member newMember = manager.registerMember(name, email, password);

            UITheme.showMessageDialog(
                    this,
                    "Registration successful!\n\n"
                            + "Name: " + newMember.getName() + "\n"
                            + "Your Generated User ID: " + newMember.getUserID() + "\n\n"
                            + "Please save this User ID to log in.",
                    "Registration Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );
            UITheme.switchFrame(this, new LoginForm());

        } catch (DuplicateUserException | IllegalArgumentException ex) {
            UITheme.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
