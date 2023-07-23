package fluffybun.game.Geometry;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fluffybun.game.Side;
import fluffybun.game.UtilityBox;


public class Rectangle implements Shape {
    private Rect m_rectangle_shape;
    private Point m_rectangle_upper_left_point;
    private Point m_rectangle_center_point = new Point(0, 0);
    private double m_rectangle_width;
    private double m_rectangle_height;
    private int m_color = Color.CYAN; // default color.
    private Paint m_paint = new Paint();
    private HashMap<Side, Line> m_shape_lines;


    /**
     * create a new rectangle with upper left location point,width,height,color.
     *
     * @param upperLeft   - this rectangle starting point,the upper left point.
     * @param width       - this rectangle width.
     * @param height      - this rectangle height.
     * @param shape_color - this rectangle color
     */
    public Rectangle(Point upperLeft, double width, double height, int shape_color) {
        this.setUpperLeftPoint(upperLeft);
        this.setHeight(height);
        this.setWidth(width);
        this.setColor(shape_color);
        this.calculateRectangleCenterPoint();
        // left x , top y, right x , bottom y
        this.setRectShape(new Rect((int) upperLeft.getX(), (int) upperLeft.getY(), (int) (upperLeft.getX() + width), (int) (upperLeft.getY() + height)));
        this.setRectangleLines();
    }

    /**
     * create a new rectangle with location start point(upperLeft) and end point(underRight).
     *
     * @param upperLeft   - this rectangle starting point,the upper left point.
     * @param bottomRight - this rectangle end point,the bottom right point.
     * @param shape_color - this rectangle color
     */
    public Rectangle(Point upperLeft, Point bottomRight, int shape_color) {
        this(upperLeft, bottomRight.getX() - upperLeft.getX(), bottomRight.getY() - upperLeft.getY(), shape_color);
    }


    /**
     * sets this rectangle left/right/top/bottom lines based on its given starting point ,width and height.
     */
    private void setRectangleLines() {
        double x = this.getUpperLeftPoint().getX() + this.getWidth();
        double y = this.getUpperLeftPoint().getY() + this.getHeight();
        Point topRightP = new Point(x, this.getUpperLeftPoint().getY());
        Point bottomLineRightP = new Point(x, y);
        Point bottomLineLeftP = new Point(this.getUpperLeftPoint().getX(), y);
        // All lines have their own points
        this.m_shape_lines = new HashMap<>();
        this.m_shape_lines.put(Side.TOP, new Line(this.getUpperLeftPoint(), new Point(topRightP), Side.TOP));
        this.m_shape_lines.put(Side.LEFT, new Line(bottomLineLeftP, new Point(this.getUpperLeftPoint()), Side.LEFT));
        this.m_shape_lines.put(Side.RIGHT, new Line(bottomLineRightP, topRightP, Side.RIGHT));
        this.m_shape_lines.put(Side.BOTTOM, new Line(new Point(bottomLineLeftP), new Point(bottomLineRightP), Side.BOTTOM));
    }

    /**
     * moves the rectangle location (top+bottom by dy value), (right+left by dx) the width and height stay unchanged.
     *
     * @param dx - the value by which to move this rectangle left+right.
     * @param dy - the value by which to move this rectangle top+bottom.
     */
    public void moveShapeBy(double dx, double dy) {
        this.getRectShape().offset((int) dx, (int) dy);
        this.getCenterPoint().movePointBy(dx, dy);
        this.getBottomLine().moveShapeBy(dx, dy);
        this.getTopLine().moveShapeBy(dx, dy);
        this.getLeftLine().moveShapeBy(dx, dy);
        this.getRightLine().moveShapeBy(dx, dy);

    }

    /**
     * moves the rectangle to a new position based on a new center point.
     *
     * @param new_center_point - the rectangle new center point coordinates.
     */
    public void moveShapeByCenterPoint(Point new_center_point) {
        Point c_bf = new Point(this.getCenterPoint()); ///////
        double dx = this.getCenterPoint().getX() - new_center_point.getX();
        double dy = this.getCenterPoint().getY() - new_center_point.getY();


        double rect_center_x = this.getWidth() / 2;
        double rect_center_y = this.getHeight() / 2;
        double left_x = (new_center_point.getX() - rect_center_x);
        double top_y = (new_center_point.getY() - rect_center_y);
        double right_x = (new_center_point.getX() + rect_center_x);
        double bottom_y = (new_center_point.getY() + rect_center_y);
        this.moveRectangleTo(left_x, top_y, right_x, bottom_y);

        //  this.moveShapeBy(dx,dy);
    //    System.out.println("moveShapeByCenterPoint   center changed from :" + c_bf.toString() + " to new center " + new_center_point.toString() + " Dx:" + dx
      //          + "  dy:" + dy + "current center:" + this.getCenterPoint().toString());

    }

