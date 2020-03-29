package game_object;

/**
 * Cell Class.
 */
public enum Cell {
    //---------- ENUM DEFINITIONS ----------
    BLACK("Black"),
    WHITE("White"),
    GREEN("Green"),
    RED("Red"),
    BLUE("Blue"),
    BROWN("Brown"),
    ORANGE("Orange"),
    EMPTY("Empty");

    //---------- LOCAL CLASS VARIABLES ----------
    private String cell;

    //---------- INITIALIZER ----------

    /**
     * Cell(String cell).
     *
     * @param cell string -- a string representing this cell.
     */
    Cell(String cell) {
        this.cell = cell;
    }

    /**
     * toString().
     *
     * @return the string representing the cell type.
     */
    @Override
    public String toString() {
        return cell;
    }
}
