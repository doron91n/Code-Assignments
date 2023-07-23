package animation;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprite.SpriteCollection;

/**
 * a PauseScreen class.
 *
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private SpriteCollection sprites;

    /**
     * the PauseScreen constructor ,by pressing p we pause the screen and pressing space continues.
     *
     * @param k - the KeyBoard sensor.
     * @param sprite - the game level sprite collection list, shown when screen is paused.
     */
    public PauseScreen(KeyboardSensor k, SpriteCollection sprite) {
        this.keyboard = k;
        this.stop = false;
        this.sprites = sprite;
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        this.sprites.drawAllOn(d);
        d.setColor(Color.DARK_GRAY);
        d.drawText(d.getWidth() / 4, 150 + d.getHeight() / 2, "paused -- press space to continue", 32);
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