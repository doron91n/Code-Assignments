
/**
 * A Point class.
 *
 * 
 */
public class Point {
    private double pX;
    private double pY;

    /**
     * Construct a point given x and y coordinates.
     *
     * @param x the point x coordinate.
     * @param y the point y coordinate.
     */
    public Point(double x, double y) {
        this.pX = x;
        this.pY = y;
    }

    /** @return the X coordinate of this point. */
    public double getX() {
        return this.pX;
    }

    /** @return the Y coordinate of this point. */
    public double getY() {
        return this.pY;
    }

    /**
     * set a new X coordinate for this point.
     *
     * @param x - the new X coordinate for this point.
     */
    public void setX(double x) {
        this.pX = x;
    }

    /**
     * set a new Y coordinate for this point.
     *
     * @param y - the new Y coordinate for this point.
     */
    public void setY(double y) {
        this.pY = y;
    }

    /**
     * calculates and returns the distance between this point to the other point.
     *
     * @param other - a point to measure the distance to.
     * @return the distance to the other point.
     */
    public double distance(Point other) {
        double dx = this.pX - other.getX();
        double dy = this.pY - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * @return true if points are equal,false otherwise.
     * @param other - a point to compare the equality with.
     */
    public boolean equals(Point other) {
        if ((this.pX == other.getX()) && (this.pY == other.getY())) {
            return true;
        }
        return false;
    }

}
