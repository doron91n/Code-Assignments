package animation;

import java.awt.Color;

import biuoop.DrawSurface;
import sprite.SpriteCollection;

/**
 * a PauseScreen class.
 *
 */
public class PauseScreen implements Animation {
    private boolean stop;
    private SpriteCollection sprites;

    /**
     * the PauseScreen constructor ,by pressing p we pause the screen and pressing space continues.
     *
     * @param sprite - the game level sprite collection list, shown when screen is paused.
     */
    public PauseScreen(SpriteCollection sprite) {
        this.stop = false;
        this.sprites = sprite;
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 4, d.getHeight() - 40, "press Space Key to continue", 30);
        d.setColor(Color.GRAY);
        d.drawText(d.getWidth() / 4, d.getHeight() - 41, "press Space Key to continue", 30);
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