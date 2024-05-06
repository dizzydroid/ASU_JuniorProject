package src.main.java.com.byteWise.ui;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.main.java.com.byteWise.filesystem.Read_Write;

public class WelcomeSceneController {

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    // ... (other @FXML annotated UI elements)

    @FXML
    public void initialize() {
        // This method is called after the FXML is loaded and elements are injected
        // Perform any initialization tasks here.
        System.out.println("WelcomeSceneController initialized!"); // Print a message to the console (debugging purposes)
    }

   @FXML
    private void handleSignUpButtonClick() throws IOException {
        // Load the sign-up scene FXML
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("./UserTypeSelection.fxml"));
        Scene signUpScene = new Scene(signUpRoot);

        // Get the current stage and switch scenes
        Stage window = (Stage) signUpButton.getScene().getWindow(); 
        window.setScene(signUpScene);
    }

    @FXML
    private void handleSignInButtonClick() throws IOException {
        // Load the sign-in scene FXML
        Parent signInRoot = FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Get the current stage and switch scenes
        Stage window = (Stage) signInButton.getScene().getWindow();
        window.setScene(signInScene);
    }
}
