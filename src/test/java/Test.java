package src.test.java;

import java.util.ArrayList;

import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.courses.TextCourse;
import src.main.java.com.byteWise.courses.VideoCourse;
import src.main.java.com.byteWise.filesystem.Read_Write;

class Test {
    // directory for testing purposes
    public static void main(String[] args) {
        Read_Write.setFilePath();
    /*
    Run this code to write to the file, 
    then comment it out and uncomment the block starting from line 26 ,run to read from the file
    */        
        // Write to File
        
        Course course1 = new TextCourse("Java", "Java Programming", "100", "10", "Java Programming");
        Course course2 = new TextCourse("Python", "Python Programming", "100", "10", "Python Programming");
        Course course3 = new VideoCourse("JavaScript", "JavaScript Programming", "100", "10", "JavaScript Programming");
        Read_Write.WriteToCoursesFile(course1);
        Read_Write.WriteToCoursesFile(course3);
        Read_Write.WriteToCoursesFile(course2);

        // READ From File

        // ArrayList<Course> courses = Read_Write.ReadFromCoursesFile();
        // for (Course course : courses) {
        //     System.out.println("Course Details");
        //     System.out.print(course.getClass().getSimpleName()+" ");
        //     System.out.print(course.getCourseId()+" ");
        //     System.out.print(course.getCourseTitle()+" ");
        //     System.out.print(course.getDescription()+" ");
        //     System.out.println(course.getLink());
        
        // }
    }
}