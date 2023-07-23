
/*
 * MenuController.java
 *
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for the menu window.
 */
public class MenuController {

    @FXML
    void over(ActionEvent event) {
        WindowNavigator.loadWindow("gameOver.fxml");
    }

    /**
     * Function: changeSetting
     * Description: Event handler fired when the user requests the settings window.
     *
     * @param event - the event that triggered the handler.
     * @return void.
     */
    @FXML
    void changeSetting(ActionEvent event) {
        WindowNavigator.loadWindow(WindowNavigator.SETTING);
    }

    /**
     * Function: playGame
     * Description: Event handler fired when the user requests the play game (board) window.
     *
     * @param event - the event that triggered the handler.
     * @return void.
     */
    @FXML
    void playGame(ActionEvent event) {
        WindowNavigator.loadWindow(WindowNavigator.GAME);
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