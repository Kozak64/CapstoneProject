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

    private final GymSystemManager manager =
            GymSystemManager.getInstance();

    public RegisterForm() {
        setTitle("Gym Membership System - Register");
        setContentPane(RegisterPanel);
        setSize(520, 560);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(registerButton);

        applyTheme();

        if (CustomIDLabel != null) CustomIDLabel.setVisible(false);
        if (CustomIDTF != null) CustomIDTF.setVisible(false);

        registerButton.addActionListener(e -> handleRegistration());
    }

    private void handleRegistration() {
        String name = nameTextField.getText().trim();
        String email = emailTextField.getText().trim();

        String password = new String(passwordTextField.getPassword());
        String confirmPassword = new String(confirmTextField.getPassword());

        if (name.isEmpty() || email.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (name.length() < 2) {
            JOptionPane.showMessageDialog(this,
                    "Name must contain at least 2 characters.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address.", "Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 4 characters.", "Invalid Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            confirmTextField.setText("");
            return;
        }

        try {
            Member newMember = manager.registerMember(name, email, password);

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!\n\n"
                            + "Name: " + newMember.getName() + "\n"
                            + "Your Generated User ID: " + newMember.getUserID() + "\n\n"
                            + "Please save this User ID to log in.",
                    "Registration Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );
            new LoginForm().setVisible(true);
            dispose();

        } catch (DuplicateUserException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void applyTheme() {
        RegisterPanel.setBackground(UITheme.BG_GRAY);
        UITheme.styleCard(cardPanel, 24, 28);

        titleLabel.setForeground(UITheme.TEXT_DARK);
        subtitleLabel.setForeground(UITheme.TEXT_MUTED);

        JLabel[] labels = {userIDLabel, emailLabel, passwordLabel, confirmLabel, CustomIDLabel};
        for (JLabel lbl : labels) {
            if (lbl != null) {
                lbl.setForeground(UITheme.TEXT_DARK);
            }
        }

        JTextField[] inputs = {nameTextField, emailTextField, passwordTextField, confirmTextField, CustomIDTF};
        for (JTextField field : inputs) {
            if (field != null) {
                UITheme.styleInput(field);
            }
        }

        UITheme.stylePrimaryButton(registerButton);
    }
}
