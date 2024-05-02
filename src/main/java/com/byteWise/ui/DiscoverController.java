package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Optional;

public class DiscoverController {

    @FXML
    private Button profileBtn, addBtn1, addBtn2, addBtn3, backBtn;

    private StudentDashboardController studentDashboardController;

    public void setStudentDashboardController(StudentDashboardController controller) {
        this.studentDashboardController = controller;
    }

    @FXML
public void handleAddCourse1Action() {
    addCourse("course1");
    System.out.println("Course 1 added successfully.");
}

@FXML
public void handleAddCourse2Action() {
    addCourse("course2");
    System.out.println("Course 2 added successfully.");
}

@FXML
public void handleAddCourse3Action() {
    addCourse("course3");
    System.out.println("Course 3 added successfully.");
}

private void addCourse(String courseId) {
    // Logic to add the course to the student's registered courses
    System.out.println("Adding course: " + courseId);
    // Potentially update a list or database here
}

@FXML
public void handleBackToDashboardAction() throws IOException {
    changeScene("StudentDashboard.fxml");
}


    // Handler for the Profile button
    @FXML
    private void handleProfileAction() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Profile Options");

        ButtonType usernameButtonType = new ButtonType("View Username", ButtonData.OTHER);
        ButtonType passwordButtonType = new ButtonType("View Password", ButtonData.OTHER);
        ButtonType signOutButtonType = new ButtonType("Sign Out", ButtonData.OTHER);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(usernameButtonType, passwordButtonType, signOutButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == usernameButtonType) {
                return "Username: " + studentDashboardController.getStudent().getName();
            } else if (dialogButton == passwordButtonType) {
                performSecurityCheck();
                return null;  // Don't close the dialog on this option
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
            showAlert("Password Revealed!", "So you forgot your password and thought you're getting away with it? Nice try.");
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
        studentDashboardController.setStudent(this.studentDashboardController.getStudent()); // Pass the student object back
        studentDashboardController.setUserName(this.studentDashboardController.getStudent().getName()); // Re-set the username
    }
    Stage stage = (Stage) backBtn.getScene().getWindow();
    stage.setScene(scene);
}

}
