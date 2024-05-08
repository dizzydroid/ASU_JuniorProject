package src.main.java.com.byteWise.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.filesystem.Read_Write;
import src.main.java.com.byteWise.quiz.Quiz;
import src.main.java.com.byteWise.quiz.Question;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QuizBuilderController {

    @FXML
    private TextField quizTitleField;
    @FXML
    private ListView<Question> questionsListView;
    @FXML
    private ComboBox<Course> courseComboBox;  // ComboBox to select the course
    private Dialog<ButtonType> dialog;

    @FXML
    public void initialize() {
        questionsListView.setCellFactory(lv -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : item.getQuestionText() + " - Options: " + item.getOptions());
            }
        });
        setupCourseListView();
    }

    private void setupCourseListView() {
        List<Course> courses = Read_Write.ReadFromCoursesFile();
        courseComboBox.setItems(FXCollections.observableList(courses));
        courseComboBox.setCellFactory(lv -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : item.getCourseTitle());
            }
        });
    }

    @FXML
    private void handleAddQuestion() {
    Dialog<Question> questionDialog = new Dialog<>();
    questionDialog.setTitle("Add New Question");

    // Setup the custom dialog layout
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField questionField = new TextField();
    questionField.setPromptText("Question");
    TextField option1 = new TextField();
    option1.setPromptText("Option 1");
    TextField option2 = new TextField();
    option2.setPromptText("Option 2");
    TextField option3 = new TextField();
    option3.setPromptText("Option 3");
    TextField option4 = new TextField();
    option4.setPromptText("Option 4");
    TextField correctAnswer = new TextField();
    correctAnswer.setPromptText("Correct Answer");

    grid.add(new Label("Question:"), 0, 0);
    grid.add(questionField, 1, 0);
    grid.add(new Label("Option 1:"), 0, 1);
    grid.add(option1, 1, 1);
    grid.add(new Label("Option 2:"), 0, 2);
    grid.add(option2, 1, 2);
    grid.add(new Label("Option 3:"), 0, 3);
    grid.add(option3, 1, 3);
    grid.add(new Label("Option 4:"), 0, 4);
    grid.add(option4, 1, 4);
    grid.add(new Label("Correct Answer:"), 0, 5);
    grid.add(correctAnswer, 1, 5);

    questionDialog.getDialogPane().setContent(grid);

    // Set the types of buttons in the dialog
    ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    questionDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

    // Process the dialog result
    questionDialog.setResultConverter(dialogButton -> {
        if (dialogButton == saveButtonType) {
            List<String> options = Arrays.asList(option1.getText(), option2.getText(), option3.getText(), option4.getText());
            return new Question(questionField.getText(), options, correctAnswer.getText());
        }
        return null;
    });

    Optional<Question> result = questionDialog.showAndWait();
    result.ifPresent(question -> questionsListView.getItems().add(question)); // Assuming 'questionsListView' is your ListView
}


@FXML
private void handleSaveQuiz() {
    // Get the selected course from the ComboBox
    Course selectedCourse = courseComboBox.getSelectionModel().getSelectedItem();

    if (selectedCourse == null) {
        showAlert("Error", "No course selected.");
        return;
    }

    // Get the quiz title from the TextField
    String quizTitle = quizTitleField.getText();

    if (quizTitle == null || quizTitle.trim().isEmpty()) {
        showAlert("Error", "Quiz title cannot be empty.");
        return;
    }

    // Create a new Quiz
    Quiz newQuiz = new Quiz(quizTitle);

    // Add the Quiz to the selected Course
    selectedCourse.addQuiz(newQuiz);

    // Close the dialog
    closeDialog();
    }

    
    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) quizTitleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Ensure dialog setup is done outside of FXML annotations to properly manage dialog properties
    public void setupDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
        this.dialog.setTitle("ByteWise Quiz Builder");
    }
}
