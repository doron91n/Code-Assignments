package sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import animation.GameLevel;
import biuoop.DrawSurface;
import collision.Collidable;
import collision.HitListener;
import collision.HitNotifier;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * a Block class.
 *
 */
public class Block implements Sprite, Collidable, HitNotifier {
    private Rectangle blockShape;
    private int blockHP = 1; // default value unless set otherwise.
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * sets this block shape to given rectangle (rect).
     *
     * @param rect - the given rectangle to be this block shape.
     */
    public Block(Rectangle rect) {
        this.blockShape = rect;
    }

    /**
     * sets this block shape by given rectangle parameters starting point (upperLeft) and its width,height.
     *
     * @param upperLeft - this block shape (rectangle) starting point,the upper left point.
     * @param width - this block shape (rectangle) width.
     * @param height - this block shape (rectangle) height.
     */
    public Block(Point upperLeft, double width, double height) {
        this.blockShape = new Rectangle(upperLeft, width, height);
    }

    /**
     * sets this block shape by given rectangle parameters starting point (upperLeft) and end point (underRight).
     *
     * @param upperLeft - this block shape (rectangle) starting point,the upper left point.
     * @param underRight - this block shape (rectangle) end point,the under right point.
     */
    public Block(Point upperLeft, Point underRight) {
        this.blockShape = new Rectangle(upperLeft, underRight);
    }

    /**
     * sets this block shape by given rectangle parameters x1,y1 (upperLeft point) and x2,y2 (underRight point). example
     * - Block(50,50,200,200) will be a block that start at upperLeft(50,50) and ends at underRight(250,250).
     *
     * @param x1 - this block shape (rectangle) starting point,the upper left point x parameter.
     * @param y1 - this block shape (rectangle) starting point,the upper left point y parameter.
     * @param x2 - this block shape (rectangle) end point,the under right point x parameter.
     * @param y2 - this block shape (rectangle) end point,the under right point y parameter.
     */
    public Block(double x1, double y1, double x2, double y2) {
        this.blockShape = new Rectangle(x1, y1, x2, y2);
    }

    /**
     * change this block shape (rectangle) color to the given color.
     *
     * @param col - the new color for the block shape (rectangle).
     */
    public void setColor(Color col) {
        this.blockShape.setColor(col);
    }

    /** change this block shape (rectangle) color to a random color. */
    public void setRandomColor() {
        this.blockShape.setRandomColor();
    }

    /** @return this block shape (rectangle) color. */
    public Color getColor() {
        return this.blockShape.getColor();
    }

    /**
     * sets this block hit points number.
     *
     * @param hp - the remaining hit points set for this block.
     */
    public void setBlockHP(int hp) {
        this.blockHP = hp;
    }

    /**
     * @return the block Hit Points number.
     */
    public int getHitPoints() {
        return this.blockHP;
    }

    /************************************** Collidable interface methods **********************************************/

    /**
     * @return the "collision shape" of the object block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.blockShape;
    }

    /**
     * Notify the object (block) that we collided with it at collisionPoint with the given velocity(currentVelocity) and
     * if needed lower this Block HP (only if HP>0).
     *
     * @param collisionPoint - the collision point at which an object (ball) collided with this collidable object(block)
     * @param currentVelocity - the velocity at which an object (ball) collided with this collidable object (block).
     * @param hitter - the Ball that's doing the hitting on this Collidable object.
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dX = currentVelocity.getDx();
        double dY = currentVelocity.getDy();
        if ((this.blockShape.getLeftLine().pointInRange(collisionPoint))
                || (this.blockShape.getRightLine().pointInRange(collisionPoint))) {
            dX = -dX;
        }
        if ((this.blockShape.getTopLine().pointInRange(collisionPoint))
                || (this.blockShape.getUnderLine().pointInRange(collisionPoint))) {
            dY = -dY;
        }
        if (this.blockHP > 0) {
            this.blockHP--;
        }
        this.notifyHit(hitter);
        return new Velocity(dX, dY);
    }

    /************************************** Sprite interface methods *************************************************/

    /**
     * draw this block (rectangle) on the given DrawSurface.
     *
     * @param surface - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        int startX = (int) this.blockShape.getUpperLeft().getX();
        int startY = (int) this.blockShape.getUpperLeft().getY();
        int endX = (int) this.blockShape.getWidth();
        int endY = (int) this.blockShape.getHeight();
        surface.fillRectangle(startX, startY, endX, endY);
        surface.setColor(Color.BLACK);
        surface.drawRectangle(startX, startY, endX, endY);
    }

    /**
     * notify the sprite(block) that time has passed and invoke a certain change.
     */
    @Override
    public void timePassed() {
        // a sprite interface method -- in this assignment its empty,left for future use.
    }

    /**
     * adds this block to the given game (g).
     *
     * @param g - the game to add this block to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * remove this block from the given Game (g),removes from the game collidable List and sprite list.
     *
     * @param g - the game to be removed from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    /**
     * add the given HitListener (hl) to this Block hitListeners list.
     *
     * @param hl - the hit Listener to be added to this Block hitListeners list.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove the given HitListener (hl) from this Block hitListeners list.
     *
     * @param hl - the hit Listener to be removed from this Block hitListeners list.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notify all of the registered HitListener objects by calling their hitEvent method that a hit occured.
     *
     * @param hitter - the Ball that's doing the hitting on this Block object.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
