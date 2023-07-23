package background;

import java.awt.Color;

import animation.GameLevel;
import biuoop.DrawSurface;
import sprite.Sprite;

/**
 * Green 3 class-level 3 background.
 *
 */
public class Green3 implements Sprite {

    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int h = d.getHeight();
        int w = d.getWidth();
        d.setColor(Color.green.darker().darker());
        d.fillRectangle(0, 0, w, h);
        d.setColor(Color.darkGray.darker().darker());
        d.fillRectangle((w / 8) + 55, 275, w / 60, h);
        d.setColor(Color.darkGray);
        d.fillRectangle((w / 8) + 38, 380, w / 16, 80);
        d.setColor(Color.white);
        d.fillRectangle(w / 8, h - (h / 4), w / 6, h / 4);
        d.setColor(Color.darkGray.darker());
        d.fillRectangle(w / 8, h - (h / 4), 15, h / 4);
        d.fillRectangle((w / 8) + 30, h - (h / 4), 10, h / 4);
        d.fillRectangle((w / 8) + 60, h - (h / 4), 10, h / 4);
        d.fillRectangle((w / 8) + 90, h - (h / 4), 10, h / 4);
        d.fillRectangle((w / 8) + 120, h - (h / 4), 15, h / 4);
        d.fillRectangle(w / 8, h - (h / 4), w / 6, 20);
        d.fillRectangle(w / 8, h - 15, w / 6, 15);
        d.fillRectangle(w / 8, h - 45, w / 6, 10);
        d.fillRectangle(w / 8, h - 75, w / 6, 10);
        d.fillRectangle(w / 8, h - 105, w / 6, 10);
        d.setColor(Color.yellow);
        d.fillCircle((w / 8) + 62, 275, 13);
        d.setColor(Color.orange);
        d.fillCircle((w / 8) + 62, 275, 8);
        d.setColor(Color.white);
        d.fillCircle((w / 8) + 62, 275, 4);
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
