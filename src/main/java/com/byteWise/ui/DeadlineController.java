package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Optional;


public class DeadlineController {
    @FXML
    private Button profileBtn, backBtn;


    private StudentDashboardController studentDashboardController;
    private InstructorDashboardController instructorDashboardController;

    public void setStudentDashboardController(StudentDashboardController controller) {
        this.studentDashboardController = controller;
        if (controller != null && controller.getStudent() != null) {
            System.out.println("StudentDashboardController and student are properly set.");
        } else {
            System.out.println("StudentDashboardController or student is null!");
        }
    }

    // Setter for instructor controller
    public void setInstructorDashboardController(InstructorDashboardController controller) {
        this.instructorDashboardController = controller;
        if (controller != null && controller.getInstructor() != null) {
            System.out.println("InstructorDashboardController and instructor are properly set.");
        } else {
            System.out.println("InstructorDashboardController or instructor is null!");
        }
    }


    @FXML
    private void handleBackToDashboardAction() throws IOException {
        if (studentDashboardController != null) {
            changeScene("StudentDashboard.fxml");
        } else if (instructorDashboardController != null) {
            changeScene("InstructorDashboard.fxml");
        }
    }


     // Utility method for changing scenes
     private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();

        if (fxmlFile.equals("StudentDashboard.fxml") && studentDashboardController != null) {
            StudentDashboardController controller = loader.getController();
            controller.setStudent(studentDashboardController.getStudent());
            controller.setUserName(studentDashboardController.getStudent().getName());
        } else if (fxmlFile.equals("InstructorDashboard.fxml") && instructorDashboardController != null) {
            InstructorDashboardController controller = loader.getController();
            controller.setInstructor(instructorDashboardController.getInstructor());
            controller.setUserName(instructorDashboardController.getInstructor().getName());
        }

        stage.setScene(scene);
    }
    

    // Profile button action that mirrors functionality in DiscoverController
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
                return "Username: " + (studentDashboardController != null ? studentDashboardController.getStudent().getName() : instructorDashboardController.getInstructor().getName());
            } else if (dialogButton == passwordButtonType) {
                performSecurityCheck();
                return null;
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
