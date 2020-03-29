package graphics_control.game_control;

import game_object.Board;
import game_object.Cell;

/**
 * This class represents parsed values.
 */
public class ParsedValues {
    private boolean firstTurnPlayer;
    private Cell player1Color;
    private Cell player2Color;
    private int boardType;

    /**
     * Creates a new parsed values object.
     *
     * @param startingPlayer The player who starts the game.
     * @param player1Col     The 1st player's color.
     * @param player2Col     The 2nd player's color.
     * @param brdsize        The board size.
     */
    public ParsedValues(String startingPlayer, String player1Col, String player2Col, String brdsize) {
        if (startingPlayer.equals("Player1")) {
            this.firstTurnPlayer = false;
        } else {
            this.firstTurnPlayer = true;
        }

        this.player1Color = getCellFromString(player1Col);
        this.player2Color = getCellFromString(player2Col);

        if (brdsize.equals("4x4")) {
            this.boardType = Board.TYPE_1;
        } else if (brdsize.equals("6x6")) {
            this.boardType = Board.TYPE_2;
        } else if (brdsize.equals("8x8")) {
            this.boardType = Board.TYPE_3;
        } else if (brdsize.equals("10x10")) {
            this.boardType = Board.TYPE_4;
        } else if (brdsize.equals("12x12")) {
            this.boardType = Board.TYPE_5;
        } else if (brdsize.equals("14x14")) {
            this.boardType = Board.TYPE_6;
        } else if (brdsize.equals("16x16")) {
            this.boardType = Board.TYPE_7;
        } else if (brdsize.equals("18x18")) {
            this.boardType = Board.TYPE_8;
        } else if (brdsize.equals("20x20")) {
            this.boardType = Board.TYPE_9;
        }
    }

    /**
     * getCellFromString(String str).
     *
     * @param str String -- a string representing Cell color.
     * @return the color ass Cell object
     */
    public Cell getCellFromString(String str) {
        switch (str) {
            case "Black":
                return Cell.BLACK;
            case "White":
                return Cell.WHITE;
            case "Green":
                return Cell.GREEN;
            case "Blue":
                return Cell.BLUE;
            case "Brown":
                return Cell.BROWN;
            case "Orange":
                return Cell.ORANGE;
            case "Red":
                return Cell.RED;
            default:
                return Cell.EMPTY;
        }

    }

    /**
     * Return the first turn player's string.
     *
     * @return The first turn player's string.
     */
    boolean getFirstTurnPlayer() {
        return this.firstTurnPlayer;
    }

    /**
     * Return the 1st player's color string.
     *
     * @return The 1st player's color string.
     */
    Cell getPlayer1Color() {
        return this.player1Color;
    }

    /**
     * Return the 2nd player's color string.
     *
     * @return The 2nd player's color string.
     */
    Cell getPlayer2Color() {
        return this.player2Color;
    }

    /**
     * Return the board size string.
     *
     * @return The board size string.
     */
    int getBoardType() {
        return this.boardType;
    }
}
