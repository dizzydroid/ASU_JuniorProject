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

    @FXML
    private void handleStudentSelection() throws IOException {
        redirectToSignUpDetails("Student");
    }

    @FXML
    private void handleInstructorSelection() throws IOException {
        redirectToSignUpDetails("Instructor");
    }

    private void redirectToSignUpDetails(String userType) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpDetails.fxml"));
        Parent detailsRoot = loader.load();
        SignUpDetailsController detailsController = loader.getController();

        if (userType.equals("Student")) {
            detailsController.setStudentUserType();
        } else if (userType.equals("Instructor")) {
            detailsController.setInstructorUserType();
        }

        Scene detailsScene = new Scene(detailsRoot);
        Stage window = (Stage) studentButton.getScene().getWindow();
        window.setScene(detailsScene);
        window.show();
    }
}

