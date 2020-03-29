package graphics_control.factory;
import game_object.Cell;
import game_object.Position;
import graphics_control.drawables.GameCell;
import graphics_control.game_control.BoardController;

/**
 * CellMatrixGenerator Class.
 */
public class CellMatrixGenerator {
    //---------- INITIALIZER ----------

    /**
     * ParseResult ParseBoard(int size) throws Exception.
     *
     * @param boardController Board -- the boardController initializing the matrix.
     * @return a GameCell matrix.
     */
    public GameCell[][] generateMatrix(BoardController boardController) {
        // Local Variables
        int size = boardController.getBoardSize();

        // initialize a new Cells matrix
        GameCell[][] matrix = new GameCell[size][size];

        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                matrix[row][col] = new GameCell(new Position(row, col), Cell.EMPTY, boardController);
                matrix[row][col].attachAvailableMoveListener();
            }
        }

        // initialize starting cells
        GameCell current = matrix[size / 2][size / 2];
        current.setCellOccupied(true);
        current.setColor(boardController.cellColorAt(size / 2, size / 2));

        current = matrix[size / 2 - 1][size / 2];
        current.setCellOccupied(true);
        current.setColor(boardController.cellColorAt(size / 2 - 1, size / 2));

        current = matrix[size / 2][size / 2 - 1];
        current.setCellOccupied(true);
        current.setColor(boardController.cellColorAt(size / 2, size / 2 - 1));

        current = matrix[size / 2 - 1][size / 2 - 1];
        current.setCellOccupied(true);
        current.setColor(boardController.cellColorAt(size / 2 - 1, size / 2 - 1));

        return matrix;
    }
}
