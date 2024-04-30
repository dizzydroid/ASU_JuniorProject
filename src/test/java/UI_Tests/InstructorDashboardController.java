import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class InstructorDashboardController {

    @FXML
    private Text userName;

    @FXML
    private Button addCoursesBtn, removeCoursesBtn, myTeachingBtn, profileBtn;

    public void setUserName(String name) {
        userName.setText(name);
    }

    @FXML
    private void handleAddCoursesAction() {
        // Code to switch to the Add Courses scene
    }

    @FXML
    private void handleRemoveCoursesAction() {
        // Code to switch to the Remove Courses scene
    }

    @FXML
    private void handleMyTeachingAction() {
        // Code to switch to the My Teaching scene
    }

    @FXML
    private void handleProfileAction() {
        // Code to switch to the Profile scene
    }
}
