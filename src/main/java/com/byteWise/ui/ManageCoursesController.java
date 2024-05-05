package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.users.Admin;

public class ManageCoursesController {
    @FXML
    private Button profileBtn, backBtn , removeUserBtn , addUserBtn;
    @FXML
    private Text selectedUser;
    @FXML
    private ListView<Course> coursesListView;
}
