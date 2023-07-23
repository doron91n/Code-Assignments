
/**
 * main.java
 *
 */
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main class - the Application(Game) main class.
 */
public class Main extends Application {
    /**
     * Function: main
     * Description: the main function, launches the application.
     *
     * @param args - user entered arguments.
     * @return void.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Function: start
     * Description: sets and starts the Application .
     *
     * @param stage - the stage upon the Application will show up on.
     * @return void.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Reversi");
        stage.setScene(createScene(loadMainPane()));
        stage.show();
    }

    /**
     * Function: createScene
     * Description: Creates the main application scene.
     *
     * @param mainPane - the main application layout.
     * @return scene - the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("Dark.css").toExternalForm());
        return scene;
    }

    /**
     * Function: loadMainPane
     * Description: Loads the main fxml layout,Sets up the window switching WindowNavigator,
     * Loads the first window into the fxml layout.
     *
     * @param void.
     * @return mainPane - the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(WindowNavigator.MAIN));
        MainController mainController = loader.getController();
        WindowNavigator.setMainController(mainController);
        WindowNavigator.loadWindow(WindowNavigator.MENU);
        return mainPane;
    }
}
