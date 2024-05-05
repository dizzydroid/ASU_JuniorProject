package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class PendingController {
    @FXML
    private Button backBtn;

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
}
