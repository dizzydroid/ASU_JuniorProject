package lib.users;
import java.util.List;
import lib.course.*;
import lib.filesystem.*;

import java.util.ArrayList;
public class Student extends User {
    private List<Course> enrolledCourses;
    public Student(int id, String name) {
        super(id, name);
        this.enrolledCourses = new ArrayList<>();
        role = 0;
    }

    @Override
    public void displayDetails() {
        System.out.println("Student ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Enrolled Courses:");
        for (Course course : enrolledCourses) {
            course.getCourseTitle();
        }
    }

    public void enrollInCourse(Course course) throws CourseNotFoundException {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            Read_Write.writeToJson(this);  // Use adjusted writeToJson method.
            System.out.println("Enrolled in course: " + course.getCourseTitle());
        } else {
            throw new CourseNotFoundException("Can't enroll in course. Course already enrolled.");
        }
    }
    

    public void dropCourse(Course course) throws CourseNotFoundException{
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            Read_Write.writeToJson(this);
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