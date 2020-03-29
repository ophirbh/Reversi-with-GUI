package graphics_control.game_control;

import game_logic.TurnsManager;
import game_object.Board;
import game_object.Cell;
import game_object.Position;
import graphics_control.event_handling.controllers_events.GameOverEvent;
import graphics_control.event_handling.controllers_events.GameOverEventArguments;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import graphics_control.factory.CellMatrixGenerator;
import graphics_control.drawables.GameCell;
import graphics_control.event_handling.game.CellPressedHandler;
import graphics_control.event_handling.BoardEventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardController extends GridPane {
    //--------- INNER CLASS VARIABLES ----------
    private Board gameBoard;
    private GameCell[][] gameCells;
    private TurnsManager turnsManager;
    private BoardEventHandler boardEventHandler;
    List<GameCell> availableMoves;

    //---------- CLASS STATIC VARIABLES ----------
    private static int BOARD_HEIGHT = 400;
    private static int BOARD_WIDTH = 400;


    /**
     * BoardController(int boardType).
     *
     * @param board Board -- a board to be held by this board controller
     */
    public BoardController(Board board, TurnsManager turnsManager) {
        // create the game board and graphics board
        this.gameBoard = board;
        this.turnsManager = turnsManager;

        // create the grid
        this.setPadding(new Insets(2));
        this.setHgap(2);
        this.setVgap(2);

        // set Grid Pane sizes
        this.setPrefHeight(BOARD_HEIGHT);
        this.setPrefWidth(BOARD_WIDTH);

        CellMatrixGenerator matrixGenerator = new CellMatrixGenerator();
        this.gameCells = matrixGenerator.generateMatrix(this);
        this.availableMoves = new ArrayList<>();

        initializeFXML();
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * evaluateAvailableMovesForThisTurn(List<Position> availableMoves).
     *
     * @param availableMoves List<Position> -- a list of positions of the available moves for this turn.
     */
    public void evaluateAvailableMovesForThisTurn(List<Position> availableMoves) {
        // clear all available moves
        for (GameCell availableCell : this.availableMoves) {
            availableCell.setAvailableMove(false);
        }

        // update cells on the GameCells matrix
        for (Position p : availableMoves) {
            int row = p.getRow();
            int col = p.getCol();

            GameCell gC = gameCells[row][col];

            // change color accordingly and set to not available.
            gC.setColor(gameBoard.getCellColor(row, col));
            gC.setAvailableMove(true);
            this.availableMoves.add(gC);
        }
    }

    /**
     * moveMade(int row, int col, Cell color).
     *
     * @param row   int -- row number.
     * @param col   int -- col number
     * @param color Cell -- cell color
     */
    public void moveMade(int row, int col, Cell color) {
        // update the game board
        gameBoard.moveMade(new Position(row, col), color);

        // update the GameCell matrix
        GameCell updateCell = gameCells[row][col];
        updateCell.setCellOccupied(true);
        updateCell.setColor(color);
        updateCell.setAvailableMove(false);

        updateBoard();
    }

    /**
     * evaluateMove().
     * <p>
     * Once a move was made, we will need to check whether the player now has any available moves to make.
     * In case he doesn't we will want to skip his turn. In case the game is stuck in a position where both
     * players cannot move - the game is over.
     */
    public void evaluateMove() {
        // check if game is over
        if (this.gameBoard.getSpaceLeft() == 0) {
            gameOver();
            return;
        }

        if (turnsManager.availableMoves() != 0)
            return;

        turnsManager.endTurn();
        turnsManager.evaluateAvailableMovesForThisTurn();
        evaluateAvailableMovesForThisTurn(turnsManager.getAvailableMoves());

        if (turnsManager.availableMoves() != 0)
            return;

        // no moves left for both players, force to end the game
        this.gameBoard.setSpaceLeft(0);
        gameOver();
    }

    /**
     * evaluatePlayer2Score().
     *
     * @return player 1 score.
     */
    public int evaluatePlayer1Score() {
        return this.gameBoard.onBoard(turnsManager.getPlayer1Color());
    }

    /**
     * evaluatePlayer2Score().
     *
     * @return player 2 score.
     */
    public int evaluatePlayer2Score() {
        return this.gameBoard.onBoard(turnsManager.getPlayer2Color());
    }

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * updateBoard().
     */
    private void updateBoard() {
        // Local Variables
        int size = gameBoard.getSize();

        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                GameCell gC = gameCells[row][col];
                gC.setColor(gameBoard.getCellColor(row, col));
            }
        }
    }

    /**
     * initializeFXML().
     */
    private void initializeFXML() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("Board.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        // try to load fxml loader
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * gameOver().
     */
    private void gameOver() {
        // Local Variables
        String winner, loser;
        int winnerScore, loserScore;
        Cell player1Color = turnsManager.getPlayer1Color();
        Cell player2Color = turnsManager.getPlayer2Color();

        // check winner
        int winScore = gameBoard.onBoard(player1Color);
        int loseScore = gameBoard.onBoard(player2Color);

        if (winScore > loseScore) {
            winner = player1Color.toString();
            loser = player1Color.toString();
            winnerScore = winScore;
            loserScore = loseScore;
        } else {
            winner = player2Color.toString();
            loser = player1Color.toString();
            winnerScore = loseScore;
            loserScore = winScore;
        }

        // create arguments
        GameOverEventArguments args =
                new GameOverEventArguments(winner, loser, winnerScore, loserScore);

        // handle event
        boardEventHandler.gameOver(new GameOverEvent(args));
    }

    //---------- GETTERS & SETTERS ----------

    /**
     * getBoardType().
     *
     * @return the size of the board controlled by this controller.
     */
    public int getBoardSize() {
        return this.gameBoard.getSize();
    }

    /**
     * getGameCell(int row, int col).
     *
     * @param row int -- row number
     * @param col int -- col number
     * @return the game cell at this row,col
     */
    public GameCell getGameCell(int row, int col) {
        return gameCells[row][col];
    }

    /**
     * cellColorAt(int row, int col).
     *
     * @param row int -- row number.
     * @param col int -- col numer.
     * @return the color of the cell on given row,col
     */
    public Cell cellColorAt(int row, int col) {
        return this.gameBoard.getCellColor(row, col);
    }

    //---------- HANDLERS -----------

    /**
     * attachBoardEventHandler(BoardEventHandler boardEventHandler).
     *
     * @param boardEventHandler BoardEventHandler -- the board event handler
     */
    public void attachBoardEventHandler(BoardEventHandler boardEventHandler) {
        this.boardEventHandler = boardEventHandler;
        attachListenerToGameCells();
    }


    /**
     * attachListenerToGameCells().
     */
    public void attachListenerToGameCells() {
        // Local Variables
        int boardSize = gameBoard.getSize();
        CellPressedHandler cellPressedHandler = this.boardEventHandler.getCellPressedHandler();

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                gameCells[i][j].attachMousePressedListener(cellPressedHandler);
            }
        }
    }

    //---------- GRAPHICS ----------

    /**
     * draw().
     */
    public void draw() {
        // local variables
        int boardSize = gameBoard.getSize();

        // clear children
        this.getChildren().clear();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameCells[i][j].draw();
            }
            this.addColumn(i);
        }
    }
}
