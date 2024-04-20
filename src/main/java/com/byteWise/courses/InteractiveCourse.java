package src.main.java.com.byteWise.courses;
public class InteractiveCourse extends Course {
    private String resourceLink;

    public InteractiveCourse(String courseId, String courseTitle, String description, String courseTag, String resourceLink) {
        super(courseId, courseTitle, description, courseTag);
        this.resourceLink = resourceLink;
    }

    @Override
    public void displayCourseDetails() {
        System.out.println("Course Title: " + getCourseTitle());
        System.out.println("Description: " + getDescription());
        System.out.println("Resource Link: " + resourceLink);
    }
}
