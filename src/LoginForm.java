import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;
    private JTextPane userIDTextPane;
    private JTextPane passwordTextPane;
    private JPanel LoginMenu;

    public LoginForm(){
        setTitle("Sign In");
        setContentPane(LoginMenu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(500,500);
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    registerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
}
    static void main(){
        SwingUtilities.invokeLater(()->{
            new LoginForm().setVisible(true);
        });
    }

}
