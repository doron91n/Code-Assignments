package fluffybun.game.GameObjects;


import android.graphics.Canvas;
import android.util.Pair;

import fluffybun.game.Collision.Collidable;
import fluffybun.game.Collision.CollidableManager;
import fluffybun.game.Collision.CollisionReport;
import fluffybun.game.Geometry.Point;
import fluffybun.game.Geometry.Rectangle;
import fluffybun.game.Geometry.Shape;
import fluffybun.game.Side;
import fluffybun.game.UtilityBox;

public class Brick implements Collidable<Rectangle>, GameObject {

    private Rectangle m_rectangle_shape;
    private int m_ID = UtilityBox.generateID();

    public Brick(Point upperLeft, double width, double height, int shape_color) {
        // left_x,top_y,right_x,bottom_y
        this.m_rectangle_shape = new Rectangle(upperLeft, width, height, shape_color);
    }

    @Override
    public void draw(Canvas canvas) {
        this.getShape().draw(canvas);
    }

    @Override
    public void update() {
        //move , report moving
        //  this.m_rectangle_shape.offset(m_shape_velocity.getDx(), m_shape_velocity.getDy());

      /*  if (this.m_rectangle_shape.intersect(this.s)) {
            System.out.println("********* Colided*****8 rect shape intersects with s");
            collided = true;
        }
        /*/

    }

    @Override
    public int getID() {
        return this.m_ID;
    }

    @Override
    public Rectangle getShape() {
        return this.m_rectangle_shape;
    }


    @Override
    public Side getCollisionSide(Point collision_point) {
        return this.getShape().getClosestLineToPoint(collision_point).getShapeLineSide();
    }


    @Override
    public Pair<Boolean,Point> collidedWith(Collidable other) {
        //System.out.println("Brick id: " + this.getID() + " collidedWith collidable id: " + other.getID());
        // System.out.println("Brick collidedWith true at point: " + other.getCollisionPoint().toString());
        return (this.getShape().intersectingShape((Shape) other.getShape()));
    }

    @Override
    public double collisionResponse(CollisionReport collision_report) {
        System.out.println("******************** Brick collision response *******************");
        this.getShape().setRandomColor();

        return 0;
    }

    @Override
    public void onDestroy(){
        CollidableManager.getCollidableManager().removeCollidable(this.getID());
     }
}