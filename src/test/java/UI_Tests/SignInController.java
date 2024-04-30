import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lib.users.Instructor;
import lib.users.Student;
import lib.filesystem.Read_Write;
import lib.filesystem.Read_Write.UserAlreadyExistsException;
import lib.filesystem.Read_Write.UserNotFoundException;
import lib.users.User;

public class SignInController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text feedbackText;

    @FXML
    private void handleSignInAction() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        try {
            int userRole = Read_Write.Login(username, password);
            // Continue based on userRole or show the main application screen
            feedbackText.setText("Login successful for user role: " + userRole);
        } catch (UserNotFoundException e) {
            feedbackText.setText("Login failed: " + e.getMessage());
        }
    }
}
