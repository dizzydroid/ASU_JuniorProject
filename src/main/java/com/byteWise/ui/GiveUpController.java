package src.main.java.com.byteWise.ui;

public class GiveUpController {
    private StudentDashboardController studentDashboardController;

    public void setStudentDashboardController(StudentDashboardController controller) {
        this.studentDashboardController = controller;
        if (controller != null && controller.getStudent() != null) {
         //   displayCourses(); // Ensure the controller and student are not null before attempting to display courses
        }
    }
}
