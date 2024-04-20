package src.main.java.com.byteWise.users;
import src.main.java.com.byteWise.courses.Course;

public class Admin extends User {
    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void displayDetails() {
        System.out.println("Admin ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
    }

    public void createUser(User user) throws UserAlreadyExistsException {
        // Implementation for creating a user
        System.out.println("Created user: " + user.getName());
    }

    public void deleteCourse(Course course) throws CourseNotFoundException {
        // Implementation for deleting a course
        System.out.println("Deleted course: " + course.getCourseTitle());
    }

    public void deleteUser(User user) throws UserNotFoundException {
        // Implementation for deleting a user
        System.out.println("Deleted user: " + user.getName());
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
