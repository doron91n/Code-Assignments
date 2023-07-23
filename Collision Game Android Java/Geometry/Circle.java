package fluffybun.game.Geometry;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;

import java.util.HashMap;

import fluffybun.game.Side;
import fluffybun.game.UtilityBox;


public class Circle implements Shape {

    private Point m_center_point;
    private int m_radius;
    private int m_color;
    private Paint m_paint = new Paint();
    private Rectangle m_rectangle_shape;


    public Circle(Point center_point,int radius,int color) {
        this.setRadius(radius);
        this.setColor(color);
        this.setCenterPoint(center_point);

        double left_x = this.getCenterPoint().getX() - this.getRadius();
        double top_y = this.getCenterPoint().getY() - this.getRadius();
        Point upper_left_point = new Point(left_x, top_y);
        int width_height = 2 * this.getRadius();
        this.m_rectangle_shape = new Rectangle(upper_left_point, width_height, width_height, Color.BLUE);
        this.m_rectangle_shape.setCenterPoint(this.getCenterPoint());
    }

    public int getRadius() {
        return this.m_radius;
    }

    public void setRadius(int radius) {
        this.m_radius = radius;
    }

    public void draw(Canvas canvas) {
      //  this.m_rectangle_shape.draw(canvas);
        this.getPaint().setColor(this.getColor());
        canvas.drawCircle((int) this.getCenterPoint().getX(), (int) this.getCenterPoint().getY(), this.getRadius(), this.getPaint());
    }

    @Override
    public boolean pointInShape(Point p) {
        if (this.getCenterPoint().getDistanceFrom(p) <= this.getRadius()) {
            return true;
        }
        return false;
    }

    @Override
    public Point getCenterPoint() {
        return this.m_center_point;
    }

    @Override
    public void setCenterPoint(Point center_point) {
        this.m_center_point = center_point;
    }

    @Override
    public String toString() {
        return "Circle: Center:" + this.getCenterPoint().toString() + " , Radius: " + this.getRadius();
    }

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

    public void updateEnclosingRectShape() {
        this.m_rectangle_shape.moveShapeByCenterPoint(this.getCenterPoint());
    }

    /**
     * checks if given point (p) is within this rectangle borders(lines),return the line that the point is on otherwise
     * null.
     *
     * @param p - the given point to check if within the rectangle borders.
     * @return - the line which the point p is on ,null if its on none of them.
     */
    public Line findCollisionLine(Point p) {
        return this.m_rectangle_shape.findCollisionLine(p);
    }

    public Line getClosestLineToPoint(Point point) {
        return this.m_rectangle_shape.getClosestLineToPoint(point);
    }


    @Override
    public String shapeType() {
        return "Circle";
    }

    @Override
    public Pair<Boolean,Point> intersectingShape(Shape other_shape) {
        return this.m_rectangle_shape.intersectingShape(other_shape);
    }

    @Override
    public HashMap<Side, Line> getShapeLines() {
        return this.m_rectangle_shape.getShapeLines();
    }
}
