
/*
 * SettingController.java
 *
 */
import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Controller class for the settings window.
 */
public class SettingController {
    private final static ObservableList<String> board_size_list = FXCollections.observableArrayList("4 X 4", "6 X 6",
            "8 X 8", "10 X 10", "12 X 12", "14 X 14", "16 X 16", "18 X 18", "20 X 20");
    private final static ObservableList<String> op_player_list = FXCollections.observableArrayList("Player 1",
            "Player 2");
    private final static ObservableList<String> player_color_list = FXCollections
            .observableArrayList(ColorUtils.getColorsNamesList());
    private final static String CHOOSE_DIFFERENT_COLOR = "Settings were not saved, Players colors need to be different ,please choose a different color";
    private final static String CHOOSE_BOARD_SIZE = "Settings were not saved, Board Size was not set ,please choose one of the available options";
    private final static String CHOOSE_OPPENING_PLAYER = "Settings were not saved, Opening Player was not set ,please choose one of the available options";
    // a text for displaying desired messages to user.
    @FXML
    private Text settings_msg_text;
    @FXML
    private ComboBox<String> board_size;
    @FXML
    private ComboBox<String> opening_player;
    @FXML
    private ComboBox<String> player_1_color;
    @FXML
    private ComboBox<String> player_2_color;
    @FXML
    private Circle player_1_color_displayer;
    @FXML
    private Circle player_2_color_displayer;

    /**
     * Function:initialize
     * Description: loads the last saved settings from the settings.txt file ,
     * if such a file not found default values are loaded.
     *
     * @param void.
     * @return void.
     */
    public void initialize() {
        // each comboBox have a listener in case the value is changed
        board_size.getItems().clear();
        board_size.getItems().addAll(board_size_list);
        board_size.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                settings_msg_text.setText("");
            }
        });
        opening_player.getItems().clear();
        opening_player.getItems().addAll(op_player_list);
        opening_player.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                settings_msg_text.setText("");
            }
        });
        player_1_color.getItems().clear();
        player_1_color.getItems().addAll(player_color_list);
        player_1_color.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                settings_msg_text.setText("");
                player_1_color_displayer.setFill(Color.valueOf(newValue));
            }
        });
        player_2_color.getItems().clear();
        player_2_color.getItems().addAll(player_color_list);
        player_2_color.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                settings_msg_text.setText("");
                player_2_color_displayer.setFill(Color.valueOf(newValue));
            }
        });
        // reads old settings onto the combo boxs.
        try {
            readSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function: backToMenu
     * Description: Event handler fired when the user requests a previous window (Menu window).
     *
     * @param event - the event that triggered the handler.
     * @return void.
     */
    @FXML
    private void backToMenu(ActionEvent event) {
        WindowNavigator.loadWindow(WindowNavigator.MENU);
    }

    /**
     * Function: saveSettings
     * Description: Event handler fired when the user requests to save current game settings.
     *
     * @param event - the event that triggered the handler.
     * @return void.
     */
    @FXML
    private void saveSettings(ActionEvent event) {
        // players colors need to be different
        if (player_1_color.getValue().equals(player_2_color.getValue())) {
            settings_msg_text.setText(CHOOSE_DIFFERENT_COLOR);
        }
        // opening player need to be set
        if (opening_player.getValue() == null) {
            settings_msg_text.setText(CHOOSE_OPPENING_PLAYER);
        }
        // board size need to be set
        if (board_size.getValue() == null) {
            settings_msg_text.setText(CHOOSE_BOARD_SIZE);
        }
        // All fields were filled , save the current settings
        if ((board_size.getValue() != null) && (opening_player.getValue() != null)
                && !player_1_color.getValue().equals(player_2_color.getValue())) {
            Vector<String> settings_values = new Vector<String>();
            settings_values.addElement(board_size.getValue());
            settings_values.addElement(opening_player.getValue());
            settings_values.addElement(player_1_color.getValue());
            settings_values.addElement(player_2_color.getValue());
            try {
                SettingsReader.saveSettings(settings_values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function: readSettings
     * Description: Event handler fired when the user requests to read last saved game settings.
     * if setting.txt file is not found default settings are loaded.
     *
     * @param void.
     * @return void.
     * @throws Exception "Error: Failed to read game settings , missing parameters."
     * @throws Exception ""Error: Failed reading settings, reason: .."
     */
    @FXML
    private void readSettings() throws Exception {
        try {
            Vector<String> settings_values = SettingsReader.readSettings();
            if ((settings_values == null) || (settings_values.size() != 4)) {
                throw new Exception("Error: Failed to read game settings , missing parameters.");
            } else {
                // load the last saved settings onto the combo boxes.
                board_size.setValue(settings_values.get(0));
                opening_player.setValue(settings_values.get(1));
                player_1_color.setValue(settings_values.get(2));
                player_2_color.setValue(settings_values.get(3));
            }
        } catch (Exception e) {
            throw new Exception("Error: Failed reading settings, reason: " + e.getMessage());
        }
    }
}