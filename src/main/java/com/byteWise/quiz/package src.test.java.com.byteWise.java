package src.test.java.com.byteWise.quiz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.main.java.com.byteWise.quiz.Quiz;

class QuizTest {

    @Test
    void testQuiz() {
        // Create a new Quiz object
        Quiz quiz = new Quiz();

        // Add test questions and answers
        quiz.addQuestion("What is the capital of France?", "Paris");
        quiz.addQuestion("What is the largest planet in our solar system?", "Jupiter");
        quiz.addQuestion("What is the chemical symbol for gold?", "Au");

        // Test the getQuestionCount() method
        assertEquals(3, quiz.getQuestionCount());

        // Test the getQuestion() method
        assertEquals("What is the capital of France?", quiz.getQuestion(0));
        assertEquals("What is the largest planet in our solar system?", quiz.getQuestion(1));
        assertEquals("What is the chemical symbol for gold?", quiz.getQuestion(2));

        // Test the getAnswer() method
        assertEquals("Paris", quiz.getAnswer(0));
        assertEquals("Jupiter", quiz.getAnswer(1));
        assertEquals("Au", quiz.getAnswer(2));
    }
}