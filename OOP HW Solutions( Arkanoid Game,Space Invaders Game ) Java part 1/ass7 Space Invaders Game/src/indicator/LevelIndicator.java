package indicator;

import java.awt.Color;

import animation.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import sprite.Sprite;

/**
 * a LevelIndicator class.
 *
 * 
 */
public class LevelIndicator implements Sprite {
    private Color color = Color.darkGray;
    private Rectangle shape;
    private String levelName;

    /**
     * indicate which level the player is playing.
     *
     * @param levelNamex - the level name.
     * @param rect - a rectangle that will display the level name.
     */
    public LevelIndicator(String levelNamex, Rectangle rect) {
        this.shape = rect;
        this.levelName = levelNamex;
    }

    /**
     * change this Level Indicator shape (rectangle) color to the given color.
     *
     * @param col - the new color for the Level Indicator shape (rectangle).
     */
    public void setColor(Color col) {
        this.color = col;
    }

    /** change this Level Indicator shape (rectangle) color to a random color. */
    public void setRandomColor() {
        this.shape.setRandomColor();
    }

    /** @return this Level Indicator shape (rectangle) color. */
    public Color getColor() {
        return this.color;
    }

    /************************************** Sprite interface methods *************************************************/

    /**
     * draw this Level Indicator (rectangle) on the given DrawSurface.
     *
     * @param surface - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        int startX = (int) this.shape.getUpperLeft().getX();
        int startY = (int) this.shape.getUpperLeft().getY();
        int endX = (int) this.shape.getWidth();
        int endY = (int) this.shape.getHeight();
        surface.fillRectangle(startX, startY, endX, endY);
        surface.setColor(Color.white);
        String text = "Level Name: " + this.levelName;
        Point rectCenter = this.shape.getRectangleCenterPoint();
        surface.drawText(startX + 5, (int) rectCenter.getY() + 10, text, 20);
    }

    /**
     * notify the sprite(Level Indicator) that time has passed and invoke a certain change.
     *
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
        // a sprite interface method -- in this assignment its empty,left for future use.
    }

    /**
     * adds this Level Indicator to the given game (g).
     *
     * @param g - the game to add this Level Indicator to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
