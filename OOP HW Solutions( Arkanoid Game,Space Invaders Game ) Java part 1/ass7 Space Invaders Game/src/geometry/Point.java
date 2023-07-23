package geometry;

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
        this.pX = Math.round(x * 100000.0) / 100000.0;
        this.pY = Math.round(y * 100000.0) / 100000.0;
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
        this.pX = Math.round(x * 100000.0) / 100000.0;
    }

    /**
     * set a new Y coordinate for this point.
     *
     * @param y - the new Y coordinate for this point.
     */
    public void setY(double y) {
        this.pY = Math.round(y * 100000.0) / 100000.0;
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
        return Math.round(Math.sqrt((dx * dx) + (dy * dy)) * 100000.0) / 100000.0;
    }

    /**
     * checks if this point and given other point are equal based on their x and y parameters.
     *
     * @param other - a point to compare the equality with.
     * @return true if points are equal,false otherwise.
     */
    public boolean equals(Point other) {
        int retValX = Double.compare(this.pX, other.getX());
        int retValY = Double.compare(this.pY, other.getY());
        if ((retValX == 0) && (retValY == 0)) {
            return true;
        }
        return false;
    }

    /**
     * prints this point in format "Point: (x,y)".
     */
    public void printPoint() {
        System.out.print("Point: (" + this.getX() + "," + this.getY() + ")");
    }
}
