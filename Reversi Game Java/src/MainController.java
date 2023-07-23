
/*
 * MainController.java
 *
 */
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Main controller class for the entire window layout.
 */
public class MainController {

    /** Holder of a switchable window. */
    @FXML
    private StackPane windowHolder;

    /**
     * Function: setWindow
     * Description: Replaces the window displayed in the window holder with given new window.
     *
     * @param node - the window node to be swapped instead of current window.
     * @return void.
     */
    public void setWindow(Node node) {
        windowHolder.getChildren().setAll(node);
    }
}