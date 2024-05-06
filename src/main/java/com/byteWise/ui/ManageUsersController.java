package src.main.java.com.byteWise.ui;

import javafx.collections.FXCollections;
import java.io.IOException;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.com.byteWise.filesystem.Read_Write;
import src.main.java.com.byteWise.users.Admin.UserAlreadyExistsException;
import src.main.java.com.byteWise.users.Admin.UserNotFoundException;

public class ManageUsersController {
   @FXML
    private Button profileBtn, backBtn , removeUserBtn , addUserBtn;
    @FXML
    private Text selectedUser;
    @FXML
    private ListView<String> usersListView;
    private List<String> users = new ArrayList<String>();

    private AdminDashboardController adminDashboardController;
    @FXML
    public void initialize(){
        populateListView();
        updateSelectedUser();
    }

    public void setManageUsersController(AdminDashboardController controller){
        this.adminDashboardController = controller;
        if (controller != null && controller.getAdmin() != null) {
            System.out.println("admindashboardc and admin are properly set.");
        } else {
            System.out.println("admindashboardc or admin is null!");
        }
    }

    private void populateListView(){
        usersListView.getItems().clear();
        Read_Write.readUsersFromCSV(users);
        ObservableList<String> elements = FXCollections.observableArrayList(users);
        try{
        usersListView.setItems(elements); 
        }
        catch(Exception e){
           System.out.println("error here"); //handle later
       } 
    }
    private void updateSelectedUser(){
        usersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    // Update UI element with the selected user information
                    if (newValue != null) {
                        selectedUser.setText(usersListView.getSelectionModel().getSelectedItem());
                    }
                }
        });
    }
    
    @FXML
    private void handleRemoveUserAction(){
        String username = usersListView.getSelectionModel().getSelectedItem();
        if(username!=null){
            try{
            adminDashboardController.getAdmin().deleteUser(username);
            usersListView.getItems().remove(username);
            System.out.println("User Removed Successfully!");
            showAlert("Success","User Removed Successfully!");
             }
            catch(UserNotFoundException e){
            showAlert("Error","User Not Found");
            }
         }
        else{
            showAlert("Error","No User Selected!");
        }
    }

    @FXML
    private void handleAddUserAction(){
            // Dialog persondialog = new PersonDialog();
            // Optional<Person> result = persondialog. showAndWait();
            Dialog<String> dialog = new Dialog<>(); 
            dialog.setTitle("Add New User");
            
            // Create layout pane
            GridPane grid = new GridPane();
            //grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setHgap(10);
            grid.setVgap(10);

            // Create labels and text fields
            Label usernameLabel = new Label("Username:");
            TextField usernameField = new TextField();
            Label passwordLabel = new Label("Password:"); 
            TextField passwordField = new TextField(); // --> passwordfield better?
            Label roleLabel = new Label("Role:");
            TextField roleField = new TextField();

            // Add elements to the grid pane
            grid.add(usernameLabel, 0, 0);
            grid.add(passwordLabel, 0, 1);
            grid.add(roleLabel, 0, 2);
            grid.add(usernameField, 1, 0);
            grid.add(passwordField, 1, 1);
            grid.add(roleField, 1, 2);

            // Add buttons to the dialog
            ButtonType addButton = new ButtonType("Add User",ButtonData.OTHER);
            ButtonType cancelButton = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton){
                    if(!(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleField.getText().isEmpty())){
                        int role = Integer.parseInt(roleField.getText());
                        if (!(role==0 || role==1 || role==2)){
                            return "Invalid Role";
                        }
                        try {
                            adminDashboardController.getAdmin().createUser(usernameField.getText(),passwordField.getText(),role);
                            if(role!=2){
                            usersListView.getItems().add(usernameField.getText());
                            System.out.println("User Added Successfully!"); // --> add alert
                            return "User Added Successfully!";
                            }
                        } catch (UserAlreadyExistsException e) {
                            System.out.println("User Already Exists!");
                            return "User Already Exists!";
                        }
                    }
                    else {
                         System.out.println("Please fill all fields");      // -->add alert
                         return "Please Fill All Fields";
                    }
                }
                else if (dialogButton == cancelButton){
                    System.out.println("Cancelled"); // --> prints twice
                    return null;
                }
                return null;
            });
            dialog.showAndWait().ifPresent(result -> showAlert("Information", result));
    }

    @FXML
    private void handleBackToDashboardAction() throws IOException{
        changeScene("AdminDashboard.fxml");
    }

    @FXML
    private void handleProfileAction() {
        if (adminDashboardController == null || adminDashboardController.getAdmin() == null) {
            showAlert("Error", "No Admin data available.");
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Profile Options");

        ButtonType usernameButtonType = new ButtonType("View Username", ButtonData.OTHER);
        ButtonType passwordButtonType = new ButtonType("View Password", ButtonData.OTHER);
        ButtonType signOutButtonType = new ButtonType("Sign Out", ButtonData.OTHER);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(usernameButtonType, passwordButtonType, signOutButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == usernameButtonType) {
                return "Username: " + adminDashboardController.getAdmin().getName();
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

    private void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        if (fxmlFile.equals("AdminDashboard.fxml")) {
            AdminDashboardController adminDashboardController = loader.getController();
            adminDashboardController.setAdmin(this.adminDashboardController.getAdmin());
            adminDashboardController.setUserName(this.adminDashboardController.getAdmin().getName());
        }
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }     
 
}
