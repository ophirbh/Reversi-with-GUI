package graphics_control.game_control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

public class Preferences extends Application {
    final static int SCENE_SIZE = 450;
    final static int MODIFIED_SCENE_SIZE = SCENE_SIZE - 50;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            GridPane root = load(ClassLoader.getSystemClassLoader().getResource("Preferences.fxml"));
            Scene scene = new Scene(root, SCENE_SIZE, MODIFIED_SCENE_SIZE);

            primaryStage.setTitle("Reversi - Preferences");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
