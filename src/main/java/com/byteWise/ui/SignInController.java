package src.main.java.com.byteWise.ui;
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

public class SignInController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text feedbackText;

   @FXML
   private void handleSignInAction() {
    Read_Write.setFilePath();
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
                StudentDashboardController studentController = loader.getController();
                studentController.setUserName(username);
                Student student = (Student) Read_Write.readFromJson(username);
                studentController.setStudent(student);
                feedbackText.setText("Login successful. Welcome, Student!");
                break;
            case 1: // Instructor
                loader.setLocation(getClass().getResource("InstructorDashboard.fxml"));
                root = loader.load();
                InstructorDashboardController instructorController = loader.getController();
                instructorController.setUserName(username);
                instructorController.setInstructor((Instructor) Read_Write.readFromJson(username));
                feedbackText.setText("Login successful. Welcome, Instructor!");
                break;
            case 2: // Admin
                loader.setLocation(getClass().getResource("AdminDashboard.fxml"));
                root = loader.load();
                AdminDashboardController adminController = loader.getController();
                adminController.setUserName(username);
                feedbackText.setText("Login successful. Welcome, Admin!");
                break;
            default:
                throw new UserNotFoundException("Invalid user role");
        }

        UserData.getInstance().setUsername(username); // Set username in UserData singleton for use in other controllers

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
