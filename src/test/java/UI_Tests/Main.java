import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_scene.fxml"));
        Parent root = loader.load();
       // WelcomeSceneController controller = loader.getController();
        Scene scene = new Scene(root, Color.gray(0.9)); // add the root node to the scene and pass a paint color
        String css = this.getClass().getResource("styles.css").toExternalForm();

        scene.getStylesheets().add(css);


        stage.setTitle("Demo");

        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show(); 
    }

}