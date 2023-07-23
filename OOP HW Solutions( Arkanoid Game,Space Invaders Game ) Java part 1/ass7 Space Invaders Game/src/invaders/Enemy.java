
package invaders;

import java.util.ArrayList;
import java.util.List;

import collision.HitListener;
import geometry.Point;
import geometry.Rectangle;
import sprite.Ball;
import sprite.Block;

/**
 * a Enemy class.
 *
 * 
 */
public class Enemy extends Block {
    private Shot shot;
    private double moveSpeed;
    private double initialMoveSpeed;
    private Point initialStartP;
    private int destroyValue = 100;
    private int hitValue = 0;
    private Formation formation;
    private ColumnFormation columnFormation;

    /**
     * construct a new enemy with given parameters.
     *
     * @param block - the block to make a new enemy off.
     * @param enemyShot - the shot for this enemy.
     * @param form - the formation this enemy belong to.
     * @param columnForm - the column formation this enemy belong to.
     * @param formationSpeed - the whole formation this enemy belong to initial movement speed.
     */
    public Enemy(Block block, Shot enemyShot, Formation form, ColumnFormation columnForm, double formationSpeed) {
        super(block);
        this.shot = enemyShot;
        this.initialMoveSpeed = formationSpeed;
        this.initialStartP = new Point(block.getTopLeftX(), block.getTopLeftY());
        this.moveSpeed = formationSpeed;
        this.formation = form;
        this.columnFormation = columnForm;
    }

    /**
     * copy the given enemy to this enemy.
     *
     * @param e - the enemy to copy.
     */
    public Enemy(Enemy e) {
        super(e);
        this.shot = e.shot;
        this.initialMoveSpeed = e.initialMoveSpeed;
        this.initialStartP = new Point(e.getTopLeftX(), e.getTopLeftX());
        this.moveSpeed = e.initialMoveSpeed;
        this.formation = e.formation;
    }

    /**
     * @return a new shot from the bottom middle part of this enemy.
     */
    public Shot shoot() {
        this.shot.setCenterP(new Point(enemyMidP().getX(), enemyMidP().getY() + 10));
        return new Shot(this.shot);
    }

    /**
     * @return the bottom middle point of this enemy.
     */
    public Point enemyMidP() {
        return this.getCollisionRectangle().getUnderLine().middle();
    }

    /**
     * Increase this enemy movement speed by 10%.
     */
    public void increaseSpeed() {
        this.moveSpeed *= 1.1;
    }

    /**
     * sets this enemy initial movement speed to given speed.
     *
     * @param s - the enemy initial movement speed to set.
     */
    public void setInitialSpeed(double s) {
        this.initialMoveSpeed = s;
    }

    /**
     * @return true if this enemy is the last in its column false otherwise.
     */
    public boolean isLastInCol() {
        double y = getTopLeftY();
        for (Enemy e : this.columnFormation.getEnemylist()) {
            if (e.getTopLeftY() > y) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return this enemy score value upon destruction.
     */
    @Override
    public int getDestroyValue() {
        return this.destroyValue;
    }

    /**
     * @return this enemy score value upon hit.
     */
    @Override
    public int getHitValue() {
        return this.hitValue;
    }

    /**
     * resets this enemy to its starting position.
     */
    public void resetEnemyPos() {
        double width = getCollisionRectangle().getWidth();
        double height = getCollisionRectangle().getHeight();
        this.moveSpeed = this.initialMoveSpeed;
        setCollisionRectangle(new Rectangle(this.initialStartP, width, height));
    }

    /**
     * move this enemy to a new position (x + (dx * this.moveSpeed), y + (dy * this.moveSpeed).
     *
     * @param dx - the x parameter to move by.
     * @param dy - the y parameter to move by.
     */
    public void move(double dx, double dy) {
        double x = getTopLeftX();
        double y = getTopLeftY();
        double width = getCollisionRectangle().getWidth();
        double height = getCollisionRectangle().getHeight();
        setCollisionRectangle(new Rectangle(x + (dx * this.moveSpeed), y + (dy * this.moveSpeed), width, height));
    }

    /**
     * notify all of the registered HitListener objects by calling their hitEvent method that a hit occured.
     *
     * @param hitter - the Ball that's doing the hitting on this Block object.
     */
    @Override
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(gethitListeners());
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            if (hitter.getVelocity().getAngle() <= 0) {
                hl.hitEvent(this, hitter);
                this.formation.removeFromFormation(this);
            }
        }
    }
}
