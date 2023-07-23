
package invaders;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import collision.Collidable;
import collision.CollisionInfo;
import game.Velocity;
import geometry.Line;
import geometry.Point;
import sprite.Ball;

/**
 * a Shot class.
 *
 */
public class Shot extends Ball {
    private boolean isEnemyShot = false;
    private List<Enemy> uncollideWith = new ArrayList<Enemy>();

    /**
     * creates a new Shot with given center point,radius,color.
     *
     * @param center - the Shot center point.
     * @param r - the Shot radius size.
     * @param color - the Shot color.
     * @param enemyShot - will be true if this shot belong to an enemy false if its for a player.
     */
    public Shot(Point center, int r, Color color, boolean enemyShot) {
        super(center, r, color);
        if (enemyShot) {
            this.isEnemyShot = true;
            setVelocity(0, 240);
        } else {
            setVelocity(0, -240);
        }
    }

    /**
     * recreates a new Shot with given shot.
     *
     * @param shot - the shot to recreate.
     */
    public Shot(Shot shot) {
        super(new Point(shot.getX(), shot.getY()), shot.getSize(), shot.getColor());
        if (shot.isEnemyShot) {
            this.isEnemyShot = true;
            setVelocity(0, 240);
        } else {
            setVelocity(0, -240);
        }
    }

    /**
     * @return true if this shot belongs to an enemy false otherwise.
     */
    public boolean isEnemyShot() {
        return this.isEnemyShot;
    }

    /**
     * add a collideable Enemy to this shot uncollideWith list, each enemy added upon impact will remove this shot
     * without harming the enemy itself.
     *
     * @param e - the enemy to not coliide with.
     */
    public void addUncollideAble(Enemy e) {
        this.uncollideWith.add(e);
    }

    /**
     * increase the shot movement speed by 10%.
     */
    public void increaseSpeed() {
        double speed = this.getVelocity().getSpeed() * 1.1;
        double angle = this.getVelocity().getAngle();
        setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
    }

    /**
     * moves the shot to a new point with position (dt(x+dx), dt(y+dy)),if the shot hits a collidable object,the shot
     * and the collidable are removed unless this is an enemy Shot and it collided with an enemy, removes only the shot.
     */
    @Override
    public void moveOneStep() {
        Line trajectory = getTrajectory();
        CollisionInfo collisionInformation = getBallGameEnvironment().getClosestCollision(trajectory);
        // there are no collisions on this trajectory - move the ball (its center) to new position (x+dx, y+dy).
        if (collisionInformation == null) {
            Point p = getVelocity().applyToPoint(getCenterP());
            setCenterP(new Point(p.getX(), p.getY()));
        }
        if (collisionInformation != null) {
            Point collisionPoint = collisionInformation.collisionPoint();
            Collidable collisionObject = collisionInformation.collisionObject();
            // if this is an enemy Shot and it collided with an enemy remove this shot.
            if ((this.uncollideWith.contains(collisionObject)) && isEnemyShot()) {
                removeFromGame(getGame());
            } else {
                setVelocity(collisionObject.hit(this, collisionPoint, getVelocity()));
            }
        }
    }

}
