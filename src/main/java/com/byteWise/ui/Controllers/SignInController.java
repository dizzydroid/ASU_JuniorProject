package src.main.java.com.byteWise.ui.Controllers;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.com.byteWise.users.Instructor;
import src.main.java.com.byteWise.users.Student;
import src.main.java.com.byteWise.filesystem.Read_Write;
import src.main.java.com.byteWise.filesystem.Read_Write.UserAlreadyExistsException;
import src.main.java.com.byteWise.filesystem.Read_Write.UserNotFoundException;
import src.main.java.com.byteWise.users.User;
// import lib.users.Instructor;
// import lib.users.Student;
// import lib.filesystem.Read_Write;
// import lib.filesystem.Read_Write.UserAlreadyExistsException;
// import lib.filesystem.Read_Write.UserNotFoundException;
// import lib.users.User;

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
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        switch (userRole) {
            case 0: // Student
                loader.setLocation(getClass().getResource("StudentDashboard.fxml"));
                root = loader.load();
                // Set the username on the student dashboard
                StudentDashboardController studentController = loader.getController();
                studentController.setUserName(username);
                feedbackText.setText("Login successful. Welcome, Student!");
                break;
            case 1: // Instructor
                loader.setLocation(getClass().getResource("InstructorDashboard.fxml"));
                root = loader.load();
                // Set the username on the instructor dashboard
                InstructorDashboardController instructorController = loader.getController();
                instructorController.setUserName(username);
                feedbackText.setText("Login successful. Welcome, Instructor!");
                break;
            case 2: // Admin
                loader.setLocation(getClass().getResource("AdminDashboard.fxml"));
                root = loader.load();
                // Set the username on the admin dashboard
                AdminDashboardController adminController = loader.getController();
                adminController.setUserName(username);
                feedbackText.setText("Login successful. Welcome, Admin!");
                break;
            default:
                throw new UserNotFoundException("Invalid user role");
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    } catch (UserNotFoundException e) {
        feedbackText.setText("Login failed: " + e.getMessage());
    } catch (IOException e) {
        feedbackText.setText("Error loading the dashboard: " + e.getMessage());
    }
}


}
