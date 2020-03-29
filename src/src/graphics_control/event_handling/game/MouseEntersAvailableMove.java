package graphics_control.event_handling.game;

import graphics_control.drawables.GameCell;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class MouseEntersAvailableMove implements EventHandler<javafx.scene.input.MouseEvent> {
    private GameCell gameCell;

    /**
     * MouseEntersAvailableMove(GameCell gC).
     *
     * @param gC GameCell -- the game cell that this Handler is listening to.
     */
    public MouseEntersAvailableMove(GameCell gC) {
        this.gameCell = gC;
    }

    /**
     * handle(javafx.scene.input.MouseEvent event).
     *
     * @param event MouseEvent -- the mouse event happened.
     */
    public void handle(javafx.scene.input.MouseEvent event) {
        if(gameCell.isAvailableMove()) {
            gameCell.getRect().setFill(Color.GREEN);
        }
    }
}
