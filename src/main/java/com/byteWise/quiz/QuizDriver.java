package src.main.java.com.byteWise.quiz;
import java.util.Scanner;

import java.util.Arrays;

public class QuizDriver {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Java Quiz");
        Question question1 = new Question("What is the size of int variable?", Arrays.asList("2 bytes", "3 bytes", "4 bytes"), "4 bytes");
        Question question2 = new Question("What is the default value of char variable?", Arrays.asList("1","2","0","null"), "null");
        Question question3 = new Question("What is the default value of float variable?", Arrays.asList("1.0","0.0","null","0.1"), "0.0");
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);
        System.out.println("Quiz Title: " + quiz.getTitle());
        System.out.println("Questions: ");
        for (Question question : quiz.getQuestions()) {
            System.out.println(question.getQuestionText());
            System.out.println("Options: ");
            for (String option : question.getOptions()) {
                System.out.println(option);
            }
            System.out.println("Pick the correct answer, (type the option and press enter): ");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if (answer.equals(question.getCorrectAnswer())) {
                System.out.println("Correct Answer!\n");
            } else {
                System.out.println("Incorrect Answer!\n");
            }
            // clear the buffer
        }
    }
}