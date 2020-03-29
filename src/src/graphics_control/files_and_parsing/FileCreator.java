package graphics_control.files_and_parsing;

import game_object.Cell;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * FileCreator Class.
 */
public class FileCreator {
    //----------- CLASS STATIC VARIABLES ----------
    public static final String DEFAULT_FIRST_TURN = "Player1";
    public static final String DEFAULT_PLAYER1_COLOR = Cell.BLACK.toString();
    public static final String DEFAULT_PLAYER2_COLOR = Cell.WHITE.toString();
    public static final String DEFAULT_BOARD_SIZE = "8X8";

    /**
     * Create a default preferences file.
     */
    public void createDefaultFile() {

        try {
            File preferences = new File("preferences.txt");
            Files.deleteIfExists(preferences.toPath());
        }catch(IOException e){
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = createFile();
        String settings = "firstTurnPlayer: " + DEFAULT_FIRST_TURN+
                "\nplayer1Color: " + DEFAULT_PLAYER1_COLOR +
                "\nplayer2Color: " + DEFAULT_PLAYER2_COLOR +
                "\nboardSize: " + DEFAULT_BOARD_SIZE;
        try {
            bufferedWriter.write(settings);
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Return a buffered writer.
     * @return Buffered writer.
     */
    public BufferedWriter createFile() {

        BufferedWriter bufferedWriter = null;
        try {
            File file = new File("preferences.txt");
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }

}
