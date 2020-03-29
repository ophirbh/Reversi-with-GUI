package graphics_control.game_control;
/**
 * Name:    Alon Vita
 * ID:      311233431
 * Github:  https://github.com/Alonvita
 */

import graphics_control.files_and_parsing.FileCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * graphics_control.game_control.ReversiMain Class.
 */
public class ReversiMain extends Application {
    static int WIDTH = 800;
    static int HEIGHT = 450;

    void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        try {
            // create the default file
            if(primaryStage.getTitle() == null) {
                FileCreator fileCreator = new FileCreator();
                fileCreator.createDefaultFile();
            }

            HBox root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("ReversiGame.fxml"));
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(ClassLoader.getSystemClassLoader().getResource("application.css").toExternalForm());

            primaryStage.setTitle("Reversi Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
