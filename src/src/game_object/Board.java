package game_object;

import graphics_control.drawables.GameCell;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Board Class.
 */
public class Board {
    public static int SIZE_4 = 4;
    public static int SIZE_6 = 6;
    public static int SIZE_8 = 8;
    public static int SIZE_10 = 10;
    public static int SIZE_12 = 12;
    public static int SIZE_14 = 14;
    public static int SIZE_16 = 16;
    public static int SIZE_18 = 18;
    public static int SIZE_20 = 20;

    public static int TYPE_1 = 1;
    public static int TYPE_2 = 2;
    public static int TYPE_3 = 3;
    public static int TYPE_4 = 4;
    public static int TYPE_5 = 5;
    public static int TYPE_6 = 6;
    public static int TYPE_7 = 7;
    public static int TYPE_8 = 8;
    public static int TYPE_9 = 9;

    //---------- LOCAL CLASS VARIABLES ----------
    private int size;
    private int cellsInUse;
    private Cell[][] board;
    //private MovesObserver movesObserver;
    private List<Position> occupiedCells;
    Cell player1Color;
    Cell player2Color;

    //---------- INITIALIZERS ----------

    /**
     * Board(Cell[][] board).
     *
     * @param type int -- the board size.
     */
    public Board(int type) {
        this.size = initializeBoardSize(type);
        initializeBoard();

        // try to initialize board
        try {
            this.occupiedCells = initializeOccupiedCells();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Board(int type, Cell player1Color, Cell player2Color).
     * @param type int -- the board type.
     * @param player1Color Cell -- player 1 color
     * @param player2Color Cell -- player 2 color
     */
    public Board(int type, Cell player1Color, Cell player2Color) {
        this.player1Color = player1Color;
        this.player2Color = player2Color;
        this.size = initializeBoardSize(type);
        initializeBoard();

        // try to initialize board
        try {
            this.occupiedCells = initializeOccupiedCells();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Board(Cell[][] matrix).
     *
     * @param matrix Cell[][] -- a cell matrix
     */
    public Board(Cell[][] matrix) {
        this.size = matrix.length;
        this.occupiedCells = initializeOccupiedCells();
    }

    //---------- GETTERS ----------

    /**
     * getSize().
     *
     * @return the size of the board.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * getGameCell(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col
     * @return the GameCell on this row,col
     */
    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    /**
     * getBoard().
     *
     * @return a copy of this game board.
     */
    public Board getBoard() {
        // Local Variables
        Cell[][] cellMatrix = new Cell[size][size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = this.board[i][j];
            }
        }

        return new Board(cellMatrix);
    }

    /**
     * getSpaceLeft().
     *
     * @return the number of empty cells on board.
     */
    public int getSpaceLeft() {
        return ((int) Math.pow(this.size, 2)) - this.cellsInUse;
    }

    /**
     * setSpaceLeft(int spaceLeft).
     *
     * @param spaceLeft int -- the space left on the board
     */
    public void setSpaceLeft(int spaceLeft) {
        this.cellsInUse = size * size - spaceLeft;
    }

    /**
     * getCellPotentialAsList(GameCell p, Cell c).
     *
     * @param p Position -- a position on board.
     * @param c Cell -- player's color.
     * @return a list of all cells that are potentially effected in the next move.
     */
    public List<Position> getCellPotentialAsList(Position p, Cell c) {
        // Local Variables
        List<Position> potentialList = new ArrayList<>();
        int dRow, dCol;

        for (dRow = -1; dRow <= 1; ++dRow) {
            for (dCol = -1; dCol <= 1; ++dCol) {
                // get potential in path
                List<Position> potentialInPath = findPotentialInPath(p, dRow, dCol, c);

                // found potential in path
                if (!potentialInPath.isEmpty()) {
                    // add it to potential list
                    potentialList.addAll(potentialInPath);
                }
            }
        }

        return potentialList;
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * moveMade(Position p, Cell value).
     *
     * @param p     Position -- a position made.
     * @param value Cell -- move's color.
     */
    public void moveMade(Position p, Cell value) {
        // Local Variables
        int row = p.getRow();
        int col = p.getCol();

        // cell out of bounds -- player had no moves
        if (!cellOnBoard(row, col))
            return;

        List<Position> cellsEffected = getCellsPotentialAsList(p, value);

        // clear duplicates
        Set<Position> hs = new HashSet<>();
        hs.addAll(cellsEffected);
        cellsEffected.clear();
        cellsEffected.addAll(hs);

        // change cell's value
        board[row][col] = value;

        // update occupied cells and draw the new cell
        this.occupiedCells.add(p);
        cellsInUse++;

        // change effected cells colors
        for (Position cellEffected : cellsEffected) {
            // change the cell color and draw it
            changeCellColor(cellEffected, value);
        }
    }

    /**
     * getCellColor(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col.
     * @return the color of the board at (row,col).
     */
    public Cell getCellColor(int row, int col) {
        // Local Variables
        return this.board[row][col];
    }

    /**
     * getCellColor(Position p).
     *
     * @param p Position -- a position
     * @return the color of the board at (row,col).
     */
    public Cell getCellColor(Position p) {
        return getCellColor(p.getRow(), p.getCol());
    }

    /**
     * getOccupiedCells().
     *
     * @return a copied list of the cells occupied on this board.
     */
    public List<Position> getOccupiedCells() {
        List<Position> cellsInUse = new ArrayList<>();
        cellsInUse.addAll(this.occupiedCells);

        return cellsInUse;
    }

    /**
     * getEmptyNeighbors(GameCell p).
     *
     * @param p Position -- a position.
     * @return a list of empty neighbors (as positions) around a given position.
     */
    public List<Position> getEmptyNeighbors(Position p) {
        // Local Variables
        List<Position> emptyNeighborsList = new ArrayList<>();
        int row = p.getRow();
        int col = p.getCol();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                // inner loop variables
                int dRow = row + i;
                int dCol = col + j;

                if (cellOnBoard(dRow, dCol)) {
                    // p on board -> get it's color
                    Cell c = this.board[dRow][dCol];

                    if (c == Cell.EMPTY) {
                        // empty val -> push p's index to the list
                        emptyNeighborsList.add(new Position(dRow, dCol));
                    }
                }
            }
        }

        return emptyNeighborsList;
    }

    /**
     * onBoard(Cell color).
     *
     * @param color Cell -- a color.
     * @return the number of pieces with this color on the board.
     */
    public int onBoard(Cell color) {
        // Local Variables
        int count = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board[i][j] == color)
                    ++count;
            }
        }

