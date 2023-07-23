package animation;

import java.awt.Color;

import background.Floyd;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprite.Sprite;

/**
 * a EndScreen class.
 *
 */

public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private String finalMsg;
    private int finalScore;

    /**
     * the EndScreen constructor , displayed once the player finish playing the game.
     * display the player final score and : if the player is out of lives write Game Over else if the player cleared all
     * levels write You Win!,the endScreen stays until space is pressed.
     *
     * @param msg - the final message to be displayed (you win! || game over).
     * @param score - the player final game score.
     * @param k - the KeyBoard sensor.
     */
    public EndScreen(KeyboardSensor k, String msg, int score) {
        this.keyboard = k;
        this.stop = false;
        this.finalMsg = msg;
        this.finalScore = score;
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        Sprite s = new Floyd();
        s.drawOn(d);
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 4, d.getHeight() - 125, this.finalMsg + this.finalScore, 32);
        d.drawText(d.getWidth() / 4, d.getHeight() - 50, "press space to continue", 32);
        // press space to continue.
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
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
