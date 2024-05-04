package src.main.java.com.byteWise.users;
import java.util.List;

import javafx.scene.control.Alert;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.filesystem.Read_Write;

import java.io.IOException;
import java.util.ArrayList;

public class Student extends User {
    private List<Course> courses;

    public Student(int id, String name) {
        super(id, name);
        this.courses = new ArrayList<>();
        role = 0;
    }

    @Override
    public void displayDetails() {
        System.out.println("Student ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Enrolled Courses:");
        for (Course course : courses) {
            System.out.println(course.getCourseTitle());
        }
    }

    public void enrollInCourse(Course course) throws IOException, CourseNotFoundException {
        if (courses.stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
            // If the course is already enrolled
            throw new CourseNotFoundException("Already enrolled in course: " + course.getCourseTitle());
        } else {
            courses.add(course);
            Read_Write.writeToJson(this);  // Save changes to JSON
            showAlert("Success", "Enrolled in course: " + course.getCourseTitle());
        }
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void dropCourse(Course course) throws CourseNotFoundException {
        if (courses.contains(course)) {
            courses.remove(course);
            Read_Write.writeToJson(this);
            System.out.println("Dropped course: " + course.getCourseTitle());
        } else {
            throw new CourseNotFoundException("Can't drop course. Course not found.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class CourseNotFoundException extends Exception {
        public CourseNotFoundException(String message) {
            super(message);
        }
    }
}