    /**
     * moves the rectangle to a new position.
     *
     * @param left_x   - the rectangle new left side x coordinates.
     * @param top_y    - the rectangle new top side y coordinates.
     * @param right_x  - the rectangle new right side x coordinates.
     * @param bottom_y - the rectangle new bottom side y coordinates.
     */
    private void moveRectangleTo(double left_x, double top_y, double right_x, double bottom_y) {
        this.getRectShape().set((int) left_x, (int) top_y, (int) right_x, (int) bottom_y);
        for (HashMap.Entry<Side, Line> entry : this.getShapeLines().entrySet()) {
            switch (entry.getKey()) {
                case TOP:
                    entry.getValue().moveLineTo(left_x, top_y, right_x, top_y);
                    break;
                case BOTTOM:
                    entry.getValue().moveLineTo(left_x, bottom_y, right_x, bottom_y);
                    break;
                case LEFT:
                    entry.getValue().moveLineTo(left_x, bottom_y, right_x, top_y);
                    break;
                case RIGHT:
                    entry.getValue().moveLineTo(right_x, bottom_y, right_x, top_y);
                    break;
            }
        }
        // recalculate width + height? can be changed like this **************************************************
        this.calculateRectangleCenterPoint();
    }


    /**
     * checks if given Rect is within this rectangle borders(lines),returns true if it is and false otherwise.
     *
     * @param other_shape - the given  shape to check if within the rectangle borders.
     * @return  Pair of <true,collision Point>  if the shape is within the rectangle borders and <false,null> otherwise.
     */

    @Override
    public Pair<Boolean,Point> intersectingShape(Shape other_shape) {
        // Checks if there is intersection between this shape lines and other shape lines
        if(!this.getShapeLines().isEmpty()&&!other_shape.getShapeLines().isEmpty()){
            Pair<Boolean,Point> collision_check;
        for (HashMap.Entry<Side, Line> my_shape_line : this.getShapeLines().entrySet()) {
            for (HashMap.Entry<Side, Line> other_shape_line : other_shape.getShapeLines().entrySet()) {
                collision_check =  my_shape_line.getValue().intersectingShape(other_shape_line.getValue());
                if (collision_check.first) {
                    return collision_check;
                }
            }
        }}
        return  new Pair<>(false,null);

        /**
        // checks if this shape contains other shape
        String shape_type = other_shape.shapeType();
        switch (shape_type) {
            case "Line":
                return this.intersectsLine((Line) other_shape);

            case "Rectangle":
                return this.intersectsRectangle((Rectangle) other_shape);

            case "Circle":
                return other_shape.intersectingShape(this);
        }
        return false; **/
    }

    /**
     * checks if given point (p) is within this rectangle borders(lines),returns true if it is and false otherwise.
     *
     * @param p - the given point to check if within the rectangle borders.
     * @return true if the point is within the rectangle borders and false otherwise.
     */
    @Override
    public boolean pointInShape(Point p) {
        double x1 = this.getUpperLeftPoint().getX();
        double y1 = this.getUpperLeftPoint().getY();
        double x2 = this.getUpperLeftPoint().getX() + this.getWidth();
        double y2 = this.getUpperLeftPoint().getY() + this.getHeight();
        int x1CmpPx = Double.compare(x1, p.getX()); // x1 needs to be smaller/equal then p.x
        int x2CmpPx = Double.compare(x2, p.getX()); // x2 needs to be bigger/equal then p.x
        int y1CmpPy = Double.compare(y1, p.getY()); // y1 needs to be smaller/equal then p.y
        int y2CmpPy = Double.compare(y2, p.getY()); // y2 needs to be bigger/equal then p.y
        if ((x1CmpPx <= 0) && (x2CmpPx >= 0) && (y1CmpPy <= 0) && (y2CmpPy >= 0)) {
            return true;
        }
        return false;
    }

    public boolean contains(int x ,int y){
        return  this.getRectShape().contains(x,y);
    }

    /**
     * checks if given point (p) is within this rectangle borders(lines),return the line that the point is on otherwise
     * null.
     *
     * @param point - the given point to check if within the rectangle borders.
     * @return - the line which the point p is on ,null if its on none of them.
     */
    public Line findCollisionLine(Point point) {
        for (HashMap.Entry<Side, Line> entry : this.getShapeLines().entrySet()) {
            Line current_line = entry.getValue();
            if (current_line.pointInShape(point)) {
                return current_line;
            }
        }
        return null;
    }

