package fluffybun.game.Movement;

import android.graphics.Color;

import java.util.Random;

import fluffybun.game.Geometry.Line;
import fluffybun.game.Geometry.Point;
import fluffybun.game.Side;
import fluffybun.game.UtilityBox;


/**
 * A Velocity class.
 */
public class Velocity {
    // The velocity dx,dy parameters are initialized to 0 unless entered otherwise.
    private double m_dX = 0;
    private double m_dY = 0;
    private double m_angle = 0;
    private double m_speed = 0;
    private Line m_direction_line;
    private int m_direction_line_length = 100; // should be radius + 100

    /**
     * Velocity specifies the change in position on the `x` and the `y` axes.
     *
     * @param dx    - the velocity x parameter, the change to the x axis.
     * @param dy    - the velocity y parameter, the change to the y axis.
     * @param angle - the velocity angle (Degrees) parameter.
     * @param speed - the velocity speed parameter.
     */
    public Velocity(double dx, double dy, double angle, double speed) {
        this.setDx(dx);
        this.setDy(dy);
        this.setSpeed(speed);
        this.setAngle(angle);
    }
    public static Velocity fromDxDy(double dx, double dy) {
        double speed =Math.sqrt((dx * dx) + (dy * dy));
        double cos_rad = dx / speed;
        double sin_rad = -dy / speed;
        double cal_angle = Math.round(Math.toDegrees(Math.acos(cos_rad)));
        // keep the angles between 0-360
        do {
            if (cal_angle < 0) {
                cal_angle += 360;
            }
            if (cal_angle > 360) {
                cal_angle -= 360;
            }
            // for angles above 180 , angle = 360 - calculated angle
            if (sin_rad < 0) {
                cal_angle = 360 - cal_angle;
            }
        } while ((cal_angle > 360) && (cal_angle < 0));
        return new Velocity(dx, dy, cal_angle, speed);
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
        double dx = (Math.cos(rad) * speed);
        double dy = (-Math.sin(rad) * speed);
        return new Velocity(dx, dy, angle, speed);
    }

    /**
     * @return a random Velocity created with a random angle(0-360) and random speed (2-15).
     */
    public static Velocity createRandomVelocity() {
        double randomAngle = UtilityBox.randomNumber(0,360);
        double randomSpeed = UtilityBox.randomNumber(2,15);
        return fromAngleAndSpeed(randomAngle, randomSpeed);
    }

    public void createDirectionLine(Point start_point) {
        double dis = 0;
        double dx = this.getDx();
        double dy = this.getDy();
        Point direction_end_point = new Point(start_point.getX() + dx, start_point.getY() + dy);
        do {
            direction_end_point.movePointBy(dx, dy);
            dis = start_point.getDistanceFrom(direction_end_point);
            System.out.println("******************************* distance is :" + dis);
        } while (dis < this.m_direction_line_length);

        this.m_direction_line = new Line(new Point(start_point), direction_end_point, Side.NOT_SET);
        this.m_direction_line.setColor(Color.BLACK);
    }

    public void moveDirectionLine() {
        //  this.getDirectionLine().offsetNoStart(this.getDx(),this.getDy());
        this.getDirectionLine().moveShapeBy(this.getDx(), this.getDy());
    }

    /**
     * Take a point with position (x,y) and offset to a new position (x+dx, y+dy).
     *
     * @param p - the point with position (x,y) before the change.
     */

    public void applyToPoint(Point p) {
        p.movePointBy(this.getDx(), this.getDy());
    }

    /**
     * @return speed - return this velocity speed based on dx dy values.
     */
    public double getSpeed() {
        return this.m_speed;
    }

    /**
     * @param speed -  this velocity speed based on dx dy values.
     */
    public void setSpeed(double speed) {
        this.m_speed = speed;
    }

    /**
     * calculates and sets this velocity speed based on dx dy values.
     */
    public double calculateSpeed() {
        double dx = this.getDx();
        double dy = this.getDy();
        return (Math.sqrt((dx * dx) + (dy * dy)));
    }

    /**
     * @return m_angle - return this velocity speed based on dx dy values.
     */
    public double getAngle() {
        return this.m_angle;
    }

    /**
     * @param angle -  this velocity angle based on dx dy values.
     */
    public void setAngle(double angle) {
        this.m_angle = angle;
    }

    public double calculateAngle(double dx,double dy,double speed) {
        double cos_rad = dx / speed;
        double sin_rad = -dy / speed;
        double cal_angle = Math.round(Math.toDegrees(Math.acos(cos_rad)));
        // keep the angles between 0-360
        do {
            if (cal_angle < 0) {
                cal_angle += 360;
            }
            if (cal_angle > 360) {
                cal_angle -= 360;
            }
            // for angles above 180 , angle = 360 - calculated angle
            if (sin_rad < 0) {
                cal_angle = 360 - cal_angle;
            }
        } while ((cal_angle > 360) && (cal_angle < 0));
        //   System.out.println("@@@@@@@@@@@@@@@@@@@  from angle: "+m_angle +" to angle : "+cal_angle+" cos_rad: "+cos_rad+" sin_rad: "+sin_rad+" to Math.toDegrees : "+Math.toDegrees(Math.acos(cos_rad)) +" acos: "+Math.acos(cos_rad));
        return cal_angle;
    }

    /**
     * returns this velocity dX parameter.
     *
     * @return this.dX - this velocity dX parameter.
     */
    public double getDx() {
        return this.m_dX;
    }

    /**
     * sets a new dX value.
     *
     * @param dx - this velocity dx parameter.
     */
    public void setDx(double dx) {
        this.m_dX = dx;
    }

    /**
     * returns this velocity dY parameter.
     *
     * @return this.dY - this velocity dY parameter.
     */
    public double getDy() {
        return this.m_dY;
    }

    /**
     * sets a new dY value.
     *
     * @param dy - this velocity dY parameter.
     */
    public void setDy(double dy) {
        this.m_dY = dy;
    }

    public Line getDirectionLine() {
        return this.m_direction_line;
    }

    public void setDirectionLine(Line line) {
        this.m_direction_line = line;
    }

    public int getDirectionLineLength() {
        return this.m_direction_line_length;
    }

    public void setDirectionLineLength(int length) {
        this.m_direction_line_length = length;
    }

    public String toString(){
        return "Velocity: Dx:"+this.getDx()+" Dy:"+this.getDy()+" Speed:"+this.getSpeed()+" Angle:"+this.getAngle();
    }
}
