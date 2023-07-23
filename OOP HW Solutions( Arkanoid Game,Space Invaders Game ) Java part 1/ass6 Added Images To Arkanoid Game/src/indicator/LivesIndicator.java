package indicator;

import java.awt.Color;

import animation.GameLevel;
import biuoop.DrawSurface;
import game.Counter;
import geometry.Point;
import geometry.Rectangle;
import sprite.Sprite;

/**
 * a LivesIndicator class.
 *
 */
public class LivesIndicator implements Sprite {
    private Color color = Color.darkGray;
    private Rectangle shape;
    private Counter livesCount;

    /**
     * indicate the player remaining lives count.
     *
     * @param lives - the player remaining lives count.
     * @param rect - a rectangle that will display the player lives count.
     */
    public LivesIndicator(Counter lives, Rectangle rect) {
        this.shape = rect;
        this.livesCount = lives;
    }

    /**
     * change this lives Indicator shape (rectangle) color to the given color.
     *
     * @param col - the new color for the lives Indicator shape (rectangle).
     */
    public void setColor(Color col) {
        this.color = col;
    }

    /** change this lives Indicator shape (rectangle) color to a random color. */
    public void setRandomColor() {
        this.shape.setRandomColor();
    }

    /** @return this lives Indicator shape (rectangle) color. */
    public Color getColor() {
        return this.color;
    }

    /************************************** Sprite interface methods *************************************************/

    /**
     * draw this lives Indicator (rectangle) on the given DrawSurface.
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
        surface.setColor(Color.WHITE);
        String text = "Lives: " + this.livesCount.getValue();
        Point rectCenter = this.shape.getRectangleCenterPoint();
        surface.drawText(startX + 5, (int) rectCenter.getY() + 10, text, 20);
    }

    /**
     * notify the sprite(lives Indicator) that time has passed and invoke a certain change.
     *
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
        // a sprite interface method -- in this assignment its empty,left for future use.
    }

    /**
     * adds this lives Indicator to the given game (g).
     *
     * @param g - the game to add this Score Indicator to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
