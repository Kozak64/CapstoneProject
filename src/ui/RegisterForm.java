package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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

    public RegisterForm() {
        setTitle("Gym Membership System - Register");
        setContentPane(RegisterPanel);
        setSize(520, 560);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(registerButton);

        applyTheme();


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordTextField.getPassword());
                String confirmPassword = new String(confirmTextField.getPassword());

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(
                            RegisterForm.this, "Password does not match", "Registration Error", JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }


                new LoginForm().setVisible(true);
                dispose();
            }
        });


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
