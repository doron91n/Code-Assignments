package fluffybun.game.Geometry;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;

import java.util.HashMap;
import java.util.List;

import fluffybun.game.Movement.Direction;
import fluffybun.game.Side;
import fluffybun.game.UtilityBox;


public class Line implements Shape {
    private Paint m_paint = new Paint();
    private int m_color = Color.RED;
    private Point m_start_point;
    private Point m_end_point;
    private Point m_center_point = new Point(0, 0);
    private Direction m_line_direction = Direction.NOT_SET;
    private Side m_shape_line_side = Side.NOT_SET;
    private HashMap<Side, Line> m_shape_lines;


    /**
     * Construct a line given two points.
     *
     * @param start     - the line start point;
     * @param end       - the line end point;
     * @param line_side - the line side on the shape orientation (left/right/top/bottom... side).
     */
    public Line(Point start, Point end, Side line_side) {
        this.setEndPoint(end);
        this.setStartPoint(start);
        this.setShapeLineSide(line_side);
        this.m_shape_lines = new HashMap<>();
        this.m_shape_lines.put(line_side, this);
        this.recalculateLine();
    }

    /**
     * Construct a line given x and y values of two points.
     *
     * @param x1        the start point x coordinate;
     * @param y1        the start point y coordinate;
     * @param x2        the end point x coordinate;
     * @param y2        the end point y coordinate;
     * @param line_side - the line side on the shape orientation (left/right/top/bottom... side).
     */
    public Line(double x1, double y1, double x2, double y2, Side line_side) {
        this(new Point(x1, y1), new Point(x2, y2), line_side);
    }


    /**
     * Switches the lines start and end points , resulting in switching line direction.
     */
    public void invertLine() {
        Point temp_start_p = this.getStartPoint();
        this.setStartPoint(this.getEndPoint());
        this.setEndPoint(temp_start_p);
        this.recalculateLine();
    }

    /**
     * sets the line to a new position.
     *
     * @param start_x - the line new start point x coordinates.
     * @param start_y - the line new start point y coordinates.
     * @param end_x   - the line new end point x coordinates.
     * @param end_y   - the line new end point y coordinates.
     */
    public void moveLineTo(double start_x, double start_y, double end_x, double end_y) {
        this.moveEndPointTo(end_x, end_y);
        this.moveStartPointTo(start_x, start_y);
    }

    /**
     * Moves the line location (start+end y by dy value), (start+end x by dx) the line length stay unchanged.
     *
     * @param dx - the value by which to move this line start+end x.
     * @param dy - the value by which to move this line start+end y.
     */
    public void moveShapeBy(double dx, double dy) {
        this.getStartPoint().movePointBy(dx, dy);
        this.getEndPoint().movePointBy(dx, dy);
        // the center point will be moved in recalculation Line
        this.recalculateLine();
    }

    public void moveShapeByCenterPoint(Point new_center_point) {
        Point current_center_p = this.getCenterPoint();
        double dx = current_center_p.getX() - new_center_point.getX();
        double dy = current_center_p.getY() - new_center_point.getY(); //******************************************* to check if works
        this.moveShapeBy(dx, dy);

    }

