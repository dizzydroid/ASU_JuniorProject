package src.main.java.com.byteWise.ui;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.com.byteWise.users.Instructor;
import src.main.java.com.byteWise.users.Student;
import src.main.java.com.byteWise.filesystem.Read_Write;
import src.main.java.com.byteWise.filesystem.Read_Write.UserAlreadyExistsException;
import src.main.java.com.byteWise.filesystem.Read_Write.UserNotFoundException;
import src.main.java.com.byteWise.users.User;
// import lib.users.Instructor;
// import lib.users.Student;
// import lib.filesystem.Read_Write;
// import lib.filesystem.Read_Write.UserAlreadyExistsException;
// import lib.filesystem.Read_Write.UserNotFoundException;
// import lib.users.User;

public class SignUpDetailsController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text feedbackText;

    private String userType;

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void initUserType(String userType) {
        this.userType = userType;
    }

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            feedbackText.setText("Username and password cannot be empty.");
            return;
        }

        try {
            User newUser;
            if ("Student".equals(userType)) {
                newUser = new Student(Read_Write.generateId(), username);
                Read_Write.Signup(username, password, 0); // Assuming this registers the user
              // Read_Write.initializeJSON(newUser); // Serialize the newly created user -> FIXME: causes error
                feedbackText.setText("New student account created successfully!");
                redirectToSignIn();
            } else if ("Instructor".equals(userType)) {
                newUser = new Instructor(Read_Write.generateId(), username);
                Read_Write.Signup(username, password, 1); // Assuming this registers the user
              //  Read_Write.initializeJSON(newUser); // Serialize the newly created user -> FIXME: causes error
                feedbackText.setText("New instructor account created successfully!");
                redirectToSignIn();
            } else {
                feedbackText.setText("User type is not recognized.");
            }
        } catch (UserAlreadyExistsException e) {
            feedbackText.setText("Account creation failed: " + e.getMessage());
        } catch (IOException iex) {
            feedbackText.setText("An error occurred: " + iex.getMessage());
            iex.printStackTrace();
        }
    }
    

    private void redirectToSignIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./SignInScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
