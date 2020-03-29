package players;

import game_object.Cell;
import game_object.Position;

import java.util.Scanner;

/**
 * HumanPlayer Class.
 */
public class HumanPlayer implements Player {
    //---------- LOCAL CLASS VARIABLES ----------
    private boolean color;
    private Cell colorAsEnum;

    //---------- INITIALIZER ----------

    /**
     * HumanPlayer(Board b, Cell color).
     *
     * @param color Cell - BLACK or WHITE.
     */
    public HumanPlayer(Cell color) {
        if (color == Cell.WHITE)
            this.color = true;

        this.color = false;
    }

    /**
     * HumanPlayer(Cell color, boolean turn).
     *
     * @param color Cell -- the player color
     * @param first -- whether player is first or not
     */
    public HumanPlayer(Cell color, boolean first) {
        this.color = first;
        this.colorAsEnum = color;
    }

    //---------- GETTERS ----------

    /**
     * getColor().
     *
     * @return the player's color.
     */
    public Cell getColor() {
        return this.colorAsEnum;
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * move().
     */
    public Position move() {
        // Local Variables
        Scanner scanner = new Scanner(System.in);
        String moveAsRawString = scanner.next();
        int row;
        int col;

        // bad argument
        if (moveAsRawString.length() > 3)
            return new Position(-1, -1);

        row = Character.getNumericValue(moveAsRawString.charAt(0));
        col = Character.getNumericValue(moveAsRawString.charAt(2));

        return new Position(row, col);
    }
}