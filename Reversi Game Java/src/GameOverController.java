
/*
 * GameOverController.java
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller class for the Game Over window.
 */
public class GameOverController {
    private static final String GAME_OVER_FILE_TXT = "gameOver.txt";

    // a text for displaying desired messages to user.
    @FXML
    private Text game_over_text;
    @FXML
    private Text game_over_winner_text;

    /**
     * Function:initialize
     * Description: display the game over text to user (winner + score).
     *
     * @param void.
     * @return void.
     */
    public void initialize() {
        try {
            readGameOverFile();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Function: readGameOverFile
     * Description: reads the game over file , and display the result:
     * Tie / winner color name and score.
     * 
     * @param void.
     * @return void.
     * @throws Exception "Error: failed reading file GAME_OVER_FILE_TXT "
     */
    public void readGameOverFile() throws Exception {
        String line = null;
        String winner_color = "Not Found";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(GAME_OVER_FILE_TXT));
            while ((line = bufferedReader.readLine()) != null) {
                // skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                // player 1 won
                if (line.contains("X")) {
                    winner_color = ColorUtils.getColorNamefromColor(SettingsReader.getPlayerColor(1));
                    line = line.replaceAll("X", winner_color);
                }
                // player 2 won
                if (line.contains("O")) {
                    winner_color = ColorUtils.getColorNamefromColor(SettingsReader.getPlayerColor(2));
                    line = line.replaceAll("O", winner_color);
                }
                game_over_winner_text.setText(line);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            throw new Exception("Error: failed reading file '" + GAME_OVER_FILE_TXT + "'");
        } finally {
            // try closing the reader
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
    }

    /**
     * Function: exit
     * Description: Event handler fired when the user requests to exit the application.
     *
     * @param event - the event that triggered the handler.
     * @return void.
     */
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }
}