    public void recalculateLine() {
        this.calculateCenterPoint();
        this.setLineDirection(UtilityBox.getDirectionFromAngle(this.getLineAngle()));
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
    private Pair<Boolean,Point> isIntersecting(Line other) {
       boolean response_1=false;
        double thisDX = this.getEndPoint().getX() - this.getStartPoint().getX();
        double otherDX = other.getEndPoint().getX() - other.getStartPoint().getX();
        double thisDY = this.getEndPoint().getY() - this.getStartPoint().getY();
        double otherDY = other.getEndPoint().getY() - other.getStartPoint().getY();
        double d = (thisDX) * (otherDY) - (otherDX) * (thisDY);
        // lines are equal or one is part of the other will have infinite
        // intersection points and will return false.
        if ((d == 0) || (this.equals(other))) {
            response_1 =false;
        }
        // both lines don't have slope and wont intersect(parallel)
        if (((thisDX == 0) && (otherDX == 0)) || ((thisDY == 0) && (otherDY == 0))) {
            response_1 =false;
        }
        Point response_2 = intersectionWith(other);
        if(response_2!=null){
        // checks if point p(intersection point) is on both lines
        if ((this.pointInShape(response_2)) && (other.pointInShape(response_2))) {
            response_1= true;
        }}
        return  new Pair<>(response_1,response_2);
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise,intersection point is considered one
     * only if the lines cross each other,and intersection point is on both lines. if the lines have more then one
     * intersection point they don't count as intersecting.
     *
     * @param other - the line to compare to this line and find the intersection point.
     * @return intersectionPoint - the intersection Point if the lines compared intersect,null otherwise.
     */
    private Point intersectionWith(Line other) {
        // returns null if both lines are identical (infinite intersection points).
        if (this.equals(other)) {
            return null;
        }
        double x1 = this.getStartPoint().getX();
        double x2 = other.getStartPoint().getX();
        double y1 = this.getStartPoint().getY();
        double y2 = other.getStartPoint().getY();
        double thisDX = this.getEndPoint().getX() - x1;
        double thisDY = y1 - this.getEndPoint().getY();
        double otherDX = other.getEndPoint().getX() - x2;
        double otherDY = y2 - other.getEndPoint().getY();
        double eq1 = (thisDY * x1) + (thisDX * y1);
        double eq2 = (otherDY * x2) + (otherDX * y2);
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
            return new Point(x, y);
        }
    }

    /**
     * Checks and return (true/false) if the given point is within range of this line(start,end points) ,meaning the
     * point is between or equal to this line start and end points.
     *
     * @param p - the compared point to this line start,end points.
     * @return true if the lines are equal (based on x,y values of the start and end points ),false otherwise.
     */
    @Override
    public boolean pointInShape(Point p) {
        double minX = Math.min(this.getStartPoint().getX(), this.getEndPoint().getX());
        double maxX = Math.max(this.getStartPoint().getX(), this.getEndPoint().getX());
        double minY = Math.min(this.getStartPoint().getY(), this.getEndPoint().getY());
        double maxY = Math.max(this.getStartPoint().getY(), this.getEndPoint().getY());
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
     * Checks and return (true/false) if compared lines have the same start and end points combinations.
     *
     * @param other - the other line to compare to this line.
     * @return true if the lines are equal (based on x,y values of the start and end points ),false otherwise.
     */
    public boolean equals(Line other) {
        if ((this.getEndPoint().equals(other.getEndPoint())) && (this.getStartPoint().equals(other.getStartPoint()))) {
            return true;
        }
        if ((this.getEndPoint().equals(other.getStartPoint())) && (this.getStartPoint().equals(other.getEndPoint()))) {
            return true;
        }
        return false;
    }


    /**
     * Calculates and returns the distance between this line starting point and given point (p).
     *
     * @param p - the given point to calculate the distance to this line starting point.
     * @return distance - the calculated distance between given point (p) and this line starting point.
     */
    public double distanceToStartPoint(Point p) {
        // dx - the distance between this line start point and given point (p) x coordinates.
        double dx = this.getStartPoint().getX() - p.getX();
        // dy - the distance between this line start point and given point (p) y coordinates.
        double dy = this.getStartPoint().getY() - p.getY();
        return UtilityBox.roundUp(Math.sqrt((dx * dx) + (dy * dy)));
    }

    /**
     * Calculates and returns the distance between this line center point and given point (p).
     *
     * @param p - the given point to calculate the distance to this line center point.
     * @return distance - the calculated distance between given point (p) and this line center point.
     */
    public double distanceToCenterPoint(Point p) {
        // dx - the distance between this line center point and given point (p) x coordinates.
        double dx = this.getCenterPoint().getX() - p.getX();
        // dy - the distance between this line center point and given point (p) y coordinates.
        double dy = this.getCenterPoint().getY() - p.getY();
        return UtilityBox.roundUp(Math.sqrt((dx * dx) + (dy * dy)));
    }

    /**
     * Calculates and returns the distance between this line end point and given point (p).
     *
     * @param p - the given point to calculate the distance to this line end point.
     * @return distance - the calculated distance between given point (p) and this line end point.
     */
    public double distanceToEndPoint(Point p) {
        // dx - the distance between this line end point and given point (p) x coordinates.
        double dx = this.getEndPoint().getX() - p.getX();
        // dy - the distance between this line end point and given point (p) y coordinates.
        double dy = this.getEndPoint().getY() - p.getY();
        return UtilityBox.roundUp(Math.sqrt((dx * dx) + (dy * dy)));
    }

    /**
     * Draws on the given board (d) this line and its start,middle,end points as a circle with radius 3 and color: start
     * = Blue,middle = Black,end = green.
     *
     * @param canvas - the given board to draw the line and points on.
     */
    public void drawWithPoints(Canvas canvas) {
        this.getPaint().setColor(this.getColor());
        canvas.drawLine((int) this.getStartPoint().getX(), (int) this.getStartPoint().getY(), (int) this.getEndPoint().getX(),
                (int) this.getEndPoint().getY(), this.getPaint());
        this.getPaint().setColor(Color.BLUE);
        canvas.drawCircle((float) this.getStartPoint().getX(), (float) this.getStartPoint().getY(), 3, this.getPaint());
        this.getPaint().setColor(Color.BLACK);
        canvas.drawCircle((float) this.getCenterPoint().getX(), (float) this.getCenterPoint().getY(), 3, this.getPaint());
        this.getPaint().setColor(Color.GREEN);
        canvas.drawCircle((float) this.getEndPoint().getX(), (float) this.getEndPoint().getY(), 3, this.getPaint());
    }

    /**
     * Draws on the given board (d) this line.
     *
     * @param canvas - the given board to draw the line and points on.
     */
    public void draw(Canvas canvas) {
        this.getPaint().setColor(this.getColor());
        canvas.drawLine((int) this.getStartPoint().getX(), (int) this.getStartPoint().getY(), (int) this.getEndPoint().getX(),
                (int) this.getEndPoint().getY(), this.getPaint());
    }

    /**
     * Draws on the given board (d) this line.
     *
     * @param canvas - the given board to draw the line and points on.
     */
    public void drawWithDirection(Canvas canvas) {
        this.getPaint().setColor(this.getColor());
        canvas.drawLine((int) this.getStartPoint().getX(), (int) this.getStartPoint().getY(), (int) this.getEndPoint().getX(),
                (int) this.getEndPoint().getY(), this.getPaint());
        this.getEndPoint().draw(canvas);
    }

    /**
     * Draws on the given board (d) each point in the given list as a orange circle with radius 3.
     *
     * @param list   - an list that holds points.
     * @param canvas - the given board to draw the points on.
     */
    public void drawIntersectionPoints(List<Point> list, Canvas canvas) {
        this.getPaint().setColor(Color.BLUE);
        for (int i = 0; i < list.size(); i++) {
            Point interPoint = list.get(i);
            canvas.drawCircle((float) interPoint.getX(), (float) interPoint.getY(), 3, this.getPaint());
        }
    }


    /**
     * Calculates and returns the length of the line.
     *
     * @return length - the length of this line.
     */
    public double getLength() {
        // dx - the distance between start and end points x coordinate.
        double dx = this.getStartPoint().getX() - this.getEndPoint().getX();
        // dy - the distance between start and end points y coordinate
        double dy = this.getStartPoint().getY() - this.getEndPoint().getY();
        return UtilityBox.roundUp(Math.sqrt((dx * dx) + (dy * dy)));
    }

    public double getLineAngle() {
        final double deltaY = (this.getStartPoint().getY() - this.getEndPoint().getY());
        final double deltaX = (this.getEndPoint().getX() - this.getStartPoint().getX());
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return UtilityBox.roundUp((result < 0) ? (360d + result) : result);
    }


    /**
     * Returns this line slope based on his start and end points.
     *
     * @return slope - this line slope.
     */
    public double getSlope() {
        double denominator = this.getStartPoint().getX() - this.getEndPoint().getX();
        if (Double.compare(denominator, 0.0) == 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return UtilityBox.roundUp((this.getStartPoint().getY() - this.getEndPoint().getY() / denominator));
        }
    }

    private void moveEndPointTo(double x, double y) {
        this.getEndPoint().movePointTo(x, y);
        this.recalculateLine();
    }

    private void moveStartPointTo(double x, double y) {
        this.getStartPoint().movePointTo(x, y);
        this.recalculateLine();
    }


    /**
     * @return m_start_point - the start point of the line
     */
    public Point getStartPoint() {
        return this.m_start_point;
    }

    private void setStartPoint(Point new_start_point) {
        this.m_start_point = new_start_point;
    }

    /**
     * @return endPoint() - the end point of the line
     */
    public Point getEndPoint() {
        return this.m_end_point;
    }

    private void setEndPoint(Point new_end_point) {
        this.m_end_point = new_end_point;
    }

    @Override
    public Point getCenterPoint() {
        return this.m_center_point;
    }

    @Override
    public void setCenterPoint(Point center_point) {
        this.m_center_point = center_point;
        this.recalculateLine();
    }

    private void calculateCenterPoint() {
        double mid_x = (this.getStartPoint().getX() + this.getEndPoint().getX()) / 2;
        double mid_y = (this.getStartPoint().getY() + this.getEndPoint().getY()) / 2;
        this.getCenterPoint().movePointTo(mid_x, mid_y);
    }

    public Direction getLineDirection() {
        return this.m_line_direction;
    }

    private void setLineDirection(Direction line_direction) {
        this.m_line_direction = line_direction;
    }

    public Side getShapeLineSide() {
        return this.m_shape_line_side;
    }

    private void setShapeLineSide(Side shape_line_side) {
        this.m_shape_line_side = shape_line_side;
    }

    /**
     * Return string "Line: start Point: (x,y) end Point: (x,y)".
     */
    @Override
    public String toString() {
        return "Line: Start " + this.getStartPoint().toString() + " End " + this.getEndPoint().toString();
    }

    /**
     * print this line start and end points at the format "Line: start Point: (x,y) end Point: (x,y)".
     */
    @Override
    public void printShapeString() {
        System.out.println(this.toString());
    }

    @Override
    public Paint getPaint() {
        return this.m_paint;
    }

    @Override
    public int getColor() {
        return this.m_color;
    }

    @Override
    public void setColor(int color) {
        this.m_color = color;
    }

    @Override
    public void setRandomColor() {
        this.setColor(UtilityBox.randomColor());
    }

    @Override
    public Pair<Boolean,Point> intersectingShape(Shape other_shape) {
        String shape_type = other_shape.shapeType();
        switch (shape_type) {
            case "Line":
                return this.isIntersecting((Line) other_shape);

            case "Rectangle":
                return other_shape.intersectingShape(this);

            case "Circle":
                return other_shape.intersectingShape(this);
        }
        return new Pair<> (false,null) ;
    }

    @Override
    public String shapeType() {
        return "Line";
    }

    @Override
    public HashMap<Side, Line> getShapeLines() {
        return this.m_shape_lines;
    }


}