package src.main.java.com.byteWise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.courses.TextCourse;

public class Main {
  
        public static void main(String[] args) {
            List<Course> courses = new ArrayList<>();
            Course textCourse = new TextCourse("1", "Aext Course", "Learn about text courses", "text",
                    "https://www.textcourse.com");
         
            courses.add(textCourse);
         
            courses.add(textCourse);
            courses.add(textCourse);
            Collections.sort(courses);
            for (Course course : courses) {
                course.getCourseTitle();
            }
        }
    }

