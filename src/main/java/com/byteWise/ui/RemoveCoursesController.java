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

public class RemoveCoursesController {
    @FXML
    private Button profileBtn, backBtn, removeBtn;

    @FXML
    private ListView<Course> coursesListView;

    private InstructorDashboardController instructorDashboardController;

    public void setInstructorDashboardController(InstructorDashboardController controller) {
        this.instructorDashboardController = controller;
        if (controller != null && controller.getInstructor() != null) {
            displayCourses();
        }
    }

    @FXML
    public void initialize() {
        if (instructorDashboardController != null && instructorDashboardController.getInstructor() != null) {
            displayCourses();
        }
    }

    private void displayCourses() {
        if (instructorDashboardController.getInstructor() != null) {
            coursesListView.setItems(FXCollections.observableArrayList(instructorDashboardController.getInstructor().getCourses()));
        }
    }

    @FXML
    private void handleBackToDashboardAction() throws IOException {
        changeScene("InstructorDashboard.fxml");
    }

    // Method to remove a selected course from the ListView and instructor's courses
    @FXML
    private void handleRemoveCourseAction() {
        Course selectedCourse = coursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                instructorDashboardController.getInstructor().removeCourse(selectedCourse);
                coursesListView.getItems().remove(selectedCourse);  // Update the ListView after successful removal
                showAlert("Success", "Course removed successfully.");
            } catch (Exception ex) {
                showAlert("Error", "Failed to update course data.");
            }
        } else {
            showAlert("Error", "No course selected!");
        }
    }
    
    @FXML
    private void handleProfileAction() {
        if (instructorDashboardController == null || instructorDashboardController.getInstructor() == null) {
            showAlert("Error", "No instructor data available.");
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
                return "Username: " + instructorDashboardController.getInstructor().getName();
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
        if (fxmlFile.equals("InstructorDashboard.fxml")) {
            InstructorDashboardController instructorDashboardController = loader.getController();
            instructorDashboardController.setInstructor(this.instructorDashboardController.getInstructor());
            instructorDashboardController.setUserName(this.instructorDashboardController.getInstructor().getName());
        }
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
