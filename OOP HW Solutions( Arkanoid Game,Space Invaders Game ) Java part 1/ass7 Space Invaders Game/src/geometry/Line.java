package geometry;

import java.awt.Color;
import java.util.List;
import biuoop.DrawSurface;

/**
 * A Line class.
 *
 * 
 */
public class Line {
    private Point startP;
    private Point endP;
    private double slope;

    /**
     * Construct a line given two points.
     *
     * @param start - the line start point;
     * @param end - the line end point;
     */
    public Line(Point start, Point end) {
        this.startP = start;
        this.endP = end;
        this.calculateSlope();
    }

    /**
     * Construct a line given x and y values of two points.
     *
     * @param x1 the start point x coordinate;
     * @param y1 the start point y coordinate;
     * @param x2 the end point x coordinate;
     * @param y2 the end point y coordinate;
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * calculates and returns the length of the line.
     *
     * @return length - the length of this line.
     */
    public double length() {
        // dx - the distance between start and end points x coordinate.
        double dx = this.start().getX() - this.end().getX();
        // dy - the distance between start and end points y coordinate
        double dy = this.start().getY() - this.end().getY();
        double length = Math.sqrt((dx * dx) + (dy * dy));
        return length;
    }

    /** @return mid - the middle point of the line. */
    public Point middle() {
        double midx = (this.start().getX() + this.end().getX()) / 2;
        double midy = (this.start().getY() + this.end().getY()) / 2;
        Point mid = new Point(midx, midy);
        return mid;
    }

    /** @return startP - the start point of the line */
    public Point start() {
        return this.startP;
    }

    /** @return endP - the end point of the line */
    public Point end() {
        return this.endP;
    }

    /**
     * calculates this line slope based on his start and end points.
     */
    public void calculateSlope() {
        double denominator = this.startP.getX() - this.endP.getX();
        if (Double.compare(denominator, 0.0) == 0) {
            this.slope = Double.POSITIVE_INFINITY;
        } else {
            this.slope = (this.startP.getY() - this.endP.getY()) / denominator;
        }
    }

    /**
     * returns this line slope based on his start and end points.
     *
     * @return slope - this line slope.
     */
    public double slope() {
        return this.slope;
    }

    /**
     * Returns true if the lines intersect, false otherwise. intersection point is considered one only if the lines
     * cross each other,and intersection point is on both lines. if one of the lines start/end on the other line it
     * doesn't count as intersection point. if the lines have more then one intersection point they don't count as
     * intersecting.
     *
     * @param other - the line to compare to this line and find the intersection point.
     * @return true - if the lines compared intersect,false otherwise.
     */
    public boolean isIntersecting(Line other) {
        double thisDX = this.endP.getX() - this.startP.getX();
        double otherDX = other.endP.getX() - other.startP.getX();
        double thisDY = this.endP.getY() - this.startP.getY();
        double otherDY = other.endP.getY() - other.startP.getY();
        double d = (thisDX) * (otherDY) - (otherDX) * (thisDY);
        // lines are equal or one is part of the other will have infinite
        // intersection points and will return false.
        if ((d == 0) || (this.equals(other))) {
            return false;
        }
        // both lines don't have slope and wont intersect(parallel)
        if (((thisDX == 0) && (otherDX == 0)) || ((thisDY == 0) && (otherDY == 0))) {
            return false;
        }
        Point p = intersectionWith(other);
        // checks if point p(intersection point) is on both lines
        if ((this.pointInRange(p)) && (other.pointInRange(p))) {
            return true;
        }
        return false;
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise,intersection point is considered one
     * only if the lines cross each other,and intersection point is on both lines. if the lines have more then one
     * intersection point they don't count as intersecting.
     *
     * @param other - the line to compare to this line and find the intersection point.
     * @return intersectionPoint - the intersection Point if the lines compared intersect,null otherwise.
     */
    public Point intersectionWith(Line other) {
        // returns null if both lines are identical (infinite intersection points).
        if (this.equals(other)) {
            return null;
        }
        double x1 = this.startP.getX();
        double x2 = this.endP.getX();
        double x3 = other.startP.getX();
        double x4 = other.endP.getX();
        double y1 = this.startP.getY();
        double y2 = this.endP.getY();
        double y3 = other.startP.getY();
        double y4 = other.endP.getY();
        double thisDX = x2 - x1;
        double thisDY = y1 - y2;
        double otherDX = x4 - x3;
        double otherDY = y3 - y4;
        double eq1 = (thisDY * x1) + (thisDX * y1);
        double eq2 = (otherDY * x3) + (otherDX * y3);
        double checkEquation = (thisDY * otherDX) - (thisDX * otherDY);
        double x = ((eq1 * otherDX) - (eq2 * thisDX)) / checkEquation;
        double y = ((eq2 * thisDY) - (eq1 * otherDY)) / checkEquation;
        if (checkEquation == 0) {
            return null;
        } else {
            if (y == -0) {
                y = 0;
            }
            if (x == -0) {
                x = 0;
            }
            Point intersectionPoint = new Point(x, y);
            return intersectionPoint;
        }
    }

    /**
     * checks and return (true/false) if the given point is within range of this line(start,end points) ,meaning the
     * point is between or equal to this line start and end points.
     *
     * @param p - the compared point to this line start,end points.
     * @return true if the lines are equal (based on x,y values of the start and end points ),false otherwise.
     */
    public boolean pointInRange(Point p) {
        double minX = Math.min(this.startP.getX(), this.endP.getX());
        double maxX = Math.max(this.startP.getX(), this.endP.getX());
        double minY = Math.min(this.startP.getY(), this.endP.getY());
        double maxY = Math.max(this.startP.getY(), this.endP.getY());
        int cmpMinX = Double.compare(minX, p.getX());
        int cmpMaxX = Double.compare(maxX, p.getX());
        int cmpMinY = Double.compare(minY, p.getY());
        int cmpMaxY = Double.compare(maxY, p.getY());
        // cmp return value is: 0 if equal || -0 if a<b || +0 if a>b || for (a,b)
        if ((cmpMinX <= 0) && (cmpMaxX >= 0) && (cmpMinY <= 0) && (cmpMaxY >= 0)) {
            return true;
        }
        return false;
    }

    /**
     * checks and return (true/false) if the other line start/end points are within range of this line(start,end points)
     * ,meaning the other line is part of this line and they have infinite intersection points.
     *
     * @param other - the other line to compare to this line.
     * @return true if the compared lines (this,other) are part of each other,false otherwise.
     */
    public boolean linesInRange(Line other) {
        if ((pointInRange(other.endP)) || ((pointInRange(other.startP)))) {
            return true;
        }
        return false;
    }

    /**
     * checks and return (true/false) if compared lines have the same start and end points combinations.
     *
     * @param other - the other line to compare to this line.
     * @return true if the lines are equal (based on x,y values of the start and end points ),false otherwise.
     */
    public boolean equals(Line other) {
        if ((this.endP.equals(other.endP)) && (this.startP.equals(other.startP))) {
            return true;
        }
        if ((this.endP.equals(other.startP)) && (this.startP.equals(other.endP))) {
            return true;
        }
        return false;
    }

    /**
     * extract from the given rectangle (rect) a list of intersection points with this line and the given rectangle
     * (rect), If this line does not intersect with the rectangle returns null,Otherwise returns the closest
     * intersection point to the start point of this line.
     *
     * @param rect - the rectangle we want to check intersection for with this line.
     * @return the closest intersection point to the start point of this line with the rectangle (rect) or null if none
     *         found.
     */

    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interPointList = rect.intersectionPoints(this);
        Point minDistanceP = closestPointToStartOfLine(interPointList);
        return minDistanceP;
    }

