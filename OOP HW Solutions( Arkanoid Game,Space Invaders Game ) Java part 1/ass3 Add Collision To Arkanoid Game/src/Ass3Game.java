
import java.awt.Color;

/**
 * a Ass3Game class - assignment 3 main function (creates and runs the game).
 *
 */
public class Ass3Game {
    /**
     * the Ass3Game main - creates and runs the game.
     *
     * @param args - user entered arguments,not used in this assignment.
     */
    public static void main(String[] args) {
        double screenWidth = 1000;
        double screenHeight = 700;
        // the screen blocks height for (top,under) screen limit and width for (right,left) screen limit.
        double screenLimitBlock = 30;
        Color screenColor = Color.BLUE;
        Game game = new Game(screenLimitBlock, screenWidth, screenHeight, screenColor);
        game.initialize();
        game.run();
    }
}
