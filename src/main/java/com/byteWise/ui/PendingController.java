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


public class PendingController {
    @FXML
    private Button backBtn;


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
    


}
