package background;

import java.awt.Color;

import animation.GameLevel;
import biuoop.DrawSurface;
import sprite.Sprite;

/**
 * Direct Hit class-level 1 background.
 *
 */
public class DirectHit implements Sprite {

    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.blue);
        d.drawCircle(d.getWidth() / 2, 180, 150);
        d.drawCircle(d.getWidth() / 2, 180, 100);
        d.drawCircle(d.getWidth() / 2, 180, 50);
        d.drawLine(d.getWidth() / 2, 210, d.getWidth() / 2, 380);
        d.drawLine(d.getWidth() / 2, 0, d.getWidth() / 2, 150);
        d.drawLine((d.getWidth() / 2) - 200, 180, (d.getWidth() / 2) - 30, 180);
        d.drawLine((d.getWidth() / 2) + 30, 180, (d.getWidth() / 2) + 200, 180);
        d.drawRectangle((d.getWidth() / 2) - 20, 160, 40, 40);
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
