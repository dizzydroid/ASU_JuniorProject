import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpDetailsController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private String userType;
    

    public void setStudentUserType() {
        this.userType = "Student";
    }

    public void setInstructorUserType() {
        this.userType = "Instructor";
    }

    // Call this method before showing the sign-up details scene
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText(); // Get the entered username
        String password = passwordField.getText(); // Get the entered password

        if ("Student".equals(userType)) {
            // Logic to create a new Student account using username and password
            System.out.println("Creating a new Student account with username: " + username);
        } else if ("Instructor".equals(userType)) {
            // Logic to create a new Instructor account using username and password
            System.out.println("Creating a new Instructor account with username: " + username);
        }
    }
}


