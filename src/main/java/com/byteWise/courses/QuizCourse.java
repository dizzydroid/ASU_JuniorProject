package src.main.java.com.byteWise.courses;
import src.main.java.com.byteWise.quiz.Quiz;
import java.util.List;
import java.util.ArrayList;
public class QuizCourse extends Course {
    private List<Quiz> quizzes;

    public QuizCourse(String courseId, String courseTitle, String description, String courseTag) {
        super(courseId, courseTitle, description, courseTag);
        this.quizzes = new ArrayList<>();
    }

    @Override
    public void displayCourseDetails() {
        System.out.println("Course Title: " + getCourseTitle());
        System.out.println("Description: " + getDescription());
        System.out.println("Number of Quizzes: " + quizzes.size());
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        System.out.println("Added quiz: " + quiz.getTitle());
    }
}
