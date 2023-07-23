package fluffybun.game.GameObjects;

import android.graphics.Canvas;
import android.util.Pair;

import fluffybun.game.Collision.Collidable;
import fluffybun.game.Collision.CollidableManager;
import fluffybun.game.Collision.CollisionReport;
import fluffybun.game.Geometry.Circle;
import fluffybun.game.Geometry.Point;
import fluffybun.game.Geometry.Shape;
import fluffybun.game.Movement.Movable;
import fluffybun.game.Movement.MovementManager;
import fluffybun.game.Movement.Velocity;
import fluffybun.game.Side;
import fluffybun.game.UtilityBox;

public class Ball implements Collidable<Circle>, GameObject, Movable {
    private Circle m_shape;
    private int m_ID = UtilityBox.generateID();
    private int m_speed = 4;
    private int m_angle = 90;
    private Velocity m_velocity;

    public Ball(Point center_point,int radius,int color) {
        this.m_shape = new Circle( center_point, radius, color);
        this.setVelocity(Velocity.fromAngleAndSpeed(this.m_angle, this.m_speed));
        this.getVelocity().createDirectionLine(this.getShape().getCenterPoint());
        MovementManager.getMovementListener().addMovingObject(this);
    }


    @Override
    public void draw(Canvas canvas) {
        this.getVelocity().getDirectionLine().drawWithDirection(canvas);
        this.getShape().draw(canvas);
    }

    @Override
    public void update() {
        this.move();
        this.getShape().updateEnclosingRectShape();
    }

    @Override
    public void move() {
        System.out.println("Center Point: " + this.getShape().getCenterPoint().toString() + this.getVelocity().toString());
        this.getVelocity().applyToPoint(this.getShape().getCenterPoint());
        this.getVelocity().moveDirectionLine();
        MovementManager.getMovementListener().reportMovement(this);
    }

    @Override
    public Pair<Boolean,Point> collidedWith(Collidable other) {
        //   System.out.println("Ball id: " + this.getID() + " collidedWith collidable id: " + other.getID());
        //   System.out.println("Ball collidedWith true at point: " + other.getCollisionPoint().toString());
        return (this.getShape().intersectingShape((Shape) other.getShape()));
    }


    @Override
    public double collisionResponse(CollisionReport collision_report) {
        System.out.println("******************** Ball collision response *******************");
      //  this.getShape().setRandomColor();
        Velocity current_velocity=this.getVelocity();
        if(collision_report.getHitterID()==this.getID()){
            System.out.println("Case 1: ball hitter " +this.getVelocity().toString());
            if((collision_report.getHitterCollisionSide()==Side.LEFT)||(collision_report.getHitterCollisionSide()==Side.RIGHT)){
                this.setVelocity(Velocity.fromDxDy(current_velocity.getDx()*-1,current_velocity.getDy()));
            }
            if((collision_report.getHitterCollisionSide()==Side.TOP)||(collision_report.getHitterCollisionSide()==Side.BOTTOM)){
                this.setVelocity(Velocity.fromDxDy(current_velocity.getDx(),current_velocity.getDy()*-1));
            }
            System.out.println("Case 1 after: ball hitter " +this.getVelocity().toString());

        }
        if(collision_report.getBeingHitID()==this.getID()){
            System.out.println("Case 2: ball being hit  " +this.getVelocity().toString());

            if((collision_report.getBeingHitCollisionSide()==Side.LEFT)||(collision_report.getBeingHitCollisionSide()==Side.RIGHT)){
                this.setVelocity(Velocity.fromDxDy(current_velocity.getDx()*-1,current_velocity.getDy()));
            }
            if((collision_report.getBeingHitCollisionSide()==Side.TOP)||(collision_report.getBeingHitCollisionSide()==Side.BOTTOM)){
                this.setVelocity(Velocity.fromDxDy(current_velocity.getDx(),current_velocity.getDy()*-1));
            }
            System.out.println("Case 1 after: ball  being hit " +this.getVelocity().toString());

        }
        this.getVelocity().createDirectionLine(this.getShape().getCenterPoint());

        return 2;
    }


    @Override
    public Side getCollisionSide(Point collision_point) {
        return this.getShape().getClosestLineToPoint(collision_point).getShapeLineSide();
    }

    @Override
    public int getID() {
        return this.m_ID;
    }

    @Override
    public Circle getShape() {
        return this.m_shape;
    }

    @Override
    public Velocity getVelocity() {
        return this.m_velocity;
    }

    @Override
    public void setVelocity(Velocity velocity) {
        this.m_velocity = velocity;
    }

    @Override
    public void onDestroy(){
        MovementManager.getMovementListener().removeMovingObject(this.getID());
        CollidableManager.getCollidableManager().removeCollidable(this.getID());
    }

}
