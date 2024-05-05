package src.main.java.com.byteWise.ui;

import java.io.IOException;
import java.util.List;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.courses.TextCourse;
import src.main.java.com.byteWise.filesystem.Read_Write;
import src.main.java.com.byteWise.users.Instructor.CourseNotFoundException;

public class AddCoursesController {
     @FXML
    private Button profileBtn, addBtn1, addBtn2, addBtn3, backBtn;

    @FXML
    private Button allCoursesBtn;

    @FXML
    private ImageView studentIMG;

    private InstructorDashboardController instructorDashboardController;

    public void setInstructorDashboardController(InstructorDashboardController controller) {
        this.instructorDashboardController = controller;
        if (controller != null && controller.getInstructor() != null) {
            System.out.println("instructorDashboardController and instructor are properly set.");
        } else {
            System.out.println("instructorDashboardController or instructor is null!");
        }
    }
    
    // hardcoded courses
    private Course course1 = new TextCourse("C1", "How To: Corn Flakes",
            "A deep dive into the art of cereal preparation.", "Food");
    private Course course2 = new TextCourse("C2", "The Art of Managing Deadlines",
            "Learn strategies to manage deadlines effectively.", "Productivity");
    private Course course3 = new TextCourse("C3", "Untitled Course",
            "A course about something that hasn't been decided yet.", "Mystery");

    @FXML
    public void handleAddCourse1Action() {
        try {
            instructorDashboardController.getInstructor().addCourse(course1);
        } catch (CourseNotFoundException e) {
            showAlert("Enrollment Failed", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Failed to save course data.");
        }
    }

    @FXML
    public void handleAddCourse2Action() {
        try {
            instructorDashboardController.getInstructor().addCourse(course2);
        } catch (CourseNotFoundException e) {
            showAlert("Enrollment Failed", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Failed to save course data.");
        }
    }

    @FXML
    public void handleAddCourse3Action() {
        try {
            instructorDashboardController.getInstructor().addCourse(course3);
        } catch (CourseNotFoundException e) {
            showAlert("Enrollment Failed", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Failed to save course data.");
        }
    }

    @FXML
    public void handleAllCoursesAction() {
        List<Course> allCourses = Read_Write.ReadFromCoursesFile();
        showCoursesDialog(allCourses);
    }

    private void showCoursesDialog(List<Course> courses) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("All Courses");

        // Create a custom pane for ListView and buttons
        VBox vbox = new VBox(10);
        ListView<Course> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(courses));
        Button addButton = new Button("Add Selected Course");

        addButton.setOnAction(e -> {
            Course selectedCourse = listView.getSelectionModel().getSelectedItem();
            if (selectedCourse != null) {
                try {
                    instructorDashboardController.getInstructor().addCourse(selectedCourse);
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Course added successfully!");
                    infoAlert.showAndWait();
                } catch (CourseNotFoundException ex) { // This exception should be defined to handle duplicates or other issues
                    System.out.println("Attempted to add duplicate course: " + selectedCourse);
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Course already added or another error occurred: " + ex.getMessage());
                    errorAlert.showAndWait();
                }
            } else {
                System.out.println("No course selected");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "No course selected!");
                errorAlert.showAndWait();
            }
        });
        
        

        vbox.getChildren().addAll(listView, addButton);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    @FXML
    public void handleBackToDashboardAction() throws IOException {
        changeScene("InstructorDashboard.fxml");
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
