package graphics_control.event_handling.game;

import graphics_control.event_handling.BoardEventHandler;
import javafx.event.EventHandler;

public class CellPressedHandler implements EventHandler<javafx.scene.input.MouseEvent> {
    private BoardEventHandler boardEventHandler;

    /**
     * CellPressedHandler(BoardEventHandler boardEventHandler).
     *
     * @param boardEventHandler BoardEventHandler -- the board event handler.
     */
    public CellPressedHandler(BoardEventHandler boardEventHandler) {
        this.boardEventHandler = boardEventHandler;
    }

    /**
     * handle(javafx.scene.input.MouseEvent event).
     *
     * @param event javafx.scene.input.MouseEvent -- a mouse event.
     */
    public void handle(javafx.scene.input.MouseEvent event) {
        this.boardEventHandler.onMouseClicked(event);
    }
}
