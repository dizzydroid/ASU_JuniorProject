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

public class SignUpDetailsController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text feedbackText;

    private String userType;

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void initUserType(String userType) {
        this.userType = userType;
    }

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            feedbackText.setText("Username and password cannot be empty.");
            return;
        }

        try {
            if ("Student".equals(userType)) {
                // Assuming role "0" is for Student
                Read_Write.Signup(username, password, 0);
                Read_Write.initializeJSON(username);
                feedbackText.setText("New student account created successfully!");
            } else if ("Instructor".equals(userType)) {
                // Assuming role "1" is for Instructor
                Read_Write.Signup(username, password, 1);
                // If Instructors also have a JSON file, you would initialize it here similarly
                feedbackText.setText("New instructor account created successfully!");
            } else {
                feedbackText.setText("User type is not recognized.");
            }
        } catch (UserAlreadyExistsException e) {
            feedbackText.setText("Account creation failed: " + e.getMessage());
        }
    }
    
}