    public Line getClosestLineToPoint(Point point) {
        double min_distance = Double.POSITIVE_INFINITY;
        double current_line_distance;
        Line closest_line = null;
        for (HashMap.Entry<Side, Line> entry : this.getShapeLines().entrySet()) {
            current_line_distance = ( entry.getValue()).distanceToCenterPoint(point);
            if (current_line_distance < min_distance) {
                min_distance = current_line_distance;
                closest_line = entry.getValue();
            }
        }
        return closest_line;
    }

    /**
     * @return this rectangle leftLine.
     */
    public Line getLeftLine() {
        return this.getShapeLines().get(Side.LEFT);
    }

    /**
     * @return this rectangle rightLine.
     */
    public Line getRightLine() {
        return this.getShapeLines().get(Side.RIGHT);
    }

    /**
     * @return this rectangle topLine.
     */
    public Line getTopLine() {
        return this.getShapeLines().get(Side.TOP);
    }

    /**
     * @return this rectangle underLine.
     */
    public Line getBottomLine() {
        return this.getShapeLines().get(Side.BOTTOM);
    }

    /**
     * @return this rectangle width.
     */
    public double getWidth() {
        return this.m_rectangle_width;
    }

    /**
     * @param width - sets this rectangle width to given value.
     */
    private void setWidth(double width) {
        this.m_rectangle_width = width;
    }

    /**
     * @return this rectangle height.
     */
    public double getHeight() {
        return this.m_rectangle_height;
    }

    /**
     * @param height - sets this rectangle height to given value.
     */
    private void setHeight(double height) {
        this.m_rectangle_height = height;
    }

    /**
     * @return this rectangle starting point(upper-left point) location.
     */
    public Point getUpperLeftPoint() {
        return this.m_rectangle_upper_left_point;
    }

    /**
     * @param upper_left_point - this rectangle starting point(upper-left point) location.
     */
    private void setUpperLeftPoint(Point upper_left_point) {
        this.m_rectangle_upper_left_point = upper_left_point;
    }


    /**
     * @return this rectangle center point location.
     */
    @Override
    public Point getCenterPoint() {
        //  calculateRectangleCenterPoint();
        return this.m_rectangle_center_point;
    }


    /**
     * @param center_point - this rectangle center point location.
     */
    @Override
    public void setCenterPoint(Point center_point) {
        this.m_rectangle_center_point = center_point;
    }

    /**
     * calculates and sets this rectangle center point - the middle of it based on upperLeft point,height,width.
     */
    private void calculateRectangleCenterPoint() {
        double x = this.getUpperLeftPoint().getX() + (this.getWidth() / 2);
        double y = this.getUpperLeftPoint().getY() + (this.getHeight() / 2);
        this.getCenterPoint().movePointTo(x, y);
    }

    public void recalculateRectangle() {
        //   this.setWidth();
        this.calculateRectangleCenterPoint();
    }

    /**
     * @return this rectangle Rect shape.
     */
    private Rect getRectShape() {
        return this.m_rectangle_shape;
    }

    /**
     * @param rectangle_shape - this rectangle Rect shape.
     */
    private void setRectShape(Rect rectangle_shape) {
        this.m_rectangle_shape = rectangle_shape;
    }


    /**
     * @return this rectangle Color.
     */
    @Override
    public int getColor() {
        return this.m_color;
    }


    /**
     * change this rectangle color to the given color.
     *
     * @param color - the new color for the rectangle.
     */
    @Override
    public void setColor(int color) {
        this.m_color = color;
    }

    /**
     * change this rectangle color to a random color.
     */
    @Override
    public void setRandomColor() {
        this.setColor(UtilityBox.randomColor());
    }

    @Override
    public Paint getPaint() {
        return this.m_paint;
    }

    public void draw(Canvas canvas) {
        this.getPaint().setColor(this.getColor());
        canvas.drawRect(this.getRectShape(), this.getPaint());
        this.getPaint().setColor(Color.MAGENTA);

        canvas.drawCircle((float) this.getCenterPoint().getX(), (float) this.getCenterPoint().getY(), 7, this.getPaint());
        this.getPaint().setColor(Color.BLACK);

        for (HashMap.Entry<Side, Line> entry : this.getShapeLines().entrySet()) {
            entry.getValue().draw(canvas);
        }
    }

    @Override
    public void printShapeString() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Rectangle: Center " + this.getCenterPoint().toString() + "UpperLeft " + this.getUpperLeftPoint().toString() + " Width: " + this.getWidth() + " Height: " + this.getHeight();
    }

    @Override
    public String shapeType() {
        return "Rectangle";
    }

    @Override
    public HashMap<Side, Line> getShapeLines() {
        return this.m_shape_lines;
    }
}