        return count;
    }

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * initializeBoardSize(int type).
     *
     * @param type int -- board type
     * @return the size of the board according to it's type.
     */
    private int initializeBoardSize(int type) {
        if (type == TYPE_1) {
            return SIZE_4;
        } else if (type == TYPE_2) {
            return SIZE_6;
        } else if (type == TYPE_9) {
            return SIZE_20;
        } else if (type == TYPE_4) {
            return SIZE_10;
        } else if (type == TYPE_5) {
            return SIZE_12;
        } else if (type == TYPE_6) {
            return SIZE_14;
        } else if (type == TYPE_7) {
            return SIZE_16;
        } else if (type == TYPE_8) {
            return SIZE_18;
        }

        // default size is 8
        return 8;
    }

    /**
     * initializeBoard().
     */
    private void initializeBoard() {
        this.cellsInUse = 0;
        this.board = new Cell[size][size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = Cell.EMPTY;
            }
        }

        board[size / 2][size / 2] = player1Color;
        board[size / 2 - 1][size / 2] = player2Color;
        board[size / 2][size / 2 - 1] = player2Color;
        board[size / 2 - 1][size / 2 - 1] = player1Color;
    }

    /**
     * cellPotential(Position p, Cell color)
     *
     * @param p     Position -- a position on board.
     * @param color Cell -- cell's color
     * @return the cell potential as a List<Position>
     */
    private List<Position> cellPotential(Position p, Cell color) {
        return getCellsPotentialAsList(p, color);
    }

    /**
     * getCellsPotentialAsList(Position p, Cell value).
     *
     * @param p     Position -- a position on board.
     * @param color Cell -- player color.
     * @return a list of GameCells, representing the cell potential.
     */
    private List<Position> getCellsPotentialAsList(Position p, Cell color) {
        // Local Variables
        List<Position> potentialList = new ArrayList<>();
        int dRow, dCol;

        for (dRow = -1; dRow <= 1; ++dRow) {
            for (dCol = -1; dCol <= 1; ++dCol) {
                List<Position> potentialInPath = findPotentialInPath(p, dRow, dCol, color);

                if (!potentialInPath.isEmpty()) {
                    // add potential
                    potentialList.addAll(potentialInPath);
                }
            }
        }
        return potentialList;
    }

    /**
     * findPotentialInPath(Position p, int dRow, int dCol, Cell color).
     *
     * @param p     Position -- a position on board.
     * @param dRow  int -- row delta.
     * @param dCol  int -- column delta.
     * @param color Cell -- p color.
     * @return a list of potential cells to effect in a given row/col delta.
     */
    private List<Position> findPotentialInPath(Position p, int dRow, int dCol, Cell color) {
        // Local Variables
        List<Position> potentialInPath = new ArrayList<>();
        int row = p.getRow() + dRow;
        int col = p.getCol() + dCol;
        int counter = 0;

        // delta is 0 - no need to check anything
        if (dRow == 0 && dCol == 0)
            return potentialInPath;

        for (; cellOnBoard(row, col); row += dRow, col += dCol) {
            // Inner loop variables
            Cell currentCellColor = board[row][col];

            // hit an empty p
            if (currentCellColor == Cell.EMPTY) {
                // clear potential
                potentialInPath.clear();
                return potentialInPath;
            }

            // player type is found -> return
            if (currentCellColor == color) {
                if (counter == 0)
                    potentialInPath.clear();
                return potentialInPath;
            }

            // getting so far means that this p is an opponent p -> add it!
            potentialInPath.add(new Position(row, col));
            ++counter;
        }

        if (!cellOnBoard(row, col))
            potentialInPath.clear();

        return potentialInPath;
    }

    /**
     * changeCellColor(Position p).
     *
     * @param p Position -- a position on the board.
     */
    private void changeCellColor(Position p, Cell newColor) {
        // Local Variables
        int row = p.getRow();
        int col = p.getCol();

        // change color
        board[row][col] = newColor;
    }

    /**
     * cellOnBoard(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col.
     * @return true if cell is on board, or false otherwise.
     */
    private boolean cellOnBoard(int row, int col) {
        return (0 <= row && row < this.size && 0 <= col && col < this.size);
    }

    /**
     * initializeOccupiedCells().
     *
     * @return a list of occupied cells.
     */
    private List<Position> initializeOccupiedCells() {
        // Local Variables
        List<Position> occupiedCells = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Cell currentColor = board[i][j];
                if (currentColor != Cell.EMPTY) {
                    occupiedCells.add(new Position(i, j));
                    cellsInUse++;
                }
            }
        }
        return occupiedCells;
    }
}