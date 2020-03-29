package graphics_control.event_handling;

import graphics_control.drawables.GameCell;
import graphics_control.event_handling.controllers_events.GameOverEvent;
import graphics_control.game_control.BoardController;
import graphics_control.game_control.ReversiGameController;
import graphics_control.event_handling.game.CellPressedHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardEventHandler implements Initializable {
    private BoardController board;
    private ReversiGameController gameController;
    private CellPressedHandler cellPressedHandler;

    /**
     * BoardEventHandler(ReversiGameController gameController).
     *
     * @param gameController ReversiGameController -- the game controller.
     */
    public BoardEventHandler(ReversiGameController gameController, BoardController board) {
        this.cellPressedHandler = new CellPressedHandler(this);
        this.gameController = gameController;
        board.attachBoardEventHandler(this);
        this.board = board;
    }

    /**
     * initialize(URL location, ResourceBundle resources).
     *
     * @param location  URL -- url;
     * @param resources ResourceBundle -- resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * CellPressedHandler getCellPressedHandler().
     * @return the CellPressedHandler for this BoardEventHandler.
     */
    public CellPressedHandler getCellPressedHandler() {
        return this.cellPressedHandler;
    }

    /**
     * onMouseClicked(javafx.scene.input.MouseEvent event).
     *
     * @param event MouseEvent -- a mouse event.
     */
    public void onMouseClicked(javafx.scene.input.MouseEvent event) {
        Node source = (Node)event.getSource();
        Integer rowIndex = (GridPane.getRowIndex(source) == null) ? 0 : GridPane.getRowIndex(source);
        Integer colIndex = (GridPane.getColumnIndex(source) == null) ? 0 : GridPane.getColumnIndex(source);

        GameCell cell = board.getGameCell(rowIndex, colIndex);

        if(cell.cellOccupied()) {
            return;
        }

        this.gameController.cellPressed(cell.getPosition());
    }

    /**
     * gameOver(Event e)
     *
     * @param e Event -- hold the game over event arguments.
     */
    public void gameOver(GameOverEvent e) {
        this.gameController.gameOver(e);
    }
}
