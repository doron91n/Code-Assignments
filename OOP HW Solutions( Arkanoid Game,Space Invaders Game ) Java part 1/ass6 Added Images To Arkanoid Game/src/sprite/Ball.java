package sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animation.GameLevel;
import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import collision.HitListener;
import collision.HitNotifier;
import game.GameEnvironment;
import game.Velocity;
import geometry.Line;
import geometry.Point;

/**
 * a Ball class.
 *
 */
public class Ball implements Sprite, HitNotifier {
    private Point centerP;
    private int radius;
    private Color ballColor;
    // initialize the ball velocity to 0 ,unless set otherwise the ball wont move.
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment environment;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private boolean changedSpeed = false;

    /**
     * creates a new ball with given center point,radius,color.
     *
     * @param center - the ball center point.
     * @param r - the ball radius size.
     * @param color - the ball color.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        if (r >= 1) {
            this.radius = r;
            this.centerP = center;
            this.ballColor = color;
        } else {
            // radius is not valid - print a detailed Error message and exit.
            System.out.println("Error:One or more of the given ball Radiuses is illegel: smaller then 1.");
            System.exit(1);
        }
    }

    /**
     * creates a new ball with given center point parameters (x,y),radius,color.
     *
     * @param x - the ball center point x parameter.
     * @param y - the ball center point y parameter.
     * @param r - the ball radius size.
     * @param color - the ball color.
     */
    public Ball(int x, int y, int r, Color color) {
        this(new Point(x, y), r, color);
    }

    /** @return this.centerP.getX() - the ball center point x parameter. */
    public int getX() {
        return (int) this.centerP.getX();
    }

    /** @return this.centerP.getY() - the ball center point y parameter. */
    public int getY() {
        return (int) this.centerP.getY();
    }

    /** @return this.radius - the ball radius. */
    public int getSize() {
        return this.radius;
    }

    /**
     * change this ball color to the given color.
     *
     * @param col - the new color for the ball.
     */
    public void setColor(Color col) {
        this.ballColor = col;
    }

    /** change this ball color to a random color. */
    public void setRandomColor() {
        this.ballColor = new Color((int) (Math.random() * 0x1000000));
    }

    /** @return this ballColor - the ball color. */
    public Color getColor() {
        return this.ballColor;
    }

    /**
     * sets the ball velocity to given velocity(v).
     *
     * @param v - the new velocity to set
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * sets the ball velocity by given velocity parameters dx,dy.
     *
     * @param dx - the x axis velocity parameter.
     * @param dy - the y axis velocity parameter.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * changes the ball velocity with a random generated angle (0-360) and speed based on the ball radius (the bigger
     * the ball the slower he is). all balls with radius bigger or equal to 50 will get velocity of 3.
     */
    public void setBallRandomVelocity() {
        Random rand = new Random(); // create a random-number generator
        double speed = 0;
        if (this.radius >= 50) {
            speed = 3;
        } else {
            if (this.radius >= 1) {
                speed = (100 / this.radius);
            }
        }
        double angle = rand.nextInt(360); // get integer in range 0-360
        this.velocity = Velocity.fromAngleAndSpeed(angle, speed);
    }

    /** @return this.velocity - the ball velocity. */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * calculates and returns this ball trajectory line with current ball center point and velocity,the trajectory line
     * starts at this ball center point and ends at position (x+dx, y+dy).
     *
     * @return trajectory - the newly calculated ball trajectory.
     */
    public Line getTrajectory() {
        double x = this.centerP.getX();
        double y = this.centerP.getY();
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        Line trajectory = new Line(x, y, (x + dx), (y + dy));
        return trajectory;
    }

    /**
     * sets this ball GameEnvironment (so the ball will be able to recognize the collidable objects).
     *
     * @param g - this ball GameEnvironment.
     */
    public void setBallGameEnvironment(GameEnvironment g) {
        this.environment = g;
    }

    /**
     * @return this ball GameEnvironment (so the ball will be able to recognize the collidable objects).
     */
    public GameEnvironment getBallGameEnvironment() {
        return this.environment;
    }

    /**
     * moves the ball to a new point with position (dt(x+dx), dt(y+dy)),if the ball hits a collidable object,the ball
     * changes
     * his trajectory with a new velocity based on the current ball velocity and the point of impact.
     */
    public void moveOneStep() {
        Line trajectory = getTrajectory();
        CollisionInfo collisionInformation = this.environment.getClosestCollision(trajectory);
        // there are no collisions on this trajectory - move the ball (its center) to new position (x+dx, y+dy).
        if (collisionInformation == null) {
            Point p = this.getVelocity().applyToPoint(this.centerP);
            this.centerP = new Point(p.getX(), p.getY());
        } else {
            Point newCenter = null;
            Point collisionPoint = collisionInformation.collisionPoint();
            Collidable collisionObject = collisionInformation.collisionObject();
            // move the ball to almost the collision point based on which side of the object it
            // is(top/under/right/left)
            Line collisonLine = collisionObject.getCollisionRectangle().findCollisonLine(collisionPoint);
            if (collisonLine.equals(collisionObject.getCollisionRectangle().getLeftLine())) {
                newCenter = new Point((collisionPoint.getX() - 0.0001), collisionPoint.getY());
            }
            if (collisonLine.equals(collisionObject.getCollisionRectangle().getRightLine())) {
                newCenter = new Point((collisionPoint.getX() + 0.0001), collisionPoint.getY());
            }
            if (collisonLine.equals(collisionObject.getCollisionRectangle().getTopLine())) {
                newCenter = new Point(collisionPoint.getX(), (collisionPoint.getY() - 0.0001));
            }
            if (collisonLine.equals(collisionObject.getCollisionRectangle().getUnderLine())) {
                newCenter = new Point(collisionPoint.getX(), (collisionPoint.getY() + 0.0001));
            }
            // move the ball to almost the impact collision and change its velocity based on the impact.
            this.centerP = newCenter;
            this.setVelocity(collisionObject.hit(this, collisionPoint, this.getVelocity()));
        }
    }

    /************************************** Sprite interface methods *************************************************/

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * notify the sprite(ball) that time has passed and invoke a certain change (moveOneStep).
     *
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
        if (!this.changedSpeed) {
            this.setVelocity(this.getVelocity().getDx() * dt, this.getVelocity().getDy() * dt);
            this.changedSpeed = true;
        }
        moveOneStep();
    }

    /**
     * adds this ball to the given game (g).
     *
     * @param g - the game to add this ball to.
     */
    @Override
    public void addToGame(GameLevel g) {
        setBallGameEnvironment(g.getGameEnvironment());
        g.addSprite(this);
    }

    /**
     * add the given HitListener (hl) to this Ball hitListeners list.
     *
     * @param hl - the hit Listener to be added to this Ball hitListeners list.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove the given HitListener (hl) from this Ball hitListeners list.
     *
     * @param hl - the hit Listener to be removed from this Ball hitListeners list.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * remove this Ball from the given Game (g),removes from the game sprite list.
     *
     * @param g - the game to be removed from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}