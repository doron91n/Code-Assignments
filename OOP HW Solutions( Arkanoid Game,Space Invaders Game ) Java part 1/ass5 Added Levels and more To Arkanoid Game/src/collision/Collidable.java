package collision;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;
import sprite.Ball;

/**
 * a Collidable interface.
 *
 */
public interface Collidable {
    /**
     * @return - the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with the given velocity(currentVelocity).
     *
     * @param collisionPoint - the collision point at which an object collided with this collidable object.
     * @param currentVelocity - the velocity at which an object collided with this collidable object .
     * @param hitter - the Ball that's doing the hitting on this Collidable object.
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
