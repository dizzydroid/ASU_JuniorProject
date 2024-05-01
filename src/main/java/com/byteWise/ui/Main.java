package src.main.java.com.byteWise.ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import src.main.java.com.byteWise.filesystem.Read_Write;

public class Main extends Application {
    public static void main(String[] args) {
        Read_Write.setFilePath();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./welcome_scene.fxml"));
        Parent root = loader.load();
       // WelcomeSceneController controller = loader.getController();
        Scene scene = new Scene(root, Color.gray(0.9)); // add the root node to the scene and pass a paint color
        String css = this.getClass().getResource("styles.css").toExternalForm();

        scene.getStylesheets().add(css);


        stage.setTitle("Demo");
        Image icon = new Image("file:///"+Read_Write.getFILEPATH()+"\\src\\main\\java\\com\\byteWise\\ui\\icon.png");        
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show(); 
    }

}