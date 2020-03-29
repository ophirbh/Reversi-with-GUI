package game_object;

/**
 * Position Class.
 */
public class Position {
    //---------- LOCAL CLASS VARIABLES ----------
    private int row;
    private int col;

    //---------- INITIALIZER ----------

    /**
     * Position(int row, int col).
     *
     * @param row int -- a row.
     * @param col int -- a col.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    //---------- GETTERS ----------

    /**
     * getRow().
     *
     * @return the row for this position.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * getCol().
     *
     * @return the col for this position.
     */
    public int getCol() {
        return this.col;
    }

    //---------- UTILITY FUNCTIONS ----------

    /**
     * toString().
     */
    @Override
    public String toString() {
        return row + "," + col;
    }

    /**
     * hashCode().
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * equals(Object obj).
     *
     * @param obj Object -- another object.
     * @return true of objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }
}