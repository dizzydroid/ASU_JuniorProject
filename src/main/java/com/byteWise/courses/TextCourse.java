package src.main.java.com.byteWise.courses;


public class TextCourse extends Course {
    private String resourceLink;
    
    public TextCourse(String courseId, String courseTitle, String description, String courseTag, String resourceLink) {
        super(courseId, courseTitle, description, courseTag);
        this.resourceLink = resourceLink;
    }
    public TextCourse(String courseId, String courseTitle, String description, String courseTag) {
        super(courseId, courseTitle, description, courseTag);
    }
    public String getLink() {
        return resourceLink;
    }

    public void setLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

 

 
}
