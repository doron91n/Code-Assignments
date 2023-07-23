package background;

import java.awt.Color;

import animation.GameLevel;
import biuoop.DrawSurface;
import sprite.Sprite;

/**
 * Wide Easy class-level 2 background.
 *
 */
public class WideEasy implements Sprite {
    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int h = d.getHeight();
        int w = d.getWidth();
        d.setColor(Color.white);
        d.fillRectangle(0, 0, w, h);
        d.setColor(Color.getHSBColor(59, 85, 88));
        d.fillCircle(w / 4, h / 5, w / 8);
        d.setColor(Color.getHSBColor(59, 95, 91));
        d.fillCircle(w / 4, h / 5, w / 10);
        d.setColor(Color.YELLOW.brighter());
        d.fillCircle(w / 4, h / 5, h / 10);
        int j = d.getWidth();
        d.setColor(Color.YELLOW);
        for (int i = 0; i < w / 8; i++) {
            d.drawLine(w / 4, h / 5, j, h / 2);
            j -= 10;
        }
    }

    /**
     * notify the sprite that time has passed and invoke a certain change.
     */
    @Override
    public void timePassed() {
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
