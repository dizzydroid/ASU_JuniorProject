package src.main.java.com.byteWise.quiz;

import java.util.Collections;
import java.util.List;

public class Question {

    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question(String text, List<String> options, String correctAnswer) {
        this.questionText = text;
        this.options = options;
        this.correctAnswer = correctAnswer;

    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        Collections.shuffle(options);
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

}