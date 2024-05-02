package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Optional;

public class DiscoverController {

    private String username;

    @FXML
    private Text debug;

    @FXML
    private Button profileBtn, addBtn1, addBtn2, addBtn3, backBtn;


    private StudentDashboardController studentDashboardController;

    public void setStudentDashboardController(StudentDashboardController controller) {
    this.studentDashboardController = controller;
    setUserName();
    }

    // Retrieve the user's name from the UserData singleton
    public void setUserName() {
    if (studentDashboardController != null) {
        username = studentDashboardController.getUsername();
       // debug.setText(username);
    }
    }

    // getter for username
    public String getUsername() {
        return username;
    }

    // print the user's name "debug" 
    public void printDebug() {
        System.out.println("Debug: " + debug.getText());
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
                return "Username: " + username;
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


    // Handler for adding Course 1
    @FXML
    private void handleAddCourse1Action() throws IOException {
        addCourse("course1");
    }

    // Handler for adding Course 2
    @FXML
    private void handleAddCourse2Action() throws IOException {
        addCourse("course2");
    }

    // Handler for adding Course 3
    @FXML
    private void handleAddCourse3Action() throws IOException {
        addCourse("course3");
    }

    // Method to add a course
    private void addCourse(String courseId) {
        // Logic to add course to student's courses
        System.out.println("Course added: " + courseId);
        // Possible implementation could update a database or a local list
    }

    // Handler for Back to Dashboard button
    @FXML
    private void handleBackToDashboardAction() throws IOException {
        // debugging to check username
        System.out.println("Debug: " + debug.getText().trim());
        changeScene("StudentDashboard.fxml");
    }

    
    // Utility method for changing scenes
    private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        if (fxmlFile.equals("StudentDashboard.fxml")) {
            StudentDashboardController studentDashboardController = loader.getController();
            studentDashboardController.setDiscoverController(this);
        }
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
