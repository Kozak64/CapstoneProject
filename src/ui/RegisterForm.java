package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame{
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

    public RegisterForm(){
        setTitle("Gym Membership System - Sign In");
        setContentPane(RegisterPanel);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(registerButton);



        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordTextField.getPassword());
                String confirmPassword = new String(confirmTextField.getPassword());

                if(!password.equals(confirmPassword)){
                    JOptionPane.showMessageDialog(
                            RegisterForm.this, "Password does not match", "Registration Error",JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }


                new LoginForm().setVisible(true);
                dispose();
            }
        });


    }
}
