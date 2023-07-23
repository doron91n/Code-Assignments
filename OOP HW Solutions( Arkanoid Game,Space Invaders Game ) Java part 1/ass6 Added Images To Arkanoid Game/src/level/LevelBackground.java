
package level;

import java.awt.Color;
import java.awt.Image;

import animation.GameLevel;
import biuoop.DrawSurface;
import game.Parsers;
import sprite.Sprite;

/**
 * a LevelBackground class.
 *
 */

public class LevelBackground implements Sprite {
    private Object background;

    /**
     * creates a background for the level.
     *
     * @param backg - the level background object image/color.
     * @throws Exception - "Error: Failed to load level background".
     */
    public LevelBackground(String backg) throws Exception {
        Parsers p = new Parsers();
        try {
            this.background = p.getBackground(backg);
        } catch (Exception e) {
            throw new Exception("Error: Failed to load level background");
        }
    }

    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        if (this.background != null) {
            if (this.background.getClass().getName().contains("Color")) {
                d.setColor((Color) this.background);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            }
            if (this.background.getClass().getName().contains("image")) {
                d.drawImage(0, 0, (Image) this.background);
            }
        }
    }

    /**
     * notify the sprite that time has passed and invoke a certain change.
     *
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * adds this sprite to the given game (g).
     *
     * @param g - the game to add this sprite to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
