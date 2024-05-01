package src.main.java.com.byteWise.users;
import java.util.List;

import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.filesystem.Read_Write;

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
            course.getCourseTitle();
        }
    }

    public void enrollInCourse(Course course) throws CourseNotFoundException {
        if (!courses.contains(course)) {
            courses.add(course);
            Read_Write.writeToJson(this, this.getName());
            System.out.println("Enrolled in course: " + course.getCourseTitle());
        } else if (courses.contains(course)){
            throw new CourseNotFoundException("Can't enroll in course. Course already enrolled.");
        } else {
            throw new CourseNotFoundException("Can't enroll in course. Course not found.");
        }
    }

    public void dropCourse(Course course) throws CourseNotFoundException{
        if (courses.contains(course)) {
            courses.remove(course);
            Read_Write.writeToJson(this, this.getName());
            System.out.println("Dropped course: " + course.getCourseTitle());
        } else {
            throw new CourseNotFoundException("Can't drop course. Course not found.");
        }
    }

    public static class CourseNotFoundException extends Exception {
        public CourseNotFoundException(String message) {
            super(message);
        }
    }
}
