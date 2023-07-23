package background;

import java.awt.Color;
import java.awt.Polygon;

import animation.GameLevel;
import biuoop.DrawSurface;
import sprite.Sprite;

/**
 * Floyd class-end screen background.
 *
 */
public class Floyd implements Sprite {

    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int h = d.getHeight();
        int w = d.getWidth();
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, w, h);
        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();
        Polygon p3 = new Polygon();
        Polygon p4 = new Polygon();
        Polygon p5 = new Polygon();
        p1.addPoint(w / 2, 50);
        p1.addPoint(w - (w / 4), 100 + h / 2);
        p1.addPoint(w / 4, 100 + h / 2);
        p3.addPoint(0, h - h / 3);
        p3.addPoint(100 + w / 4, 50 + h / 3);
        p3.addPoint(90 + w / 4, 48 + h / 3);
        p4.addPoint(90 + w / 4, 48 + h / 3);
        p4.addPoint(w - (w / 4) - 125, 10 + h / 3);
        p4.addPoint(w - (w / 4) - 90, 70 + h / 3);
        p2.addPoint(w / 2, 80);
        p2.addPoint(w - (w / 4) - 30, 82 + h / 2);
        p2.addPoint(30 + w / 4, 82 + h / 2);
        int y3 = (h / 3) + 15;
        int y4 = (h / 3) + 30;
        int y5 = (h / 3) + 5;
        int y6 = (h / 3) + 15;
        for (int i = 0; i < 6; i++) {
            p5 = new Polygon();
            p5.addPoint(w - (w / 4) - 110, y5);
            p5.addPoint(w, y3);
            p5.addPoint(w, y4);
            p5.addPoint(w - (w / 4) - 110, y6);
            if (i == 0) {
                d.setColor(Color.red);
            }
            if (i == 1) {
                d.setColor(Color.orange);
            }
            if (i == 2) {
                d.setColor(Color.yellow);
            }
            if (i == 3) {
                d.setColor(Color.green);
            }
            if (i == 4) {
                d.setColor(Color.CYAN.darker());
            }
            if (i == 5) {
                d.setColor(Color.MAGENTA.darker().darker());
            }
            d.fillPolygon(p5);
            y3 = y4;
            y4 += 20;
            y5 = y6;
            y6 += 10;
        }
        d.setColor(Color.WHITE);
        d.fillPolygon(p1);
        d.fillPolygon(p3);
        d.setColor(Color.BLACK);
        d.fillPolygon(p2);
        d.setColor(Color.WHITE);
        d.fillPolygon(p4);
        d.setColor(Color.GRAY.darker());
        int y1 = 24 + h / 3;
        int y2 = (h / 3) + 12;
        for (int i = 0; i < 19; i++) {
            d.drawLine(w - (w / 4) - 125, y2, w / 2, y1);
            y1 += 2;
            y2 += 1;
        }
        y1 = (h / 3) + 30;
        y2 = (h / 3) + 40;
        d.setColor(Color.GRAY.darker());
        for (int i = 0; i < 19; i++) {
            d.drawLine((w / 2), y2, (w / 2) + 90, y1);
            y1 += 2;
            y2 += 1;
        }
        d.setColor(Color.WHITE);
        d.drawLine((w / 2) + 7, 85, w - (w / 4) - 25, 85 + h / 2);
        d.setColor(Color.BLACK);
        int x1 = (w / 2);
        int x2 = w - (w / 4) - 31;
        for (int i = 0; i < 5; i++) {
            d.drawLine(x1, 95, x2, 80 + h / 2);
            x1 -= 2;
            x2 -= 2;
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
