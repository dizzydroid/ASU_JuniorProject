package src.main.java.com.byteWise.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.java.com.byteWise.courses.Course;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.awt.Desktop;


public class DeadlineController {
    @FXML
    private Button profileBtn, backBtn;


    private StudentDashboardController studentDashboardController;

    public void setStudentDashboardController(StudentDashboardController controller) {
        this.studentDashboardController = controller;
        if (controller != null && controller.getStudent() != null) {
            System.out.println("StudentDashboardController and student are properly set.");
        } else {
            System.out.println("StudentDashboardController or student is null!");
        }
    }


    @FXML
    private void handleBackToDashboardAction() throws IOException {
        changeScene("StudentDashboard.fxml");
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
    

    // Profile button action that mirrors functionality in DiscoverController
    @FXML
    private void handleProfileAction() {
    
        if (studentDashboardController == null || studentDashboardController.getStudent() == null) {
            showAlert("Error", "No student data available.");
            return;
        }
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

}
