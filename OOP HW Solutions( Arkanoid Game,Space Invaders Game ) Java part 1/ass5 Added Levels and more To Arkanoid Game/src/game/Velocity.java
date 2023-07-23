package game;

import java.util.Random;

import geometry.Point;

/**
 * A Velocity class.
 *
 */
public class Velocity {
    // The velocity dx,dy parameters are initialized to 0 unless entered otherwise.
    private double dX = 0;
    private double dY = 0;

    /**
     * Velocity specifies the change in position on the `x` and the `y` axes.
     *
     * @param dx - the velocity x parameter, the change to the x axis.
     * @param dy - the velocity y parameter, the change to the y axis.
     */
    public Velocity(double dx, double dy) {
        this.dX = Math.round(dx * 100000.0) / 100000.0;
        this.dY = Math.round(dy * 100000.0) / 100000.0;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p - the point with position (x,y) before the change.
     * @return newP - the new point with position (x+dx, y+dy) , x and y from point p.
     */

    public Point applyToPoint(Point p) {
        Point newP = new Point(p.getX() + this.dX, p.getY() + this.dY);
        return newP;
    }

    /**
     * sets a new dX value.
     *
     * @param dx - this velocity dx parameter.
     */
    public void setDx(double dx) {
        this.dX = Math.round(dx * 100000.0) / 100000.0;
    }

    /**
     * sets a new dY value.
     *
     * @param dy - this velocity dY parameter.
     */
    public void setDy(double dy) {
        this.dY = Math.round(dy * 100000.0) / 100000.0;
    }

    /**
     * returns this velocity dX parameter.
     *
     * @return this.dX - this velocity dX parameter.
     */
    public double getDx() {
        return this.dX;
    }

    /**
     * returns this velocity dY parameter.
     *
     * @return this.dY - this velocity dY parameter.
     */
    public double getDy() {
        return this.dY;
    }

    /**
     * specifies the velocity in terms of angle and speed,extracts dx,dy values from them.
     *
     * @param angle - the velocity angle (Degrees) parameter.
     * @param speed - the velocity speed parameter.
     * @return new Velocity - the new velocity from given speed and angle.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double rad = Math.toRadians(angle);
        double dx = Math.round((Math.sin(rad) * speed) * 100000.0) / 100000.0;
        double dy = Math.round((-Math.cos(rad) * speed) * 100000.0) / 100000.0;
        return new Velocity(dx, dy);
    }

    /**
     * @return speed - extract and return this velocity speed based on dx dy values.
     */
    public double getSpeed() {
        double speed = Math.round((Math.sqrt((getDx() * getDx()) + (getDy() * getDy()))) * 100000.0) / 100000.0;
        return speed;
    }

    /**
     * @return a random Velocity created with a random angle(0-360) and random speed (2-20).
     */
    public static Velocity generateRandomVelocity() {
        Random rand = new Random();
        double randomAngle = rand.nextInt((360 - 0) + 1) + 0;
        double randomSpeed = rand.nextInt((20 - 2) + 1) + 2;
        return fromAngleAndSpeed(randomAngle, randomSpeed);
    }
}
