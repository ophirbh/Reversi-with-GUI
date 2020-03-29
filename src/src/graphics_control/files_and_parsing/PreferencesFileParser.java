package graphics_control.files_and_parsing;

import graphics_control.game_control.ParsedValues;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * PreferencesFileParser Class.
 */
public class PreferencesFileParser {

    static final int PREFERENCE = 0;
    static final int CHOICE = 1;

    /**
     * parseFile().
     *
     * @return a ParsedValues object, containing the preferences set by the player.
     */
    public ParsedValues parseFile() {
        String firstTurnPlayer = null;
        String player1Color = null;
        String player2Color = null;
        String boardSize = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("preferences.txt"));
            String currLine;

            while ((currLine = bufferedReader.readLine()) != null) {
                String[] preference = currLine.split(" ");
                switch (preference[PREFERENCE]) {
                    case "firstTurnPlayer:":
                        firstTurnPlayer = preference[CHOICE];
                        break;
                    case "player1Color:":
                        player1Color = preference[CHOICE];
                        break;
                    case "player2Color:":
                        player2Color = preference[CHOICE];
                        break;
                    case "boardSize:":
                        boardSize = preference[CHOICE];
                        break;
                }
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ParsedValues(firstTurnPlayer, player1Color, player2Color, boardSize);
    }
}
