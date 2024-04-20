package src.main.java.com.byteWise.courses;
public class VideoCourse extends Course {
    private String videoUrl;

    public VideoCourse(String courseId, String courseTitle, String description, String CourseTag, String videoUrl) {
        super(courseId, courseTitle, description, CourseTag);
        this.videoUrl = videoUrl;
    }

    @Override
    public void displayCourseDetails() {
        System.out.println("Course Title: " + getCourseTitle());
        System.out.println("Description: " + getDescription());
        System.out.println("Video URL: " + videoUrl);
    }
}
