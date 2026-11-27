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
    private JLabel logoLabel;
    private JLabel brandLabel;
    private JPanel optionsPanel;
    private JCheckBox rememberMeCheckBox;
    private JLabel forgotPasswordLabel;
    private JPanel dividerPanel;
    private JLabel footerLabel;

    private final GymSystemManager manager =
            GymSystemManager.getInstance();

    public LoginForm() {
        setContentPane(LoginMenu);
        UITheme.configureWindow(this, "Hussle Fitness - Gym Membership System (Sign In)");
        getRootPane().setDefaultButton(loginButton);

        if (forgotPasswordLabel != null) {
            forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        loginButton.addActionListener(e -> handleLogin());
        textField1.addActionListener(e -> handleLogin());
        passwordField1.addActionListener(e -> handleLogin());

        registerButton.addActionListener(e -> UITheme.switchFrame(this, new RegisterForm()));
    }

    private void createUIComponents() {
        LoginMenu = new UITheme.BackgroundPanel(UITheme.loadImage(getClass(), UITheme.LOGIN_BG_ASSET));
        logoLabel = UITheme.createHeaderLogoLabel(getClass(), UITheme.LOGO_HORIZONTAL_ASSET, 34);
        cardPanel = new UITheme.GlassCardPanel();
        textField1 = new UITheme.DarkTextField("xx-xxxx-xxx");
        passwordField1 = new UITheme.DarkPasswordField("Enter password");
        rememberMeCheckBox = new UITheme.DarkCheckBox("Remember me");
        loginButton = new UITheme.GradientButton("LOG IN \u2192");
        dividerPanel = new UITheme.DividerWithLabel("or");
        registerButton = new UITheme.OutlineAccentButton("CREATE NEW ACCOUNT");
    }

    private void handleLogin() {
        String userID = textField1.getText().trim();
        String password = new String(passwordField1.getPassword());

        if (userID.isEmpty() || password.isEmpty()) {
            UITheme.showMessageDialog(this,
                    "Please enter both User ID and password.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!userID.matches("\\d{2}-\\d{4}-\\d{3}")) {
            UITheme.showMessageDialog(this,
                    "Invalid User ID format. Use XX-XXXX-XXX.",
                    "Invalid Format",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User matchedUser = manager.authenticate(userID, password);

            if (matchedUser instanceof Member member) {
                UITheme.switchFrame(this, new MemberDashboard(member));
            } else if (matchedUser instanceof Trainer trainer) {
                UITheme.switchFrame(this, new TrainerDashboard(trainer));
            } else if (matchedUser instanceof Admin admin) {
                UITheme.switchFrame(this, new AdminDashboard(admin));
            }

        } catch (InvalidLoginException ex) {
            UITheme.showMessageDialog(this,
                    ex.getMessage(),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            passwordField1.setText("");
        }
    }
}
