package graphics_control.game_control;

import game_object.Cell;
import graphics_control.files_and_parsing.FileCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class PreferencesController implements Initializable {
    ObservableList<String> firstTurnSelections =
            FXCollections.observableArrayList(
                    "Player1",
                    "Player2"
            );
    ObservableList<String> diskColors =
            FXCollections.observableArrayList(
                    Cell.BLACK.toString(), Cell.WHITE.toString(),
                    Cell.RED.toString(), Cell.GREEN.toString(), Cell.BLUE.toString(),
                    Cell.BROWN.toString(), Cell.ORANGE.toString()
            );
    ObservableList<String> boardSizes =
            FXCollections.observableArrayList(
                    "4x4", "6x6", "8x8",
                    "10x10", "12x12", "14x14",
                    "16x16", "18x18", "20x20"
            );

    private FileCreator fileCreator;

    @FXML
    private ComboBox firstTurn;
    @FXML
    private ComboBox player1Color;
    @FXML
    private ComboBox player2Color;
    @FXML
    private ComboBox boardSize;
    @FXML
    private Button apply;

    /**
     * initialize().
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        firstTurn.setItems(firstTurnSelections);
        player1Color.setItems(diskColors);
        player2Color.setItems(diskColors);
        boardSize.setItems(boardSizes);

        firstTurn.setValue(FileCreator.DEFAULT_FIRST_TURN);
        player1Color.setValue(FileCreator.DEFAULT_PLAYER1_COLOR);
        player2Color.setValue(FileCreator.DEFAULT_PLAYER2_COLOR);
        boardSize.setValue(FileCreator.DEFAULT_BOARD_SIZE);

        this.fileCreator = new FileCreator();
    }

    /**
     * applyChanges().
     */
    public void applyChanges() {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File("preferences.txt");
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch(Exception e) {
            System.out.println(e.getCause().toString());
        }

        String preferences = "firstTurnPlayer: " + firstTurn.getValue().toString() + "\nplayer1Color: " +
                player1Color.getValue().toString() + "\nplayer2Color: " + player2Color.getValue().toString() +
                "\nboardSize: " + boardSize.getValue().toString();
        try {
            try {
                bufferedWriter.write(preferences);
            } catch (NullPointerException e) {
                System.out.println(e.getCause().toString());
            }
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println(e.getCause().toString());
        }

        ReversiMain reversiMain = new ReversiMain();
        Stage stage = (Stage) apply.getScene().getWindow();
        try {
            reversiMain.start(stage);
        } catch (Exception e) {
        }
    }
}
