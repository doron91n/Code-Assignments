package fluffybun.game.Geometry;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Pair;

import java.util.HashMap;

import fluffybun.game.Side;
import fluffybun.game.UtilityBox;

public class Triangle implements Shape {
    private Paint m_paint = new Paint();
    private int m_color = Color.RED;
    private Point m_point_A;
    private Point m_point_B;
    private Point m_point_C;
    private Point m_center_point;
    private Line m_AB_line;
    private Line m_AC_line;
    private Line m_BC_line;
    private HashMap<Side, Line> m_shape_lines;

    private double m_height;

    /**
     * A top (1)
     * |\
     * | \
     * |  \
     * |   \
     * C left (3) |____\ B right (2)
     */
    public Triangle(Point top_point_A, Point right_point_B, Point left_point_C) {
        this.setPointA(top_point_A);
        this.setPointB(right_point_B);
        this.setPointC(left_point_C);
    }

    public void createLines() {
        this.m_shape_lines = new HashMap<>();
        this.m_shape_lines.put(Side.TOP_RIGHT, new Line(new Point(this.getPointA()), new Point(this.getPointB()), Side.TOP_RIGHT));
        this.m_shape_lines.put(Side.BOTTOM ,new Line(this.getPointB(),new Point( this.getPointC()), Side.BOTTOM));
        this.m_shape_lines.put(Side.TOP_LEFT ,new Line(this.getPointA(), this.getPointC(), Side.TOP_LEFT));

    }

    public void recalculateTriangle() {

    }

    public double getTriangleArea() {
        Point a = this.getPointA();
        Point b = this.getPointB();
        Point c = this.getPointC();
        return Math.abs(((a.getX() * (b.getY() - c.getY())) +
                (b.getX() * (c.getY() - a.getY())) +
                (c.getX() * (a.getY() - b.getY()))) / 2);
    }
    /*
    private void drawTriangle(int x, int y, int width, int height, boolean inverted, Paint paint, Canvas canvas){

        Point p1 = new Point(x,y);
        int pointX = x + width/2;
        int pointY = inverted?  y + height : y - height;

        Point p2 = new Point(pointX,pointY);
        Point p3 = new Point(x+width,y);


        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p3.x,p3.y);
        path.close();

        canvas.drawPath(path, paint);
    }

*/

    public void draw(Canvas canvas) {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo((float) this.getPointA().getX(), (float) this.getPointA().getY());
        path.lineTo((float) this.getPointB().getX(), (float) this.getPointB().getY());
        path.lineTo((float) this.getPointC().getX(), (float) this.getPointC().getY());
        path.close();
        canvas.drawPath(path, this.getPaint());
    }

    public Point getPointA() {
        return this.m_point_A;
    }

    public void setPointA(Point point_A) {
        this.m_point_A = point_A;
    }

    public Point getPointB() {
        return this.m_point_B;
    }

    public void setPointB(Point point_B) {
        this.m_point_B = point_B;
    }

    public Point getPointC() {
        return this.m_point_C;
    }

    public void setPointC(Point point_C) {
        this.m_point_C = point_C;
    }

    public Line getABLine() {
     return this.getShapeLines().get(Side.TOP_RIGHT);
    }

    public Line getACLine() {
        return this.getShapeLines().get(Side.TOP_LEFT);
    }

    public Line getBCLine() {
        return this.getShapeLines().get(Side.BOTTOM);
    }

    @Override
    public Point getCenterPoint() {
        return this.m_center_point;
    }

    @Override
    public void setCenterPoint(Point center_point) {
        this.m_center_point = center_point;
    }

    /**
     * prints this point in format "Point: (x,y)".
     */
    @Override
    public void printShapeString() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Triangle: A " + this.getPointA().toString() + " ,B " + this.getPointB().toString() + " ,C " + this.getPointC().toString();
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
    public boolean pointInShape(Point p) {
        double x1 = this.getPointA().getX();
        double y1 = this.getPointA().getY();
        double x2 = this.getPointB().getX();
        double y2 = this.getPointB().getY();
        double x3 = this.getPointC().getX();
        double y3 = this.getPointC().getY();

        double denominator = ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        double a = ((y2 - y3) * (p.getX() - x3) + (x3 - x2) * (p.getY() - y3)) / denominator;
        double b = ((y3 - y1) * (p.getX() - x3) + (x1 - x3) * (p.getY() - y3)) / denominator;
        double c = 1 - a - b;
        return ((0 <= a) && (a <= 1) && (0 <= b) && (b <= 1) && (0 <= c) && (c <= 1));
    }

    // another version
    public boolean pointInShape(Point p, Point p0, Point p1, Point p2) {
        double A = 1 / 2 * (-p1.getY() * p2.getX() + p0.getY() * (-p1.getX() + p2.getX()) + p0.getX() * (p1.getY() - p2.getY()) + p1.getX() * p2.getY());
        double sign = A < 0 ? -1 : 1; // if(A<0) sign =  -1 else 1
        double s = (p0.getY() * p2.getX() - p0.getX() * p2.getY() + (p2.getY() - p0.getY()) * p.getX() + (p0.getX() - p2.getX()) * p.getY()) * sign;
        double t = (p0.getX() * p1.getY() - p0.getY() * p1.getX() + (p0.getY() - p1.getY()) * p.getX() + (p1.getX() - p0.getX()) * p.getY()) * sign;

        return ((s > 0) && (t > 0) && ((s + t) < (2 * A * sign)));
    }

    @Override
    public Pair<Boolean,Point> intersectingShape(Shape other_shape) {
        return new     Pair<>(false,null) ;                               //////////////  implement
    }

    @Override
    public String shapeType() {
        return "Triangle";
    }

    @Override
    public HashMap<Side, Line> getShapeLines() {
        return this.m_shape_lines;
    }
}
