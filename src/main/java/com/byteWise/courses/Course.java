package src.main.java.com.byteWise.courses;

import java.util.List;

import src.main.java.com.byteWise.interfaces.Assessable;
import src.main.java.com.byteWise.quiz.Quiz;

// import src.main.java.com.byteWise.interfaces.Sortable;

public abstract class Course implements Comparable<Course> , Assessable {
    private String courseId;
    private String courseTitle;
    private String description;
    private String courseTag;
    private int progress;
    List<Quiz> quizzes;
    

    // Constructor
    public Course(String courseId, String courseTitle, String description, String courseTag) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.description = description;
        this.courseTag = courseTag;
    }


    @Override
    public Quiz conductAssessment()  {

        
        return quizzes.remove(0);
    }

   


    @Override
    public int quizzesCount()  {

        
        return quizzes.size();
    }

   
        
    @Override
    public int compareTo(Course o) {
        return this.getCourseTitle().compareTo(o.getCourseTitle());
    }


    public int getProgress() {
        return progress;
    }

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
    
    public void setProgress(int progress)  {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100");
        }
        this.progress = progress;
    }

    public void setCourseTag(String courseTag) {
        this.courseTag = courseTag;
    }
    
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
    public abstract String getLink();
    public abstract void setLink(String link);
}
