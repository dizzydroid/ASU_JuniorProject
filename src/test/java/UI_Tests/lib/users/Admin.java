package lib.users;
import lib.course.Course;
import lib.course.*;
import lib.users.User;
import lib.filesystem.*;

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

    public void deleteCourse(Course course) throws CourseNotFoundException {
        // Implementation for deleting a course
        System.out.println("Deleted course: " + course.getCourseTitle());
    }

    public void deleteUser(String userName) throws UserNotFoundException {
        try {Read_Write.deleteLineByUsername(userName);
        }
        catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        System.out.println("Deleted user: " + userName);
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