    /**
     * return from the given list of points the closest point to this line start point. , If the list is empty returns
     * null.
     *
     * @param pointList - the list from which we want to get the closest point to this line start point.
     * @return the closest point from the given list (pointList) to the start point of this line or null if none found.
     */
    public Point closestPointToStartOfLine(List<Point> pointList) {
        int size = pointList.size();
        Point minDistanceP = null;
        int i = 0;
        if (size == 1) {
            minDistanceP = pointList.get(i);
        }
        if (size > 1) {
            for (i = 0; i < size - 1; i++) {
                double d1 = distanceToStartPoint(pointList.get(i));
                double d2 = distanceToStartPoint(pointList.get(i + 1));
                int retVal = Double.compare(d1, d2);
                if (retVal < 0) {
                    minDistanceP = pointList.get(i);
                } else {
                    minDistanceP = pointList.get(i + 1);
                }
            }
        }
        return minDistanceP;
    }

    /**
     * calculates and returns the distance between this line starting point and given point (p).
     *
     * @param p - the given point to calculate the distance to this line starting point.
     * @return distance - the calculated distance between given point (p) and this line starting point.
     */
    public double distanceToStartPoint(Point p) {
        // dx - the distance between this line start point and given point (p) x coordinates.
        double dx = this.start().getX() - p.getX();
        // dy - the distance between this line start point and given point (p) y coordinates.
        double dy = this.start().getY() - p.getY();
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        return distance;
    }

    /**
     * draws on the given board (d) this line and its start,middle,end points as a circle with radius 2 and color: start
     * = red,middle = yellow,end = green.
     *
     * @param d - the given board to draw the line and points on.
     */
    public void drawLine(DrawSurface d) {
        d.setColor(Color.black);
        d.drawLine((int) this.start().getX(), (int) this.start().getY(), (int) this.end().getX(),
                (int) this.end().getY());
        d.setColor(Color.red);
        d.fillCircle((int) this.start().getX(), (int) this.start().getY(), 2);
        d.setColor(Color.yellow);
        d.fillCircle((int) this.middle().getX(), (int) this.middle().getY(), 2);
        d.setColor(Color.green);
        d.fillCircle((int) this.end().getX(), (int) this.end().getY(), 2);
    }

    /**
     * draws on the given board (d) each point in the given list as a orange circle with radius 3.
     *
     * @param list - an list that holds points.
     * @param d - the given board to draw the points on.
     */
    public void drawIntersectionPoints(List<Point> list, DrawSurface d) {
        d.setColor(Color.orange);
        for (int i = 0; i < list.size(); i++) {
            Point interPoint = list.get(i);
            d.fillCircle((int) interPoint.getX(), (int) interPoint.getY(), 3);
        }
    }

    /**
     * print this line start and end points at the format "Line: start Point: (x,y) end Point: (x,y)".
     */
    public void printLine() {
        System.out.print("Line: start ");
        this.start().printPoint();
        System.out.print(" end ");
        this.end().printPoint();
        System.out.println("");
    }
}
