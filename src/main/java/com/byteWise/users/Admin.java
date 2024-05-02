package src.main.java.com.byteWise.users;

import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.filesystem.Read_Write;

public class Admin extends User {
    public Admin(int id, String name) {
        super(id, name);
        role = 2;
    }

    @Override
    public void displayDetails() {
        System.out.println("Admin ID: " + getId());
        System.out.println("Name: " + getName());
    }

    public void createUser(String userName,String password,int role) throws UserAlreadyExistsException {
        try {
            Read_Write.Signup(userName, password, role);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("User already exists");
        }
        System.out.println("Created user: " +userName);
    }

    public void deleteUser(String userName) throws UserNotFoundException {
        try {Read_Write.deleteLineByUsername(userName);
        }
        catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        System.out.println("Deleted user: " + userName);
    }
    
    public void createCourse(Course course){
        Read_Write.WriteToCoursesFile(course);
        System.out.println("Created course: " + course.getCourseTitle());
    }

    public void deleteCourse(Course course){
        Read_Write.deleteCourse(course);
        System.out.println("Deleted course: " + course.getCourseTitle());
    }

    public static class CourseNotFoundException extends Exception {
        public CourseNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}
