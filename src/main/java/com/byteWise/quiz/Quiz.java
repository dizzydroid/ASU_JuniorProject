package src.main.java.com.byteWise.quiz;
import java.util.ArrayList;
import java.util.List;


public class Quiz {
    private String quizTitle;
    private List<Question> questions;

    
    public Quiz(String title) {
        
        this.quizTitle = title;
        this.questions = new ArrayList<>();
    }
    
    public Quiz(String title,List<Question> questions) {
        this.quizTitle = title;
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return quizTitle;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setTitle(String title) {
        this.quizTitle = title;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
  
 
}


