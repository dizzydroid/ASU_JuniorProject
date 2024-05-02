package src.main.java.com.byteWise.ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.com.byteWise.users.Student;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import java.util.Optional;

public class StudentDashboardController {
    private static StudentDashboardController instance;
      private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public static StudentDashboardController getInstance() {
        if (instance == null) {
            instance = new StudentDashboardController();
        }
        return instance;
    }
    
    

    @FXML
    private Text userName;

    @FXML
    private Button myLearningBtn, discoverBtn, giveUpBtn, profileBtn;

    
    // Method to set the user's name on the dashboard
    public void setUserName(String name) {
        userName.setText(name);
        setUserNameInUserData(); // Add this line to set the username in UserData singleton
    }

    // getter for username
    public String getUsername() {
        return userName.getText();
    }

    // Method to set the user's name in the UserData singleton
    private void setUserNameInUserData() {
        UserData.getInstance().setUsername(userName.getText());
    }

    // Method for handling the My Learning button action
    @FXML
    private void handleMyLearningAction() throws IOException {
        changeScene("MyLearning.fxml");
    }

    // Method for handling the Discover button action
    @FXML
    private void handleDiscoverAction() throws IOException {
        changeScene("DiscoverScene.fxml");
    }

    // Method for handling the Give Up button action
    @FXML
    private void handleGiveUpAction() throws IOException {
        changeScene("GiveUpScene.fxml");
    }

    // Method for handling the Profile button action
    @FXML
    private void handleProfileAction() {
        // debugging, try printing student object attributes
      //  System.out.println("Student: " + student.getName());
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Profile Options");

        ButtonType usernameButtonType = new ButtonType("View Username", ButtonData.OTHER);
        ButtonType passwordButtonType = new ButtonType("View Password", ButtonData.OTHER);
        ButtonType signOutButtonType = new ButtonType("Sign Out", ButtonData.OTHER);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(usernameButtonType, passwordButtonType, signOutButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == usernameButtonType) {
                return "Username: " + userName.getText();
            } else if (dialogButton == passwordButtonType) {
                performSecurityCheck();
                return null;  // Don't close the dialog on this option
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

    // private void changeScene(String fxmlFile) throws IOException {
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    //     Stage stage = (Stage) userName.getScene().getWindow();
    //     Scene scene = new Scene(loader.load());
    //     stage.setScene(scene);
    // }

    private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        if (fxmlFile.equals("DiscoverScene.fxml")) {
            DiscoverController discoverController = loader.getController();
            discoverController.setStudentDashboardController(this);
        }
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setScene(scene);
    }
    
}
