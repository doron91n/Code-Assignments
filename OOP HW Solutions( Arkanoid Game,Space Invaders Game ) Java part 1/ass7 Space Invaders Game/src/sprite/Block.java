package sprite;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 
 */
public class Block implements Sprite, Collidable, HitNotifier {
    private Rectangle blockShape;
    private int blockHP = 1; // default value unless set otherwise.
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private Map<Integer, Object> hpToColor = new HashMap<Integer, Object>();
    private Image backgroundImage;
    private Color strokeColor;
    private Boolean hasStroke = false;
    private Boolean hasBackground = false;
    private Boolean hasChangingBackground = false;
    private boolean countHit = true;
    private GameLevel game;
    private int destroyValue = 15;
    private int hitValue = 5;

    /**
     * sets this block shape to given rectangle (rect).
     *
     * @param rect - the given rectangle to be this block shape.
     */
    public Block(Rectangle rect) {
        this.blockShape = rect;
    }

    /**
     * construct this block by given block.
     *
     * @param block - the given block to copy values to this block.
     */
    public Block(Block block) {
        this.blockShape = block.getCollisionRectangle();
        this.blockHP = block.getHitPoints();
        if (block.hasBackgroundImage()) {
            this.setBackgroundImage(block.getBackgroundImage());
        }
        if (block.hasStroke()) {
            this.setStrokeColor(block.getStrokeColor());
        }
        if (block.hasChangingBackground()) {
            this.setChangingBackground(block.getChangingBackground());
        }
        this.countHit = block.getCountHit();
        this.setColor(block.getColor());
        this.hitListeners = block.gethitListeners();
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
     * @return this block top left Point X parameter.
     */
    public double getTopLeftX() {
        return getCollisionRectangle().getUpperLeft().getX();
    }

    /**
     * @return this block top left Point Y parameter.
     */
    public double getTopLeftY() {
        return getCollisionRectangle().getUpperLeft().getY();
    }

    /**
     * @return the game this block belong to.
     */
    public GameLevel getGame() {
        return this.game;
    }

    /**
     * @return the block bottom middle point.
     */
    public Point blockMidP() {
        return this.getCollisionRectangle().getUnderLine().middle();
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

    /**
     * sets this block to not be counted among destroyed blocks when hit.
     */
    public void setToNotCountHit() {
        this.countHit = false;
    }

    /**
     * @return true if this block will be counted among destroyed blocks when hit false otherwise.
     */
    public boolean getCountHit() {
        return this.countHit;
    }

    /**
     * @return true if the block has stroke(outline) false otherwise.
     */
    public boolean hasStroke() {
        return this.hasStroke;
    }

    /**
     * change this block shape (rectangle) outline color to the given color.
     *
     * @param col - the new color for the block shape (rectangle) outline.
     */
    public void setStrokeColor(Color col) {
        this.hasStroke = true;
        this.strokeColor = col;
    }

    /** @return this block shape (rectangle) Stroke color. */
    public Color getStrokeColor() {
        if (this.hasStroke()) {
            return this.strokeColor;
        }
        return null;
    }

    /**
     * @return true if the block has background(image) false otherwise.
     */
    public boolean hasBackgroundImage() {
        return this.hasBackground;
    }

    /**
     * change this block shape (rectangle) background Image to the given Image.
     *
     * @param image - the new image for the block shape (rectangle) background.
     */
    public void setBackgroundImage(Image image) {
        this.hasBackground = true;
        this.backgroundImage = image;
    }

    /**
     * return this block background Image.
     *
     * @return image - the image for the block shape (rectangle) background.
     */
    public Image getBackgroundImage() {
        if (this.hasBackground) {
            return this.backgroundImage;
        }
        return null;
    }

    /**
     * @return true if the block has color/image Changing Background false otherwise.
     */
    public boolean hasChangingBackground() {
        return this.hasChangingBackground;
    }

    /**
     * set this block to have Changing Image/color Background based on his Hp.
     *
     * @param hpToColor2 - a Map(Integer,Object) where the keys represent a block hp and the values a color/image.
     */
    public void setChangingBackground(Map<Integer, Object> hpToColor2) {
        this.hasChangingBackground = true;
        this.hpToColor = hpToColor2;
    }

    /**
     * return this block hpToColor Map(Integer,String).
     *
     * @return a Map(Integer,String) where the keys represent a block hp and the values a color/image.
     */
    public Map<Integer, Object> getChangingBackground() {
        if (this.hasChangingBackground) {
            return this.hpToColor;
        }
        return null;
    }

    /**
     * @return the score value when this block is destroyed.
     */
    public int getDestroyValue() {
        return this.destroyValue;
    }

    /**
     * @return the score value when this block is hit.
     */
    public int getHitValue() {
        return this.hitValue;
    }

    /**
     * changes the Block(rectangle) starting position.
     *
     * @param topLeft - a new top Left Point.
     * @return copy - a copy of this block with different top-left point.
     */
    public Block changePosition(Point topLeft) {
        Block copy = new Block(topLeft, this.getCollisionRectangle().getWidth(),
                this.getCollisionRectangle().getHeight());
        copy.setBlockHP(this.getHitPoints());
        if (this.hasBackgroundImage()) {
            copy.setBackgroundImage(this.backgroundImage);
        }
        if (this.hasStroke()) {
            copy.setStrokeColor(this.strokeColor);
        }
        if (this.hasChangingBackground()) {
            copy.setChangingBackground(this.getChangingBackground());
        }
        copy.setColor(this.getColor());
        return copy;
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
     * changes this block shape to given rectangle.
     *
     * @param rect - the new block shape.
     */
    public void setCollisionRectangle(Rectangle rect) {
        this.blockShape = rect;
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
        if (this.hasBackgroundImage()) {
            surface.drawImage(startX, startY, this.backgroundImage);
        }
        if (this.hasChangingBackground()) {
            int hp = getHitPoints();
            for (Map.Entry<Integer, Object> entry : this.hpToColor.entrySet()) {
                if (entry.getKey() == hp) {
                    Object background = entry.getValue();
                    try {
                        surface.setColor((Color) background);
                        surface.fillRectangle(startX, startY, endX, endY);
                    } catch (Exception e) {
                        ;
                    }
                    try {
                        surface.drawImage(startX, startY, (Image) background);
                    } catch (Exception e) {
                        ;
                    }
                }
            }
        }
        if (this.hasStroke()) {
            surface.setColor(this.strokeColor);
            surface.drawRectangle(startX, startY, endX, endY);
        }
    }

    /**
     * notify the sprite(block) that time has passed and invoke a certain change.
     *
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
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
        this.game = g;
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
     * @return this block hitListeners list.
     */
    public List<HitListener> gethitListeners() {
        return this.hitListeners;
    }

    /**
     * notify all of the registered HitListener objects by calling their hitEvent method that a hit occured.
     *
     * @param hitter - the Ball that's doing the hitting on this Block object.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(gethitListeners());
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);

        }
    }

}
