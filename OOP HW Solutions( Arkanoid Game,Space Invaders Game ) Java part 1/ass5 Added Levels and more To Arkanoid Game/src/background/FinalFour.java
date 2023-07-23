package background;

import java.awt.Color;

import animation.GameLevel;
import biuoop.DrawSurface;
import sprite.Sprite;

/**
 * Final Four class-level 4 background.
 *
 * 
 */
public class FinalFour implements Sprite {

    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int h = d.getHeight();
        int w = d.getWidth();
        d.setColor(Color.cyan.darker());
        d.fillRectangle(0, 0, w, h);
        // rain lines
        d.setColor(Color.gray.brighter());
        d.drawLine(w - (w / 8) - 20, (h / 2) + 50, w - (w / 8), h);
        d.drawLine(w - (w / 8) - 40, (h / 2) + 50, w - (w / 8) - 30, h);
        d.drawLine(w - (w / 8) - 60, (h / 2) + 50, w - (w / 8) - 50, h);
        d.drawLine(w - (w / 8) - 80, (h / 2) + 50, w - (w / 8) - 70, h);
        d.drawLine(w - (w / 8) - 100, (h / 2) + 50, w - (w / 8) - 90, h);
        d.drawLine(w - (w / 8) + 10, (h / 2) + 50, w - (w / 8) + 30, h);
        d.drawLine(w - (w / 8) + 20, (h / 2) + 50, w - (w / 8) + 50, h);
        d.drawLine(w - (w / 8) + 30, (h / 2) + 50, w - (w / 8) + 70, h);
        d.drawLine((w / 8) - 20, (h / 2) + 50, (w / 8), h);
        d.drawLine((w / 8) - 40, (h / 2) + 50, (w / 8) - 30, h);
        d.drawLine((w / 8) - 60, (h / 2) + 50, (w / 8) - 50, h);
        d.drawLine((w / 8) + 10, (h / 2) + 50, (w / 8) + 30, h);
        d.drawLine((w / 8) + 20, (h / 2) + 50, (w / 8) + 50, h);
        d.drawLine((w / 8) + 30, (h / 2) + 50, (w / 8) + 70, h);
        d.drawLine((w / 8) + 50, (h / 2) + 50, (w / 8) + 90, h);
        d.drawLine((w / 8) + 60, (h / 2) + 50, (w / 8) + 110, h);
        // two clouds
        d.setColor(Color.gray);
        d.fillCircle(w / 8, (h / 2) + 50, 60);
        d.fillCircle(w / 12, (h / 2) + 50, 50);
        d.fillCircle(w / 5, (h / 2) + 35, 60);
        d.fillCircle(w / 5 + 40, (h / 2) + 35, 40);
        d.fillCircle(w / 5, (h / 2) + 50, 35);
        d.fillCircle(w - (w / 8), (h / 2) + 50, 60);
        d.fillCircle(w - (w / 12), (h / 2) + 50, 50);
        d.fillCircle(w - (w / 5), (h / 2) + 35, 60);
        d.fillCircle(w - (w / 5) + 40, (h / 2) + 35, 40);
        d.fillCircle(w - (w / 5), (h / 2) + 50, 35);
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
