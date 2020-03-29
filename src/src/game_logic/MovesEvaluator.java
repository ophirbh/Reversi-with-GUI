package game_logic;

import game_object.Board;
import game_object.Cell;
import game_object.Position;
import graphics_control.drawables.GameCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovesEvaluator {

    /**
     * MovesEvaluator().
     */
    MovesEvaluator() {}

    /**
     * calculateAvailableMoves(Board gameBoard, Cell playerColor).
     *
     * @param gameBoard   Board -- a game board
     * @param playerColor Cell -- a player color
     * @return a list of available moves for this player as List<Position>.
     */
    public List<Position> calculateAvailableMoves(Board gameBoard, Cell playerColor) {
        List<Position> cellsInUse = gameBoard.getOccupiedCells();
        List<Position> availableMoves = new ArrayList<>();

        for (Position p : cellsInUse) {
            int row = p.getRow();
            int col = p.getCol();

            if (playerColor != gameBoard.getCellColor(row, col)) {
                // Get empty neighbors for current position
                List<Position> emptyNeighbors = gameBoard.getEmptyNeighbors(p);

                for(Position neighbor : emptyNeighbors) {
                    List<Position> neighborPotential =
                            gameBoard.getCellPotentialAsList(neighbor, playerColor);

                    if(!neighborPotential.isEmpty()) {
                        availableMoves.add(neighbor);
                    }
                }

            }
        }

        // clear duplicates
        Set<Position> posSet = new HashSet<>();
        posSet.addAll(availableMoves);
        availableMoves.clear();
        availableMoves.addAll(posSet);

        return availableMoves;
    }
}