
/*
 * WindowNavigator.java
 *
 */
import java.io.IOException;

import javafx.fxml.FXMLLoader;

/**
 * Utility class for controlling navigation between windows.
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class WindowNavigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String MAIN = "main.fxml";
    public static final String MENU = "menu.fxml";
    public static final String SETTING = "setting.fxml";
    public static final String GAME = "board.fxml";

    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Function: setMainController
     * Description: Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     * @return void.
     */
    public static void setMainController(MainController mainController) {
        WindowNavigator.mainController = mainController;
    }

    /**
     * Function: loadWindow
     * Description: Loads the window specified by the fxml file into the windowHolder pane of the main application
     * layout.
     * Previously loaded window for the same fxml file are not cached, The fxml is loaded anew and a new window node
     * hierarchy generated every time this method is invoked.
     * 
     * @param fxml the fxml file to be loaded.
     * @return void.
     */
    public static void loadWindow(String fxml) {
        try {
            mainController.setWindow(FXMLLoader.load(WindowNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}