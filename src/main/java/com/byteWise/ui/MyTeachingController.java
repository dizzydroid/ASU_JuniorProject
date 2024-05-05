package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.java.com.byteWise.courses.Course;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.input.MouseEvent;


public class MyTeachingController {
    @FXML
    private Button profileBtn, backBtn;
    
    @FXML
    private ListView<Course> coursesListView;


    private InstructorDashboardController instructorDashboardController;

    public void setInstructorDashboardController(InstructorDashboardController controller) {
        this.instructorDashboardController = controller;
        if (controller != null && controller.getInstructor() != null) {
            displayCourses(); // Ensure the controller and instructor are not null before attempting to display courses
        }
    }

    // Method to initialize the scene with courses
    @FXML
    public void initialize() {
        setupCourseListView();
        if (instructorDashboardController != null && instructorDashboardController.getInstructor() != null) {
            displayCourses(); // Safety check before displaying courses
        }
    }

    private void displayCourses() {
        coursesListView.getItems().clear();
        coursesListView.getItems().addAll(instructorDashboardController.getInstructor().getCourses());
    }

    
    private void setupCourseListView() {
        coursesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click
                Course selectedCourse = coursesListView.getSelectionModel().getSelectedItem();
                if (selectedCourse != null) {
                    System.out.println("Double-click detected on: " + selectedCourse.getCourseTitle()); // Debug output
    
                    try {
                        if ("How To: Corn Flakes".equals(selectedCourse.getCourseTitle())) {
                            System.out.println("Attempting to change scene to CornFlakes.fxml"); // Debug output
                            changeScene("CornFlakes.fxml"); // Change to the Corn Flakes course scene
                        } else if ("The Art of Managing Deadlines".equals(selectedCourse.getCourseTitle())) {
                            System.out.println("Attempting to change scene to ManagingDeadlines.fxml"); // Debug output
                            changeScene("DeadlineScene.fxml"); // Change to The Art of Managing Deadlines course scene
                        } else {
                            System.out.println("Redirecting to PendingScene.fxml for other courses"); // Debug output
                            changeScene("PendingScene.fxml"); // Default case for other courses
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        showAlert("Error", "Failed to load the course page.");
                    }
                } else {
                    System.out.println("No course selected"); // Debug output
                }
            }
        });
    }
    


    @FXML
    private void handleBackToDashboardAction() throws IOException {
        changeScene("InstructorDashboard.fxml");
    }

    // Profile button action that mirrors functionality in DiscoverController
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

        dialog.getDialogPane().getButtonTypes().addAll(usernameButtonType, passwordButtonType, signOutButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == usernameButtonType) {
                return "Username: " + instructorDashboardController.getInstructor().getName();
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
        if (fxmlFile.equals("InstructorDashboard.fxml")) {
            InstructorDashboardController instructorDashboardController = loader.getController();
            instructorDashboardController.setInstructor(this.instructorDashboardController.getInstructor());
            instructorDashboardController.setUserName(this.instructorDashboardController.getInstructor().getName());
         } // else if (fxmlFile.equals("CornFlakes.fxml")){
        //     CornController cornController = loader.getController();
        //     cornController.setInstructorDashboardController(instructorDashboardController);
        // } else if (fxmlFile.equals("DeadlineScene.fxml")){
        //     DeadlineController deadlineController = loader.getController();
        //     deadlineController.setInstructorDashboardController(instructorDashboardController);
        // } else if (fxmlFile.equals("PendingScene.fxml")){
        //     PendingController pendingController = loader.getController();
        //     pendingController.setInstructorDashboardController(instructorDashboardController);
        // }
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
