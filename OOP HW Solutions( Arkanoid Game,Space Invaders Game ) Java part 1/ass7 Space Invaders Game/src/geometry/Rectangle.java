package geometry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * a rectangle class.
 *
 * 
 */
public class Rectangle {
    private double rectangleWidth;
    private double rectangleHeight;
    private Point rectangleStartPoint;
    private Line leftLine;
    private Line topLine;
    private Line rightLine;
    private Line underLine;
    private Color rectColor = Color.darkGray;; // default color.

    /**
     * create a new rectangle with location point and width/height.
     *
     * @param upperLeft - this rectangle starting point,the upper left point.
     * @param width - this rectangle width.
     * @param height - this rectangle height.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.rectangleHeight = height;
        this.rectangleWidth = width;
        this.rectangleStartPoint = upperLeft;
        this.setRectangleLines();
    }

    /**
     * create a new rectangle with location start point(upperLeft) and end point(underRight).
     *
     * @param upperLeft - this rectangle starting point,the upper left point.
     * @param underRight - this rectangle end point,the under right point.
     */
    public Rectangle(Point upperLeft, Point underRight) {
        this(upperLeft, underRight.getX() - upperLeft.getX(), underRight.getY() - upperLeft.getY());
    }

    /**
     * create a new rectangle with given rectangle parameters x1,y1 (upperLeft point) and x2,y2 (underRight point).
     * example - rectangle(50,50,200,200) will be a rectangle that start at upperLeft(50,50) and ends at
     * underRight(250,250).
     *
     * @param x1 - this rectangle starting point,the upper left point x parameter.
     * @param y1 - this rectangle starting point,the upper left point y parameter.
     * @param x2 - this rectangle end point,the under right point x parameter.
     * @param y2 - this rectangle end point,the under right point y parameter.
     */
    public Rectangle(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2 + x1, y2 + y1));
    }

    /**
     * changes the rectangle starting position.
     *
     * @param topLeft - a new top Left Point.
     */
    public void changePosition(Point topLeft) {
        this.rectangleStartPoint = topLeft;
    }

    /**
     * sets this rectangle left/right/top/under lines based on its given starting point ,width and height.
     */
    public void setRectangleLines() {
        double x = this.rectangleStartPoint.getX() + this.rectangleWidth;
        double y = this.rectangleStartPoint.getY() + this.rectangleHeight;
        Point topRightP = new Point(x, this.rectangleStartPoint.getY());
        Point underLineRightP = new Point(x, y);
        Point underLineLeftP = new Point(this.rectangleStartPoint.getX(), y);
        this.leftLine = new Line(this.rectangleStartPoint, underLineLeftP);
        this.rightLine = new Line(topRightP, underLineRightP);
        this.topLine = new Line(this.rectangleStartPoint, topRightP);
        this.underLine = new Line(underLineLeftP, underLineRightP);
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line,in this list there wont be
     * duplicate points.
     *
     * @param line - the given line to check intersection with this rectangle.
     * @return interPointList - a list of unique intersection points between this rectangle and given line (list can be
     *         empty if no intersection points found).
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> interPointList = new ArrayList<Point>();
        if (line.isIntersecting(leftLine)) {
            interPointList.add(line.intersectionWith(leftLine));
        }
        if (line.isIntersecting(topLine)) {
            interPointList.add(line.intersectionWith(topLine));
        }
        if (line.isIntersecting(rightLine)) {
            interPointList.add(line.intersectionWith(rightLine));
        }
        if (line.isIntersecting(underLine)) {
            interPointList.add(line.intersectionWith(underLine));
        }
        if (!interPointList.isEmpty()) {
            removeListDuplicates(interPointList);
        }
        return interPointList;
    }

    /**
     * removes all duplicates points in given point list (list).
     *
     * @param list - the given point list.
     */
    public void removeListDuplicates(List<Point> list) {
        int j;
        for (int i = 0; i < list.size() - 1; i++) {
            for (j = 0; j < list.size() - 1; j++) {
                if (list.get(j).equals(list.get(i + 1))) {
                    list.remove(j);
                }
            }
        }
    }

    /**
     * print given Point list at format "point list point number :i x: y: ".
     *
     * @param list - the list of points to print.
     */
    public void printPointList(List<Point> list) {
        System.out.println("new point list print");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(
                    "point list point number : " + i + " x: " + list.get(i).getX() + " y: " + list.get(i).getY());
        }
    }

    /**
     * checks if given point (p) is within this rectangle borders(lines),returns true if it is and false otherwise.
     *
     * @param p - the given point to check if within the rectangle borders.
     * @return true if the point is within the rectangle borders and false otherwise.
     */
    public boolean checkPointInRectangle(Point p) {
        double x1 = this.rectangleStartPoint.getX();
        double y1 = this.rectangleStartPoint.getY();
        double x2 = this.rectangleStartPoint.getX() + this.rectangleWidth;
        double y2 = this.rectangleStartPoint.getY() + this.rectangleHeight;
        int x1CmpPx = Double.compare(x1, p.getX()); // x1 needs to be smaller/equal then p.x
        int x2CmpPx = Double.compare(x2, p.getX()); // x2 needs to be bigger/equal then p.x
        int y1CmpPy = Double.compare(y1, p.getY()); // y1 needs to be smaller/equal then p.y
        int y2CmpPy = Double.compare(y2, p.getY()); // y2 needs to be bigger/equal then p.y
        if ((x1CmpPx <= 0) && (x2CmpPx >= 0) && (y1CmpPy <= 0) && (y2CmpPy >= 0)) {
            return true;
        }
        return false;
    }

    /**
     * checks if given point (p) is within this rectangle borders(lines),return the line that the point is on otherwise
     * null.
     *
     * @param p - the given point to check if within the rectangle borders.
     * @return - the line which the point p is on ,null if its on none of them.
     */
    public Line findCollisonLine(Point p) {
        if (this.underLine.pointInRange(p)) {
            return this.underLine;
        }
        if (this.topLine.pointInRange(p)) {
            return this.topLine;
        }
        if (this.rightLine.pointInRange(p)) {
            return this.rightLine;
        }
        if (this.leftLine.pointInRange(p)) {
            return this.leftLine;
        }
        return null;
    }

    /**
     * @return this rectangle leftLine.
     */
    public Line getLeftLine() {
        return this.leftLine;
    }

    /**
     * @return this rectangle rightLine.
     */
    public Line getRightLine() {
        return this.rightLine;
    }

    /**
     * @return this rectangle topLine.
     */
    public Line getTopLine() {
        return this.topLine;
    }

    /**
     * @return this rectangle underLine.
     */
    public Line getUnderLine() {
        return this.underLine;
    }

    /**
     * @return this rectangle width.
     */
    public double getWidth() {
        return this.rectangleWidth;
    }

    /**
     * @return this rectangle height.
     */
    public double getHeight() {
        return this.rectangleHeight;
    }

    /**
     * @return this rectangle starting point(upper-left point) location.
     */
    public Point getUpperLeft() {
        return this.rectangleStartPoint;
    }

    /**
     * @return this rectangle center point - the middle of it.
     */
    public Point getRectangleCenterPoint() {
        double x = this.getUpperLeft().getX() + (this.rectangleWidth / 2);
        double y = this.getUpperLeft().getY() + (this.rectangleHeight / 2);
        return new Point(x, y);
    }

    /**
     * change this rectangle color to the given color.
     *
     * @param col - the new color for the rectangle.
     */
    public void setColor(Color col) {
        this.rectColor = col;
    }

    /** change this rectangle color to a random color. */
    public void setRandomColor() {
        this.rectColor = new Color((int) (Math.random() * 0x1000000));
    }

    /** @return this rectangle Color. */
    public Color getColor() {
        return this.rectColor;
    }
}
