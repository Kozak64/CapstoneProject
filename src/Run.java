
import ui.loginform.LoginForm;
import javax.swing.SwingUtilities;

//DO NOT EDIT
public class Run {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
