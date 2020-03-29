package graphics_control.game_control;

import game_object.Board;
import game_logic.TurnsManager;
import game_object.Cell;
import game_object.Position;
import graphics_control.event_handling.BoardEventHandler;
import graphics_control.event_handling.controllers_events.GameOverEvent;
import graphics_control.event_handling.controllers_events.GameOverEventArguments;
import graphics_control.files_and_parsing.PreferencesFileParser;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;
import static javafx.fxml.FXMLLoader.load;

/**
 * ReversiGameController Class.
 */
public class ReversiGameController implements Initializable {
    final static int CELL_PRESENTATION_ADDITION = 1;

    // Game initializing values
    private int boardType;
    private boolean startingPlayer;
    private Cell player1Color;
    private Cell player2Color;

    // Local Variables
    private TurnsManager manager;
    private BoardEventHandler boardEventHandler;
    private BoardController gameBoardController = null;
    private BooleanProperty canStart = new SimpleBooleanProperty(true);
    private BooleanProperty dialogVisibility = new SimpleBooleanProperty(false);
    private BooleanProperty backToMainMenu = new SimpleBooleanProperty(false);
    private BooleanProperty labelVisibility = new SimpleBooleanProperty(false);
    private BooleanProperty preferencesVisibility = new SimpleBooleanProperty(true);

    // FXML Properties
    @FXML
    private Node mainMenuVBox;
    @FXML
    private HBox root;
    @FXML
    private Button startBtn;
    @FXML
    private Button preferencesBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button backToMainMenuBtn;
    @FXML
    DialogPane dialog;
    @FXML
    Label label;

    /**
     * initialize(URL location, ResourceBundle resources).
     *
     * @param location  URL -- url;
     * @param resources ResourceBundle -- resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startBtn.visibleProperty().bind(canStart);
        dialog.visibleProperty().bind(dialogVisibility);
        backToMainMenuBtn.visibleProperty().bind(backToMainMenu);
        label.visibleProperty().bind(labelVisibility);
        preferencesBtn.visibleProperty().bind(preferencesVisibility);

        dialog.getStylesheets().add(ClassLoader.getSystemClassLoader().getResource("dialogs.css").toExternalForm());
        dialog.getStyleClass().add("myDialog");

        preferencesSetFromFile();
    }

    /**
     * preferencesSetFromFile().
     */
    private void preferencesSetFromFile() {
        // Create a parser and parse the file
        PreferencesFileParser parser = new PreferencesFileParser();
        ParsedValues parsedValues = parser.parseFile();

        // set initializing values
        this.boardType = parsedValues.getBoardType();
        this.startingPlayer = parsedValues.getFirstTurnPlayer();
        this.player1Color = parsedValues.getPlayer1Color();
        this.player2Color = parsedValues.getPlayer2Color();
    }

    /**
     * preferencesClicked().
     */
    @FXML
    public void preferencesClicked() {
        if (!this.canStart.getValue()) {
            dialog.setContentText("You are in a game!");
            return;
        }

        Preferences preferences = new Preferences();
        Stage stage = (Stage) preferencesBtn.getScene().getWindow();
        try {
            preferences.start(stage);
        } catch (Exception e) {
        }
    }

    /**
     * startGame().
     */
    @FXML
    protected void startGame() {
        // game started event
        this.canStart.setValue(false);
        this.dialogVisibility.setValue(true);
        this.labelVisibility.setValue(true);
        this.preferencesVisibility.setValue(false);

        // initialize a new Board to play on
        Board board = new Board(boardType, player1Color, player2Color);

        // initialize board controller and manager
        this.manager = new TurnsManager(board, startingPlayer, player1Color, player2Color);
        if (gameBoardController != null) {
            gameBoardController.getChildren().clear();
        }

        this.gameBoardController = new BoardController(board, manager);

        // create the BoardEventHandler
        this.boardEventHandler = new BoardEventHandler(this, this.gameBoardController);
        gameBoardController.attachBoardEventHandler(boardEventHandler);

        // initialize available moves
        gameBoardController.evaluateAvailableMovesForThisTurn(manager.getAvailableMoves());

        // add the board as a child to this Game
        root.getChildren().add(0, this.gameBoardController);

        label.setText("It's " + manager.getCurrentPlayerColor() + " player turn");

        //evaluate available moves for the first turn
        gameBoardController.draw();
    }

    /**
     * exitGame().
     */
    @FXML
    protected void exitGame() {
        exit(0);
    }

    /**
     * backToMain().
     */
    @FXML
    protected void backToMain() {
        ReversiMain main = new ReversiMain();
        Stage stage = (Stage) backToMainMenuBtn.getScene().getWindow();
        try {
            main.start(stage);
        } catch (Exception e) {
        }
    }

    /**
     * cellPressed(Position p).
     *
     * @param p Position -- position of the cell pressed.
     */
    @FXML
    public void cellPressed(Position p) {
        if (this.backToMainMenu.getValue()) {
            return;
        }

        // Local Variables
        int row = p.getRow();
        int col = p.getCol();

        // illegal move
        if (!manager.moveIsLegal(p)) {
            dialog.setContentText("This move is illegal, please pick an available move.");
            return;
        }

        // notify gameBoardController that a move was made
        gameBoardController.moveMade(row, col, manager.getCurrentPlayerColor());

        int rowRep = row + CELL_PRESENTATION_ADDITION;
        int colRep = col + CELL_PRESENTATION_ADDITION;

        // end turn
        manager.endTurn();

        label.setText("It's " + manager.getCurrentPlayerColor() + " player turn");
        dialog.setContentText("Last move made is: (" + rowRep + "," + colRep + ")" +
                "\nThe current score is:\nPlayer 1: " + gameBoardController.evaluatePlayer1Score()
                + "\nPlayer 2: " + gameBoardController.evaluatePlayer2Score());

        // prepare manager for next turn
        manager.evaluateAvailableMovesForThisTurn();
        gameBoardController.evaluateAvailableMovesForThisTurn(manager.getAvailableMoves());
        gameBoardController.evaluateMove();
        gameBoardController.draw();
    }

    /**
     * gameOver(Event e).
     *
     * @param e Event -- will hold the game over event arguments.
     */
    @FXML
    public void gameOver(GameOverEvent e) {
        // Local Variables
        GameOverEventArguments args = e.getArgs();
        String winner = args.getWinner();
        String loser = args.getLoser();
        int winnerScore = args.getWinnerScore();
        int loserScore = args.getLoserScore();

        if (winnerScore == loserScore) {
            dialog.setContentText("TIE! the score is: " + winnerScore + ":" + loserScore);
        } else {
            dialog.setContentText("The winner is " + winner + " with: " + winnerScore + " points\n"
                    + "over " + loser + " with: " + loserScore + " points");
        }

        // a new game can start now
        this.labelVisibility.setValue(false);
        this.backToMainMenu.setValue(true);
    }
}
