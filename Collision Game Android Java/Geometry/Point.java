package fluffybun.game.Geometry;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import fluffybun.game.UtilityBox;

public class Point {
    private double m_X;
    private double m_Y;
    private Paint m_paint = new Paint();

    /**
     * Construct a point given x and y coordinates.
     *
     * @param x the point x coordinate.
     * @param y the point y coordinate.
     */
    public Point(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Construct a point given a point.
     *
     * @param other_point the point to copy.
     */
    public Point(Point other_point) {
        this.setX(other_point.getX());
        this.setY(other_point.getY());
    }

    /**
     * moves the point location to new (x,y) coordinates .
     *
     * @param x - the new X coordinate for this point.
     * @param y - the new Y coordinate for this point.
     */
    public void movePointTo(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * set a new X,Y coordinate for this point based on given point.
     *
     * @param other - the new point coordinate for this point.
     */
    public void movePointTo(Point other) {
        this.setX(other.getX());
        this.setY(other.getY());
    }


    /**
     * moves the point location ( x by dx),(y by dy value) .
     *
     * @param dx - the value by which to move this point x.
     * @param dy - the value by which to move this point y.
     */
    public void movePointBy(double dx, double dy) {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }


    /**
     * @return the X coordinate of this point.
     */
    public double getX() {
        return this.m_X;
    }

    /**
     * set a new X coordinate for this point.
     *
     * @param x - the new X coordinate for this point.
     */
    private void setX(double x) {
        this.m_X = UtilityBox.roundUp(x);
    }

    /**
     * @return the Y coordinate of this point.
     */
    public double getY() {
        return this.m_Y;
    }

    /**
     * set a new Y coordinate for this point.
     *
     * @param y - the new Y coordinate for this point.
     */
    private void setY(double y) {
        this.m_Y = UtilityBox.roundUp(y);
    }


    /**
     * calculates and returns the distance between this point to the other point.
     *
     * @param other - a point to measure the distance to.
     * @return the distance to the other point.
     */
    public double getDistanceFrom(Point other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return UtilityBox.roundUp(Math.sqrt((dx * dx) + (dy * dy)));
    }

    /**
     * checks if this point and given other point are equal based on their x and y parameters.
     *
     * @param other - a point to compare the equality with.
     * @return true if points are equal,false otherwise.
     */
    public boolean equals(Point other) {
        int retValX = Double.compare(this.getX(), other.getX());
        int retValY = Double.compare(this.getY(), other.getY());
        if ((retValX == 0) && (retValY == 0)) {
            return true;
        }
        return false;
    }

    private Paint getPaint() {
        return this.m_paint;
    }

    /**
     * draws on the given board (d) this line.
     *
     * @param canvas - the given board to draw the line and points on.
     */
    public void draw(Canvas canvas) {
        this.getPaint().setColor(Color.BLUE);
        canvas.drawCircle((int) this.getX(), (int) this.getY(), 5, this.getPaint());
    }

    /**
     * prints this point in format "Point: (x,y)".
     */
    public void printPoint() {
        System.out.println(this.toString());
    }


    public String toString() {
        return "Point:(" + this.getX() + "," + this.getY() + ")";
    }
}
