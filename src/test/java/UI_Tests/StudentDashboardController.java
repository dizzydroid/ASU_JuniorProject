import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class StudentDashboardController {

    @FXML
    private Text userName;

    @FXML
    private Button myLearningBtn, discoverBtn, giveUpBtn, profileBtn;

    // Method to set the user's name on the dashboard
    public void setUserName(String name) {
        userName.setText(name);
    }

    // Example method for handling the My Learning button action
    @FXML
    private void handleMyLearningAction() throws IOException {
        // Code to switch to the My Learning scene
        changeScene("MyLearningScene.fxml");
    }

    // Example method for handling the Discover button action
    @FXML
    private void handleDiscoverAction() throws IOException {
        // Code to switch to the Discover scene
        changeScene("DiscoverScene.fxml");
    }

    // Example method for handling the Give Up button action
    @FXML
    private void handleGiveUpAction() throws IOException {
        // Code to switch to an appropriate scene
        changeScene("GiveUpScene.fxml");
    }

    // Example method for handling the Profile button action
    @FXML
    private void handleProfileAction() throws IOException {
        // Code to switch to the Profile scene
        changeScene("ProfileScene.fxml");
    }

    // Utility method for changing scenes
    private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Stage stage = (Stage) userName.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }
}
