package ui;

import javax.swing.*;

public class RegisterForm extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPanel RegisterPanel;
    private JButton registerButton;

    public RegisterForm(){
        setTitle("Gym Membership System - Register Form");
        setContentPane(RegisterPanel);
        setSize(500, 520);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(registerButton);
    }
}
