package lib.course;
public class VideoCourse extends Course {
    private String videoUrl;

    public VideoCourse(String courseId, String courseTitle, String description, String CourseTag, String videoUrl) {
        super(courseId, courseTitle, description, CourseTag);
        this.videoUrl = videoUrl;
    }

    public VideoCourse(String courseId, String courseTitle, String description, String CourseTag) {
        super(courseId, courseTitle, description, CourseTag);
    }
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

 
}
