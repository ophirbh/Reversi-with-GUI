package graphics_control.event_handling.controllers_events;

/**
 * GameOverEventArguments Class.
 */
public class GameOverEventArguments {
    private String winner;
    private String loser;
    private int winnerScore;
    private int loserScore;

    /**
     * GameOverEventArguments(Color winner, Color loser, int winnerScore, int loserScore).
     *
     * @param winner Color -- the color of the winning player.
     * @param loser Color -- the color of the losing player.
     * @param winnerScore -- winner's score.
     * @param loserScore -- loser's score.
     */
    public GameOverEventArguments(String winner, String loser, int winnerScore, int loserScore) {
        this.winner = winner;
        this.loser = loser;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
    }

    /**
     * getWinner()
     *
     * @return the Color of the winner passed by the event arguments.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * getLoser()
     *
     * @return the Color of the loser passed by the event arguments.
     */
    public String getLoser() {
        return loser;
    }

    /**
     * getWinnerScore()
     *
     * @return the winner's score passed by the event arguments.
     */
    public int getWinnerScore() {
        return winnerScore;
    }

    /**
     * getLoserScore()
     *
     * @return the loser's score passed by the event arguments.
     */
    public int getLoserScore() {
        return loserScore;
    }

}
