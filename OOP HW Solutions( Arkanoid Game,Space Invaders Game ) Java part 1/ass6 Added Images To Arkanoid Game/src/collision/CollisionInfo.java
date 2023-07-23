package collision;

import geometry.Point;

/**
 * a CollisionInfo class.
 *
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * sets this CollisionInfo parameters (collisionPoint,collisionObject).
     *
     * @param collisionP - the point at which the collision occurs.
     * @param collisionO - the collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionP, Collidable collisionO) {
        this.collisionPoint = collisionP;
        this.collisionObject = collisionO;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
