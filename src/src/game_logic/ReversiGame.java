package game_logic;

import game_object.Board;
import game_object.Cell;
import game_object.Position;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * graphics_control.game_control.ReversiMain Class.
 */
public class ReversiGame {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private TurnsManager manager;
    //private GameStartedEventHandler eventHandler;

    //---------- INITIALIZER ----------

    /**
     * graphics_control.game_control.ReversiMain(Board b).
     *
     * @param b Board -- a game board.
     */
    public ReversiGame(Board b) {
        this.board = b;
        this.manager = new TurnsManager(this.board);
        //this.eventHandler = gameStartedEventHandler;
    }

    //---------- GETTERS ----------

    /**
     * getBoard().
     *
     * @return the board played by this game.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * start().
     */
    public void start() {
        // draw board

        while (this.board.getSpaceLeft() > 0) {
            if(manager.availableMoves() != 0) {
                // Local Variables
                Position move = this.manager.nextMove();

                // play a move
                this.board.moveMade(move, this.manager.getCurrentPlayerColor());

                // notify handler
                //this.eventHandler.handleMoveMade(move);
            }

            // end turn
            manager.endTurn();
        }
        endGame();
    }

    /**
     * endGame().
     */
    private void endGame() {
        System.out.println(calculateWinner());
    }

    /**
     * calculateWinner().
     *
     * @return "W" if white player is the winner or "B" if black is.
     */
    private String calculateWinner() {
        int whiteScore = 0;
        int blackScore = 0;

        for(int i = 0; i < board.getSize(); ++i) {
            for (int j = 0; j < board.getSize(); ++j) {
                if(board.getCellColor(i, j) == Cell.WHITE) {
                    ++whiteScore;
                    continue;
                }
                ++blackScore;
            }
        }

        String winner = whiteScore > blackScore ? "W" : "B";
        try {
            Files.write(Paths.get("output.txt"), winner.getBytes());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println(e.getMessage());
        }

        return winner;
    }
}
