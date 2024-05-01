package src.main.java.com.byteWise.ui;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UserTypeSelectionController {
    
    @FXML
    private Button studentButton;
    private Button instructorButton;

    @FXML
    private void handleStudentButtonAction() throws IOException {
        loadSignUpDetails("Student");
    }

    @FXML
    private void handleInstructorButtonAction() throws IOException {
        loadSignUpDetails("Instructor");
    }

    private void loadSignUpDetails(String userType) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpDetails.fxml"));
        Parent root = loader.load();

        SignUpDetailsController signUpDetailsController = loader.getController();
        signUpDetailsController.setUserType(userType);

        Stage stage = (Stage) studentButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        //stage.show();
    }
}
