package src.main.java.com.byteWise.users;
import java.util.List;

import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.filesystem.Read_Write;

import java.util.ArrayList;
public class Instructor extends User {
    private List<Course> coursesTeaching;

    public Instructor(int id, String name) {
        super(id, name);
        this.coursesTeaching = new ArrayList<>();
        role = 1;
    }

    @Override
    public void displayDetails() {
        System.out.println("Instructor ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Courses Teaching:");
        for (Course course : coursesTeaching) {
            System.out.println(course.getCourseTitle());
        }
    }

    public void addCourse(Course course) throws CourseNotFoundException {
        if (!coursesTeaching.contains(course)) {
            coursesTeaching.add(course);
            Read_Write.writeToJson(this, this.getName());
            System.out.println("Added course to teach: " + course.getCourseTitle());
        } else if (coursesTeaching.contains(course)){
            throw new CourseNotFoundException("Can't add course. Course already teaching.");
        } else {
            throw new CourseNotFoundException("Can't add course. Course not found.");
        }
    }

    public void removeCourse(Course course) {
        if (coursesTeaching.contains(course)) {
            coursesTeaching.remove(course);
            Read_Write.writeToJson(this, this.getName());
            System.out.println("Removed course from teaching: " + course.getCourseTitle());
        } else {
            System.out.println("Can't remove course. Course not found.");
        }
    }
    
    public static class CourseNotFoundException extends Exception {
        public CourseNotFoundException(String message) {
            super(message);
        }
    }
}
