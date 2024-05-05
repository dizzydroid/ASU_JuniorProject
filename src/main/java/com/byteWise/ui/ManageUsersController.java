package src.main.java.com.byteWise.ui;

import javafx.collections.FXCollections;
import java.io.IOException;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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

    AdminDashboardController adminDashboardController;
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

    public void populateListView(){
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
    public void updateSelectedUser(){
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
    public void handleRemoveUserAction(){
        String username = usersListView.getSelectionModel().getSelectedItem();
        try{
        adminDashboardController.getAdmin().deleteUser(username);
        usersListView.getItems().remove(username);
        }
        catch(UserNotFoundException e){
            e.getStackTrace(); //handle later
        }
        
    }

    @FXML
    public void handleAddUserACtion(){
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
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton){
                    if(!(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleField.getText().isEmpty())){
                        int role = Integer.parseInt(roleField.getText());
                        try {
                            adminDashboardController.getAdmin().createUser(usernameField.getText(),passwordField.getText(),role);
                            if(role!=2){
                            usersListView.getItems().add(usernameField.getText());
                            }
                        } catch (UserAlreadyExistsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            
                        }
                        System.out.println("User Added Successfully!"); // --> add alert
                        return "User Added Successfully!";
                    }
                    else {
                         System.out.println("Please fill all fields");      // -->add alert
                         return "Please fill all fields";
                    }
                }
                else if (dialogButton == cancelButton){
                    System.out.println("Cancelled"); // --> prints twice
                    return null;
                }
                return null;
            });
            // ((ButtonBase) dialog.getDialogPane().lookupButton(addButton)).setOnAction(event1 -> {
            //     // Handle add button click (e.g., process username and password)
            //     String username = usernameField.getText();
            //     String password = passwordField.getText();
            //     // Perform your action here, e.g., save user data to database or file
            //     System.out.println("Adding user: " + username + " with password: " + password);
            //     return "hi";
            // });
            
            // Set content and show the dialog
            dialog.getDialogPane().setContent(grid);
            dialog.showAndWait();
        
    }

    @FXML
    public void handleBackToDashboardAction(){

    }

    @FXML
    public void handleProfileAction(){

    }
        
 
}
