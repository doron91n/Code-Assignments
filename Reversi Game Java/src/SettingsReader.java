
/*
 * SettingsReader.java
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javafx.scene.paint.Color;

/**
 * SettingsReader class - reads and writes the game settings from/to file.
 */
public class SettingsReader {
    private static final String SETTINGS_FILE_TXT = "settings.txt";
    private static final String BOARD_SIZE_TXT = "Board Size";
    private static final String OPENING_PLAYER_TXT = "Opening Player";
    private static final String PLAYER_1_COLOR_TXT = "Player 1 Color";
    private static final String PLAYER_2_COLOR_TXT = "Player 2 Color";

    // a vector for the game default settings.
    private static Vector<String> getDefaultSettings() {
        Vector<String> default_settings_values = new Vector<String>();
        // Default board size is 8 X 8
        default_settings_values.add("8 X 8");
        // Default opening player is player 1
        default_settings_values.add("Player 1");
        // Default player 1 color is white
        default_settings_values.add("WHITE");
        // Default player 2 color is black
        default_settings_values.add("BLACK");
        return default_settings_values;
    }

    /**
     * Function: getOpeningPlayer
     * Description: returns a number (1/2) representing the opening player.
     *
     * @param void.
     * @return op_player - a number (1/2) representing the opening player , if 0 is returned error on read.
     */
    public static int getOpeningPlayer() {
        int op_player = 0;
        Vector<String> settings_values = null;
        try {
            settings_values = readSettings();
        } catch (Exception e) {
            settings_values = getDefaultSettings();
        }
        if (settings_values.get(1).equals("Player 1")) {
            op_player = 1;
        }
        if (settings_values.get(1).equals("Player 2")) {
            op_player = 2;
        }
        return op_player;
    }

    /**
     * Function: getBoardSize
     * Description: returns a number (4/6/8/10/12/14/16/18/20) representing the board size (n X n).
     *
     * @param void.
     * @return b_size - representing the board size (n X n) , if 0 is returned error on read.
     */
    public static int getBoardSize() {
        int b_size = 0;
        Vector<String> settings_values = null;
        try {
            settings_values = readSettings();
        } catch (Exception e) {
            settings_values = getDefaultSettings();
        }
        String[] pair = settings_values.get(0).trim().split("X");
        if (pair.length == 2) {
            b_size = Integer.parseInt(pair[0].trim());
        }
        return b_size;
    }

    /**
     * Function: getPlayerColor
     * Description: returns the color of the desired player (1/2).
     *
     * @param player_num - a number (1/2) representing the player we want the color of.
     * @return p_color - the color of the desired player , if Color.AQUA is returned error on read.
     */
    public static Color getPlayerColor(int player_num) {
        Color p_color = Color.AQUA;
        Vector<String> settings_values = null;
        try {
            settings_values = readSettings();
        } catch (Exception e) {
            settings_values = getDefaultSettings();
        }
        if (player_num == 1) {
            p_color = ColorUtils.getColorFromColorName(settings_values.get(2));
        }
        if (player_num == 2) {
            p_color = ColorUtils.getColorFromColorName(settings_values.get(3));
        }
        return p_color;
    }

    /**
     * Function: readSettings
     * Description: reads the game settings from the settings file and returns Vector with all the needed game settings
     * in order: vector[0] = board size,vector[1] = opening player,vector[2] = player 1 color,vector[3] = player 2
     * color.
     *
     * @param void.
     * @return settings_values -the color of the desired player , if error on read , default values are loaded and sent.
     * @throws Exception "Error: failed reading file SETTINGS_FILE_TXT , missing parameters."
     * @throws Exception "Error: failed reading file SETTINGS_FILE_TXT "
     */
    public static Vector<String> readSettings() throws Exception {
        String line = null;
        String player_1_color = null;
        String player_2_color = null;
        String board_size = null;
        String opening_player = null;
        BufferedReader bufferedReader = null;
        Vector<String> settings_values = new Vector<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(SETTINGS_FILE_TXT));
            while ((line = bufferedReader.readLine()) != null) {
                // skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] pair = line.trim().split(":");
                // assign values from file into variables
                if (pair.length == 2) {
                    switch (pair[0]) {
                    case BOARD_SIZE_TXT:
                        board_size = pair[1];
                        break;
                    case OPENING_PLAYER_TXT:
                        opening_player = pair[1];
                        break;
                    case PLAYER_1_COLOR_TXT:
                        player_1_color = pair[1];
                        break;
                    case PLAYER_2_COLOR_TXT:
                        player_2_color = pair[1];
                        break;
                    default:
                        break;
                    }
                } else {
                    throw new Exception("Error: failed reading file '" + SETTINGS_FILE_TXT + "', missing parameters.");
                }
            }
            if ((player_1_color == null) || (board_size == null) || (player_2_color == null)
                    || opening_player == null) {
                throw new Exception("Error: failed reading file '" + SETTINGS_FILE_TXT + "', missing parameters.");
            } else {
                // all parameters were read successfully from setting file, add them to the settings vector.
                settings_values.add(board_size);
                settings_values.add(opening_player);
                settings_values.add(player_1_color);
                settings_values.add(player_2_color);
            }
        } catch (FileNotFoundException ex) {
            // Unable to open file ,file not found, loading default parameters.
            settings_values = getDefaultSettings();
        } catch (IOException ex) {
            throw new Exception("Error: failed reading file '" + SETTINGS_FILE_TXT + "'");
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
        return settings_values;
    }

    /**
     * Function: saveSettings
     * Description: saves to the settings file the given game settings from the given settings_values Vector with all
     * the needed game settings in order: vector[0] = board size,vector[1] = opening player,vector[2] = player 1
     * color,vector[3] = player 2 color.
     *
     * @param settings_values - the vector containing all the needed game settings values.
     * @return void.
     * @throws Exception "Error: Failed to save game settings , missing parameters.".
     * @throws IOException "Error - the save to a file has failed" .
     */
    public static void saveSettings(Vector<String> settings_values) throws Exception {
        BufferedWriter writer = null;
        try {
            if ((settings_values == null) || (settings_values.size() != 4)) {
                throw new Exception("Error: Failed to save game settings , missing parameters.");
            } else {
                String settings = "";
                settings = BOARD_SIZE_TXT + ":" + settings_values.get(0) + System.lineSeparator();
                settings = settings + OPENING_PLAYER_TXT + ":" + settings_values.get(1) + System.lineSeparator();
                settings = settings + PLAYER_1_COLOR_TXT + ":" + settings_values.get(2) + System.lineSeparator();
                settings = settings + PLAYER_2_COLOR_TXT + ":" + settings_values.get(3) + System.lineSeparator();
                writer = new BufferedWriter(new FileWriter(SETTINGS_FILE_TXT));
                writer.write(settings);
            }
        } catch (IOException e) {
            throw new IOException("Error - the save to a file has failed");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                ;
            }
        }
    }
}