import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

/*
 * BoardController.java
 *
 *      
 */

/**
 * Controller class for the board (play) window.
 */
public class BoardController extends GridPane {
    @FXML
    private GamePiece[][] piece_matrix;
    @FXML
    private GridPane board_grid;
    private Color player_1_color;
    private Color player_2_color;

    /**
     * Function:initialize
     * Description:
     *
     * @param void.
     * @return void.
     */
    public void initialize() {

        int board_size = SettingsReader.getBoardSize();
        Rectangle[][] rec = new Rectangle[board_size][board_size];
        double width = 40;
        board_grid.getRowConstraints().clear();
        board_grid.getColumnConstraints().clear();
        board_grid.setPrefSize(width * board_size, width * board_size);
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * width);
                rec[i][j].setY(j * width);
                rec[i][j].setWidth(width);
                rec[i][j].setHeight(width);
                rec[i][j].setFill(Color.DARKORCHID);
                rec[i][j].setStroke(Color.SPRINGGREEN);
                board_grid.getChildren().add(rec[i][j]);
                board_grid.getColumnConstraints().add(new ColumnConstraints(rec[i][j].computeAreaInScreen()));
                board_grid.getRowConstraints().add(new RowConstraints(rec[i][j].computeAreaInScreen()));
            }
        }

    }

    public Pane makeGrid(int n) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double width = 20;// primaryScreenBounds.getWidth() / n;
        System.out.println(
                "in makeGrid width: " + primaryScreenBounds.getWidth() + " calcau width: " + width + " n: " + n);
        Pane p = new Pane();

        Rectangle[][] rec = new Rectangle[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * width);
                rec[i][j].setY(j * width);
                rec[i][j].setWidth(width);
                rec[i][j].setHeight(width);
                rec[i][j].setFill(Color.BLACK);
                rec[i][j].setStroke(Color.BLACK);
                p.getChildren().add(rec[i][j]);
            }
        }

        return p;
    }
}