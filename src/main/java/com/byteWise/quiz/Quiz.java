package src.main.java.com.byteWise.quiz;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private String quizTitle;
    private List<Question> questions;

    public Quiz(String title) {
        this.quizTitle = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return quizTitle;
    }

    public void conductQuiz() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("Starting Quiz: " + quizTitle);
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ": " + options.get(i));
            }
            System.out.print("Your answer (1-" + options.size() + "): ");
            int answer = scanner.nextInt();
            if (options.get(answer - 1).equals(question.getCorrectAnswer())) {
                score++;
            }
        }
        System.out.println("You scored " + score + "/" + questions.size());
        scanner.close();
    }

    public int calculateScore() {
        int score = 0;
        // Assume a Scanner has been used to take user input when quiz is conducted
        for (Question question : questions) {
            // Logic to check answers and calculate score
            // This part is simulated here
        }
        return score;
    }
}


