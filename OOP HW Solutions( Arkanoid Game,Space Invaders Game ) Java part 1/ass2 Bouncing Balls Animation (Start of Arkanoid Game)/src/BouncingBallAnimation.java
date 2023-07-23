
/**
 */
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * a BouncingBallAnimation class.
 */
public class BouncingBallAnimation {

    /**
     * the BouncingBallsAnimation main method ,creates a bouncing ball animation.
     *
     * @param args - none.
     */
    public static void main(String[] args) {
        int screenStartX = 0;
        int screenStartY = 0;
        int screenHeight = 200;
        int screenWidth = 200;
        int centerX = 0;
        int centerY = 0;
        int radius = 30;
        GUI gui = new GUI("BouncingBallsAnimation", screenWidth, screenHeight);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(centerX, centerY, radius, java.awt.Color.BLACK);
        Velocity v = Velocity.fromAngleAndSpeed(90, 2);
        ball.setVelocity(v);
        ball.setScreenSize(screenStartX, screenStartY, screenWidth, screenHeight);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(050); // wait for 50 milliseconds.
        }
    }
}
