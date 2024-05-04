package src.main.java.com.byteWise.ui;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.users.Student.CourseNotFoundException;

public class GiveUpController {
    @FXML
    private Button profileBtn, backBtn, removeBtn;

    @FXML
    private ListView<Course> coursesListView;

    private StudentDashboardController studentDashboardController;

    public void setStudentDashboardController(StudentDashboardController controller) {
        this.studentDashboardController = controller;
        if (controller != null && controller.getStudent() != null) {
            // Initialize the ListView with student's courses when setting the dashboard controller
            displayCourses();
        }
    }

    @FXML
    public void initialize() {
        // Initialize may be called before setStudentDashboardController, handle appropriately
        if (studentDashboardController != null && studentDashboardController.getStudent() != null) {
            displayCourses();
        }
    }

    private void displayCourses() {
        if (studentDashboardController.getStudent() != null) {
            coursesListView.setItems(FXCollections.observableArrayList(studentDashboardController.getStudent().getCourses()));
        }
    }

    @FXML
    private void handleBackToDashboardAction() throws IOException {
        changeScene("StudentDashboard.fxml");
    }

    // Method to remove a selected course from the ListView and student's courses
    @FXML
    private void handleRemoveCourseAction() {
        Course selectedCourse = coursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                studentDashboardController.getStudent().dropCourse(selectedCourse);
                coursesListView.getItems().remove(selectedCourse);  // Update the ListView after successful removal
                showAlert("Success", "Course removed successfully.");
            } catch (CourseNotFoundException ex) {
                showAlert("Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert("Error", "Failed to update course data.");
            }
        } else {
            showAlert("Error", "No course selected!");
        }
    }
    
    @FXML
    private void handleProfileAction() {
        if (studentDashboardController == null || studentDashboardController.getStudent() == null) {
            showAlert("Error", "No student data available.");
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Profile Options");

        ButtonType usernameButtonType = new ButtonType("View Username", ButtonData.OTHER);
        ButtonType passwordButtonType = new ButtonType("View Password", ButtonData.OTHER);
        ButtonType signOutButtonType = new ButtonType("Sign Out", ButtonData.OTHER);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(usernameButtonType, passwordButtonType, signOutButtonType,
                cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == usernameButtonType) {
                return "Username: " + studentDashboardController.getStudent().getName();
            } else if (dialogButton == passwordButtonType) {
                performSecurityCheck();
                return null; // Don't close the dialog on this option
            } else if (dialogButton == signOutButtonType) {
                try {
                    changeScene("welcome_scene.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Signed out";
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> showAlert("Information", result));
    }

    private void performSecurityCheck() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Security Check");
        alert.setHeaderText("You must pass the security check to see your password.");
        alert.setContentText("Are you sure you're not a robot?");

        ButtonType yesButton = new ButtonType("Yes, I'm human!");
        ButtonType noButton = new ButtonType("Oops, I'm a robot!", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            showAlert("Password Revealed!",
                    "So you forgot your password and thought you're getting away with it? Nice try.");
        } else {
            showAlert("Access Denied", "Only humans can see passwords!");
        }
    }

    private void showAlert(String title, String content) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(content);
        infoAlert.showAndWait();
    }

    // Utility method for changing scenes
    private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        if (fxmlFile.equals("StudentDashboard.fxml")) {
            StudentDashboardController studentDashboardController = loader.getController();
            studentDashboardController.setStudent(this.studentDashboardController.getStudent());
            studentDashboardController.setUserName(this.studentDashboardController.getStudent().getName());
        }
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
