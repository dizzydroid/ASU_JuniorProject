package src.main.java.com.byteWise.courses;
public abstract class Course {
    private String courseId;
    private String courseTitle;
    private String description;
    private String courseTag;

    // Constructor
    public Course(String courseId, String courseTitle, String description, String courseTag) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.description = description;
        this.courseTag = courseTag;
    }

    // Abstract method to display course details
    public abstract void displayCourseDetails();

    // Getters
    public String getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseTag() {
        return courseTag;
    }

    // Setters
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseTag(String courseTag) {
        this.courseTag = courseTag;
    }
}
