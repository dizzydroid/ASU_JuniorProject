package src.main.java.com.byteWise.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.awt.Desktop;
import javafx.geometry.Insets;

public class CornController {
    @FXML
    private Button profileBtn, backBtn, quizBtn;
    @FXML
    private Hyperlink vidLink;

    private StudentDashboardController studentDashboardController;
    private InstructorDashboardController instructorDashboardController;

    // Setter for student controller
    public void setStudentDashboardController(StudentDashboardController controller) {
        this.studentDashboardController = controller;
    }

    // Setter for instructor controller
    public void setInstructorDashboardController(InstructorDashboardController controller) {
        this.instructorDashboardController = controller;
    }

    @FXML
    public void openLink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=QvDrtGERbCw"));
    }

    @FXML
    public void handleQuizAction() {
        // Create a dialog
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Corn Flakes Quiz");
    
        // Setup buttons
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);
    
        // Create the quiz content as a GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
    
        ToggleGroup group1 = new ToggleGroup();
        RadioButton q1a = new RadioButton("Hens");
        RadioButton q1b = new RadioButton("Monkeys");
        RadioButton q1c = new RadioButton("Cows");
        RadioButton q1d = new RadioButton("Trees");
        q1c.setToggleGroup(group1);
        q1b.setToggleGroup(group1);
        q1a.setToggleGroup(group1);
        q1d.setToggleGroup(group1);
    
        ToggleGroup group2 = new ToggleGroup();
        RadioButton q2a = new RadioButton("before, bowl");
        RadioButton q2b = new RadioButton("after, bowl");
        RadioButton q2c = new RadioButton("before, floor");
        RadioButton q2d = new RadioButton("after, floor");
        q2a.setToggleGroup(group2);
        q2b.setToggleGroup(group2);
        q2c.setToggleGroup(group2);
        q2d.setToggleGroup(group2);
    
        ToggleGroup group3 = new ToggleGroup();
        RadioButton q3a = new RadioButton("dinner");
        RadioButton q3b = new RadioButton("breakfast");
        RadioButton q3c = new RadioButton("lunch");
        RadioButton q3d = new RadioButton("monday");
        q3b.setToggleGroup(group3);
        q3a.setToggleGroup(group3);
        q3c.setToggleGroup(group3);
        q3d.setToggleGroup(group3);
    
        Label question1 = new Label("1. The primary source of milk is (are):");
        question1.setStyle("-fx-font-weight: bold;");
        Label question2 = new Label("2. Cereal is placed ....... milk in the .......");
        question2.setStyle("-fx-font-weight: bold;");
        Label question3 = new Label("3. Corn Flakes are the lead singers in .......");
        question3.setStyle("-fx-font-weight: bold;");
    
        grid.add(question1, 0, 0);
        grid.add(q1a, 1, 0);
        grid.add(q1b, 1, 1);
        grid.add(q1c, 1, 2);
        grid.add(q1d, 1, 3);
    
        grid.add(question2, 0, 4);
        grid.add(q2a, 1, 4);
        grid.add(q2b, 1, 5);
        grid.add(q2c, 1, 6);
        grid.add(q2d, 1, 7);
    
        grid.add(question3, 0, 8);
        grid.add(q3a, 1, 8);
        grid.add(q3b, 1, 9);
        grid.add(q3c, 1, 10);
        grid.add(q3d, 1, 11);
    
        dialog.getDialogPane().setContent(grid);
    
        // Convert the result to a response when the submit button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                int score = 0;
                if (q1c.isSelected()) score++;
                if (q2a.isSelected()) score++;
                if (q3b.isSelected()) score++;
                return score;
            }
            return null;
        });
    
    
        Optional<Integer> result = dialog.showAndWait();
    
        result.ifPresent(score -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Result");
            alert.setHeaderText(null);
            alert.setContentText("You scored: " + score + "/3");
            alert.showAndWait();
        });
    }

    @FXML
    private void handleBackToDashboardAction() throws IOException {
        if (studentDashboardController != null) {
            changeScene("StudentDashboard.fxml");
        } else if (instructorDashboardController != null) {
            changeScene("InstructorDashboard.fxml");
        }
    }

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
