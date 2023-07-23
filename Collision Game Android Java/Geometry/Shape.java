package fluffybun.game.Geometry;

import android.graphics.Paint;
import android.util.Pair;

import java.util.HashMap;

import fluffybun.game.Side;


public interface Shape {
    Point getCenterPoint();

    void setCenterPoint(Point center_point);

    //  void moveShapeByCenterPoint(Point new_center_point);
    //  void moveShapeByCenterPoint(double new_center_point_X,double new_center_point_Y );
///      void moveShapeBy(double dx, double dy) {
    String toString();

    void printShapeString();

    Paint getPaint();

    int getColor();

    void setColor(int color);

    void setRandomColor();

    boolean pointInShape(Point p);

    Pair<Boolean,Point> intersectingShape(Shape other_shape);

    String shapeType();

    HashMap<Side, Line> getShapeLines();
}
