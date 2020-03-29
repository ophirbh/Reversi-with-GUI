package graphics_control.event_handling.game;

import graphics_control.drawables.GameCell;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class MouseExitsAvailableMove implements EventHandler<javafx.scene.input.MouseEvent> {
    private GameCell gameCell;

    public MouseExitsAvailableMove(GameCell gC) {
        this.gameCell = gC;
    }

    public void handle(javafx.scene.input.MouseEvent event) {
        gameCell.getRect().setFill(Color.DARKGRAY);
    }
}
