package src.main.java.com.byteWise.ui;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.com.byteWise.users.Admin;

public class AdminDashboardController {
    private static AdminDashboardController instance;

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public Admin getAdmin() {
        return admin;
    }

    @FXML
    private Text userName;

    @FXML
    private Button manageUsersBtn, manageCoursesBtn, profileBtn;

    @FXML
    public void initialize(){
        Adjustments.adjustUserFontSize(userName);
    }

    public void setUserName(String name) {
        userName.setText(name);
    }

    public static AdminDashboardController getInstance() {
        if (instance == null) {
            instance = new AdminDashboardController();
        }
        return instance;
    }
    

    @FXML
    private void handleManageUsersAction() throws IOException {
        changeScene("ManageUsers.fxml");
    }

    @FXML
    private void handleManageCoursesAction() throws IOException {
        changeScene("ManageCourses.fxml");
    }

    @FXML
    private void handleProfileAction() {
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
                    changeScene("./welcome_scene.fxml");
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

    private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        if (fxmlFile.equals("ManageUsers.fxml")) {
            ManageUsersController manageuser = loader.getController();
            manageuser.setManageUsersController(this);
         }
         else if (fxmlFile.equals("ManageCourses.fxml")){
            ManageCoursesController managecourses = loader.getController();
            managecourses.setManageCoursesController(this);
        }
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setScene(scene);
    }
}

