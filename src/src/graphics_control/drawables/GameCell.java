package graphics_control.drawables;

import game_object.Cell;
import game_object.Position;
import graphics_control.event_handling.game.CellPressedHandler;
import graphics_control.event_handling.game.MouseEntersAvailableMove;
import graphics_control.event_handling.game.MouseExitsAvailableMove;
import graphics_control.game_control.BoardController;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * GameCell implements Drawable.
 */
public class GameCell implements Drawable {
    final static String SHADOW = "SHADOW";
    final static double CIRCLE_OFFSET_SMALL = 2;
    final static double CIRCLE_OFFSET_MEDIUM = 2.1;
    final static double CIRCLE_OFFSET_LARGE = 2.2;

    //----------LOCAL CLASS VARIABLES----------
    private Cell color;
    private Rectangle rect;
    private BoardController gameBoard;
    private Position position;
    private boolean availableMove;
    private boolean occupied = false;

    private double length;
    private double circleDrawOffset;

    // COLORS
    private HashMap<String, Color> colors = new HashMap<>();

    //----------INITIALIZER----------

    /**
     * GameCell(Position position, Cell color, Board gameBoard).
     *
     * @param position  Position -- cell's position.
     * @param color     Cell -- cell's color
     * @param gameBoard Board -- the board this Cell is on.
     */
    public GameCell(Position position, Cell color, BoardController gameBoard) {
        this.color = color;
        this.position = position;
        this.gameBoard = gameBoard;
        this.availableMove = false;

        // COLORS
        colors.put(Cell.WHITE.toString(), Color.WHITE);
        colors.put(Cell.BLACK.toString(), Color.BLACK);
        colors.put(Cell.GREEN.toString(), Color.GREEN);
        colors.put(Cell.RED.toString(), Color.RED);
        colors.put(Cell.BLUE.toString(), Color.BLUE);
        colors.put(Cell.BROWN.toString(), Color.BROWN);
        colors.put(Cell.ORANGE.toString(), Color.ORANGE);
        colors.put(Cell.EMPTY.toString(), Color.DARKGRAY);
        colors.put(SHADOW, Color.BLACK);

        this.length = (((int) this.gameBoard.getPrefWidth()) / gameBoard.getBoardSize());
        this.circleDrawOffset = length / CIRCLE_OFFSET_SMALL;

        // create this rect
        this.rect = new Rectangle(length, length, this.colors.get(Cell.EMPTY.toString()));
    }

    //----------LISTENERS----------

    /**
     * attachAvailableMoveListener().
     */
    public void attachAvailableMoveListener() {
        this.rect.setOnMouseEntered(new MouseEntersAvailableMove(this));
        this.rect.setOnMouseExited(new MouseExitsAvailableMove(this));
    }

    /**
     * attachMousePressedListener().
     *
     * @return the position of the shape held by this GameCell.
     */
    public void attachMousePressedListener(CellPressedHandler handler) {
        this.rect.setOnMousePressed(handler);
    }

    //----------GETTERS & SETTERS----------

    /**
     * cellOccupied().
     *
     * @return true if this cell is occupied, or false otherwise
     */
    public boolean cellOccupied() {
        return this.occupied;
    }

    /**
     * setCellOccupied(boolean occupied).
     *
     * @param occupied boolean -- indicates a change in cell's occupation status.
     */
    public void setCellOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * getPosition().
     *
     * @return the position of this GameCell on the Board.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * setColor(Cell color).
     *
     * @param color Cell -- a color to set to.
     */
    public void setColor(Cell color) {
        this.color = color;
    }

    /**
     * getColor().
     *
     * @return the color of this cell as a Cell object.
     */
    public Cell getColor() {
        return this.color;
    }

    /**
     * setAvailableMove(boolean moveIsAvailable).
     *
     * @param moveIsAvailable boolean -- true if the move is an available move, or false otherwise.
     */
    public void setAvailableMove(boolean moveIsAvailable) {
        this.availableMove = moveIsAvailable;
    }

    /**
     * isAvailableMove().
     *
     * @return true if this is an available move, or false otherwise.
     */
    public boolean isAvailableMove() {
        return this.availableMove;
    }

    /**
     * getRect().
     *
     * @return the rectangle representing this GameCell.
     */
    public Rectangle getRect() {
        return this.rect;
    }

    //----------DRAWABLE----------

    /**
     * draw().
     */
    public void draw() {
        // Local variables
        Color c;
        int row = this.position.getCol();
        int col = this.position.getRow();
        Point2D point = new Point2D(row, col);

        // avoid duplicating -- if board contains this rect, remove it and read it
        if (gameBoard.contains(point)) {
            gameBoard.getChildren().remove(this.rect);
        }

        // draw this cell's representing rect
        this.gameBoard.add(this.rect, row, col);

        // if cell is empty, just return.
        if (this.color == Cell.EMPTY)
            return;

        // define drawing color
        c = this.colors.get(this.color.toString());

        if(this.color  != Cell.BLACK) {
            this.gameBoard.add(
                    new Circle(
                            row + circleDrawOffset,
                            col + circleDrawOffset,
                            length / CIRCLE_OFFSET_MEDIUM, colors.get(SHADOW)),
                    row, col
            );
        } else {
            this.gameBoard.add(
                    new Circle(
                            row + circleDrawOffset,
                            col + circleDrawOffset,
                            length / CIRCLE_OFFSET_MEDIUM, colors.get(Cell.WHITE.toString())),
                    row, col
            );
        }

        // cell is NOT empty -- create a circle
        this.gameBoard.add(

                new Circle(
                        row + circleDrawOffset,
                        col + circleDrawOffset,
                        length / CIRCLE_OFFSET_LARGE, c),
                row, col
        );
    }
}