package src.main.java.com.byteWise.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.courses.TextCourse;
import src.main.java.com.byteWise.courses.VideoCourse;
import src.main.java.com.byteWise.filesystem.Read_Write;
import src.main.java.com.byteWise.users.Admin.CourseAlreadyExists;

public class ManageCoursesController {
    @FXML
    private Button profileBtn, backBtn , removeCourseBtn , addCourseBtn;
    @FXML
    private Text selectedCourse;
    @FXML
    private ListView<Course> coursesListView;
    private List<Course> courses = new ArrayList<Course>();
    private AdminDashboardController adminDashboardController;
    
    @FXML
    public void initialize(){
        populateListView();
        updateSelectedCourse();
        
    }

    // public static void setLastCourseID(int lastCourseID) {
    //     ManageCoursesController.lastCourseID = lastCourseID;
    // }
    public void setManageCoursesController(AdminDashboardController controller){
        adminDashboardController = controller;
    }
    private void populateListView(){
        courses = Read_Write.ReadFromCoursesFile();
        coursesListView.getItems().clear();
        ObservableList<Course> elements = FXCollections.observableArrayList(courses);
        try{
        coursesListView.setItems(elements); 
        }
        catch(Exception e){
           e.getStackTrace();
       } 
    }
    private void updateSelectedCourse(){
        coursesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>(){
                @Override
                public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
                    if (newValue != null) {
                        selectedCourse.setText(newValue.toString());
                        if(Arrays.asList("C1", "C2","C3").contains(newValue.getCourseId())){
                            removeCourseBtn.setDisable(true);
                        }
                        else{
                            removeCourseBtn.setDisable(false);
                        }
                    }
                }
        });
    }

    @FXML
    private void handleRemoveCourseAction(){
        Course course = coursesListView.getSelectionModel().getSelectedItem();
        if (course!=null){
        adminDashboardController.getAdmin().deleteCourse(course);
        coursesListView.getItems().remove(course);
        System.out.println("Course Deleted Successfully!");
        showAlert("Success","Course Removed Successfully!");
        }
        else{
            showAlert("Error","No Course Selected!");
        }
    }

    @FXML
    private void handleAddCourseAction(){
            // Dialog persondialog = new PersonDialog();
            // Optional<Person> result = persondialog. showAndWait();
            Dialog<String> dialog = new Dialog<>(); 
            dialog.setTitle("Add New Course");
            
            // Create layout pane
            GridPane grid = new GridPane();
            //grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setHgap(10);
            grid.setVgap(10);

            // Create labels and text fields
            Label typeLabel = new Label("Course Type*:");
            TextField typeField = new TextField();
            Label titleLabel = new Label("Course Title*:"); 
            TextField titleField = new TextField(); 
            Label descripLabel = new Label("Course Description*:");
            TextField descripField = new TextField();
            Label linkLabel = new Label("Course Link:");
            TextField linkField = new TextField();

            // Add elements to the grid pane
            grid.add(typeLabel, 0, 0);
            grid.add(titleLabel, 0, 1);
            grid.add(descripLabel, 0, 2);
            grid.add(linkLabel, 0, 3);
            grid.add(typeField, 1, 0);
            grid.add(titleField, 1, 1);
            grid.add(descripField, 1, 2);
            grid.add(linkField, 1, 3);

            // Add buttons to the dialog
            ButtonType addButton = new ButtonType("Add Course",ButtonData.OTHER);
            ButtonType cancelButton = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton){
                    if(!(typeField.getText().isEmpty() || titleField.getText().isEmpty() || descripField.getText().isEmpty())){
                        String ID = "C" + String.valueOf(Read_Write.generateCourseID());
                        String Tag = null;
                        Course course;
                        if(typeField.getText().equals("TextCourse")){
                            course = new TextCourse(ID, titleField.getText(), descripField.getText(), Tag, linkField.getText());
                            try {
                                adminDashboardController.getAdmin().createCourse(course);
                                coursesListView.getItems().add(course);
                                System.out.println("Course Added Successfully!"); 
                                return "Course Added Successfully!";
                            } catch (CourseAlreadyExists e) {
                                System.out.println("CourseAlreadyExists");
                                return "Course Already Exists!";
                            }
                            
                        }
                        else if(typeField.getText().equals("VideoCourse")){
                            course = new VideoCourse(ID, titleField.getText(), descripField.getText(),Tag, linkField.getText());
                            try {
                                adminDashboardController.getAdmin().createCourse(course);
                                coursesListView.getItems().add(course);
                                System.out.println("Course Added Successfully!");
                                return "Course Added Successfully!";
                            } catch (CourseAlreadyExists e) {
                                System.out.println("CourseAlreadyExists");
                                return "Course Already Exists!";
                            }
                        }
                        else{
                            System.out.println("Undefined Type"); // --> add alert
                            return "Invalid Course Type";
                        }
                        
                    }
                    else {
                         System.out.println("Please fill all required fields");      // -->add alert
                         return "Please Fill All Required Fields";
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
    private void handleSortAction(){
        courses = Read_Write.ReadFromCoursesFile();
        Course[] courseArr = courses.toArray(new Course[courses.size()]);
        Arrays.sort(courseArr);
        coursesListView.getItems().clear();
        ObservableList<Course> sortedcourses = FXCollections.observableArrayList(courseArr);
        coursesListView.setItems(sortedcourses); 
        System.out.println("Sorted!");
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


      /*this works to disable selection*/
        // System.out.println(coursesListView.getSelectionModel().isEmpty());
        // System.out.println(coursesListView.getSelectionModel().getSelectedIndex());
        // coursesListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        //     @Override
        //     public void changed(ObservableValue<? extends Number> observableValue, Number oldv, Number newv) {
        //         System.out.println(coursesListView.getSelectionModel().isEmpty());
        //         System.out.println(coursesListView.getSelectionModel().getSelectedIndex());
        //        // coursesListView.getSelectionModel().clearSelection();
        //         if (newv.intValue() == 1) {
        //             coursesListView.getSelectionModel().select(-1);
        //         };
        //     }
        // });