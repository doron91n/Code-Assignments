package animation;

import java.awt.Color;
import java.awt.Image;

import biuoop.DrawSurface;
import game.Parsers;

/**
 * a EndScreen class.
 *
 *
 */

public class EndScreen implements Animation {
    private boolean stop;
    private int finalScore;
    private Image background = null;

    /**
     * the EndScreen constructor , displayed once the player finish playing the game.
     * display the player final score and : if the player is out of lives write Game Over else if the player cleared all
     * levels write You Win!,the endScreen stays until space is pressed.
     *
     * @param msg - the final message to be displayed (you win! || game over).
     * @param score - the player final game score.
     */
    public EndScreen(String msg, int score) {
        this.stop = false;
        this.finalScore = score;
        Parsers p = new Parsers();
        if (msg.equals("Game Over")) {
            try {
                this.background = (Image) p.getBackground("image(my_screens_Background/game-over.jpg)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (msg.equals("You Win!")) {
            try {
                this.background = (Image) p.getBackground("image(my_screens_Background/youwin.jpg)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        if (this.background != null) {
            d.drawImage(0, 0, this.background);
        }
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 4, 50, "Your Final Score is  " + this.finalScore, 32);
        d.drawText(d.getWidth() / 4, d.getHeight() - 40, "press Space Key to continue", 30);
        d.setColor(Color.GRAY);
        d.drawText(d.getWidth() / 4, d.getHeight() - 41, "press Space Key to continue", 30);
        d.drawText(d.getWidth() / 4, 51, "Your Final Score is  " + this.finalScore, 32);

    }

    /**
     * in charge of the animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
