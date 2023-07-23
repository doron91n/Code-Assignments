
import java.util.ArrayList;
import java.util.List;

/**
 * a GameEnvironment class.
 *
 */
public class GameEnvironment {
    private List<Collidable> collidableList = new ArrayList<Collidable>();

    /**
     * add the given Collidable object (c) to the environment.
     *
     * @param c - the new Collidable object to be added to this Game Environment collidableList.
     */
    public void addCollidable(Collidable c) {
        this.collidableList.add(c);
    }

    /**
     * @return returns this game environment collidable List.
     */
    public List<Collidable> getCollidableList() {
        return this.collidableList;
    }

    /**
     * checks if the object with the given trajectory line collides with any of the collidables (from this Game
     * Environment collidableList) if so returns the information about the closest collision that is going to occur else
     * returns null. p.s - Assume an object moving from line.start() to line.end().
     *
     * @param trajectory - an object trajectory line (his anticipated route) to be checked if the object is gonna
     *            Collide with any of the collidables (from this Game Environment collidableList).
     * @return closestCollisionInfo - the information about the closest collision that is going to occur
     *         (collisionPoint,collisionObject),will be null if there is no collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollisionInfo = null;
        Point collisionP = null;
        List<Point> trajectoryCollidPointsList = new ArrayList<Point>();
        for (int i = 0; i < this.collidableList.size(); i++) {
            collisionP = trajectory
                    .closestIntersectionToStartOfLine(this.collidableList.get(i).getCollisionRectangle());
            if (collisionP != null) {
                trajectoryCollidPointsList.add(collisionP);
            }
            if (!trajectoryCollidPointsList.isEmpty()) {
                Point closestP = trajectory.closestPointToStartOfLine(trajectoryCollidPointsList);
                Collidable closestObject = findClosestCollidableObject(closestP);
                closestCollisionInfo = new CollisionInfo(closestP, closestObject);
            }
        }
        return closestCollisionInfo;
    }

    /**
     * returns the Collidable object (from Game Environment collidableList) that the given point(p) is within him.
     *
     * @param p - the point we want to check if the objects in this Game Environment collidableList,contains.
     * @return closestObject - the object that the point p is within him.
     */
    public Collidable findClosestCollidableObject(Point p) {
        Collidable closestObject = null;
        for (int i = 0; i < this.collidableList.size(); i++) {
            if (this.collidableList.get(i).getCollisionRectangle().checkPointInRectangle(p)) {
                closestObject = this.collidableList.get(i);
            }
        }
        return closestObject;
    }
}
