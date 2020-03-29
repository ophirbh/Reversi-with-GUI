package game_logic;

import game_object.Board;
import game_object.Cell;
import graphics_control.drawables.GameCell;
import game_object.Position;
import players.HumanPlayer;
import players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * TurnsManager Class.
 */
public class TurnsManager {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private Player[] players;
    private boolean playerTurn;
    private MovesEvaluator movesEvaluator;
    private List<Position> availableMoves;

    //---------- INITIALIZER ----------

    /**
     * TurnsManager(Board b, boolean startingPlayer, Cell player1Color, Cell player2Color).
     *
     * @param b Board -- the board to manage.
     * @param startingPlayer boolean -- false for player 1 or true for player 2
     * @param player1Color Cell -- Cell color.
     * @param player2Color Cell -- Cell color.
     */
    public TurnsManager(Board b, boolean startingPlayer, Cell player1Color, Cell player2Color) {
        this.board = b;
        this.movesEvaluator = new MovesEvaluator();
        this.playerTurn = startingPlayer;

        // initialize players and available moves
        initializePlayers(player1Color, player2Color);
        evaluateAvailableMovesForThisTurn();
    }

    /**
     * TurnsManager(Board b).
     *
     * @param b Board -- the board to manage.
     */
    public TurnsManager(Board b) {
        this.board = b;
        this.movesEvaluator = new MovesEvaluator();
        this.playerTurn = evaluateColor(Cell.BLACK);

        // initialize players and available moves
        initializePlayers();
        evaluateAvailableMovesForThisTurn();
    }

    //---------- GETTERS ----------

    /**
     * getPlayer1Color().
     *
     * @return the color of player 1.
     */
    public Cell getPlayer1Color() {
        return ((HumanPlayer)this.players[0]).getColor();
    }

    /**
     * getPlayer2Color().
     *
     * @return the color of player 2.
     */
    public Cell getPlayer2Color() {
        return ((HumanPlayer)this.players[1]).getColor();
    }

    /**
     * getCurrentPlayerColor().
     *
     * @return current player color.
     */
    public Cell getCurrentPlayerColor() {
        return ((HumanPlayer)players[!playerTurn ? 0 : 1]).getColor();
    }

    /**
     * availableMoves().
     *
     * @return the number of available moves.
     */
    public int availableMoves() {
        return this.availableMoves.size();
    }

    /**
     * List<Position> getAvailableMoves().
     *
     * @return a copy of the available moves for this turn
     */
    public List<Position> getAvailableMoves() {
        // Local Variables
        List<Position> aMoves = new ArrayList<>();

        // copy all moves
        aMoves.addAll(this.availableMoves);

        return aMoves;
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * moveIsLegal(Position move).
     *
     * @param move Position -- a move.
     * @return true if the move is one of the available moves for this turn.
     */
    public boolean moveIsLegal(Position move) {
        // check move within boundaries. Basically this is redundant, might save some calculations...
        if (move.getRow() < 0)
            return false;
        if (move.getCol() < 0)
            return false;
        if (move.getRow() > board.getSize())
            return false;
        if (move.getCol() > board.getSize())
            return false;

        // check move is an available move
        for(Position availableMove : this.availableMoves) {
            Position availableMovePosition = availableMove;
            if (move.getRow() == availableMovePosition.getRow()) {
                if (move.getCol() == availableMovePosition.getCol()) { return true; }
            }
        }

        // the move is within board borders yet is NOT an available move.
        return false;
    }

    /**
     * endTurn().
     */
    public void endTurn() {
        this.playerTurn = !this.playerTurn;
        evaluateAvailableMovesForThisTurn();
    }

    /**
     * nextMove().
     *
     * @return the position of the next move.
     */
    public Position nextMove() {
        // Local Variables
        Position playerMove;
        int nextToPlay = playerTurn ? 1 : 0;

        do {
            playerMove = (this.players)[nextToPlay].move();
        } while (!moveIsLegal(playerMove));

        return (playerMove);
    }

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * evaluateAvailableMovesForThisTurn().
     */
    public void evaluateAvailableMovesForThisTurn() {
        // Local Variables
        //Cell playerTurn = playerTurnToCellType();

        if (availableMoves != null) {
            this.availableMoves.clear();
        }

        this.availableMoves =
                this.movesEvaluator.calculateAvailableMoves(
                        board, ((HumanPlayer)players[!playerTurn ? 0 : 1]).getColor());
    }

    /**
     * evaluateColor(Heuristic heuristic).
     *
     * @param color Cell -- a player color.
     * @return player color as an integer: 0 for white or 1 for black.
     */
    private boolean evaluateColor(Cell color) {
        if (color == Cell.WHITE) {
            return true;
        }
        return false;
    }

    /**
     * initializePlayers(Cell player1Color, Cell player2Color).
     *
     * @param player1Color Cell -- cell color.
     * @param player2Color Cell -- cell color.
     */
    private void initializePlayers(Cell player1Color, Cell player2Color) {
        // Local Variables
        this.players = new Player[2];

        // initialize players
        this.players[1] = new HumanPlayer(player2Color, false);
        this.players[0] = new HumanPlayer(player1Color, true);
    }

    /**
     * initializePlayers().
     */
    private void initializePlayers() {
        // Local Variables
        this.players = new Player[2];

        // initialize players
        this.players[1] = new HumanPlayer(Cell.WHITE);
        this.players[0] = new HumanPlayer(Cell.BLACK);
    }

    /**
     * playerTurnToCellType().
     *
     * @return the player color.
     */
    private Cell playerTurnToCellType() {
        if (this.playerTurn)
            return Cell.WHITE;
        return Cell.BLACK;
    }
}