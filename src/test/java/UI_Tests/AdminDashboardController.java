import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class AdminDashboardController {

    @FXML
    private Text userName;

    @FXML
    private Button manageUsersBtn, manageCoursesBtn, profileBtn;

    public void setUserName(String name) {
        userName.setText(name);
    }

    @FXML
    private void handleManageUsersAction() {
        // Code to switch to the Manage Users scene
    }

    @FXML
    private void handleManageCoursesAction() {
        // Code to switch to the Manage Courses scene
    }

    @FXML
    private void handleProfileAction() {
        // Code to switch to the Profile scene
    }
}
