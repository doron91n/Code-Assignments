
/**
 */
import java.awt.Color;
import java.util.Random;
import biuoop.DrawSurface;

/**
 * a ball class.
 */
public class Ball {
    private Point centerP;
    private int radius;
    private java.awt.Color ballColor;
    private Velocity velocity;
    private int screenStartX = 0;
    private int screenStartY = 0;
    private int screenHeight = 300;
    private int screenWidth = 400;
    private int rightScreenLimit = this.screenStartX + this.screenWidth;
    private int bottomScreenLimit = this.screenStartY + this.screenHeight;

    /**
     * creates a new ball with given center point,radius,color,checks that the ball starting point will be within the
     * screen parameters(height,width) P.S:default screen size is height = 0 to 300,width = 0 to 400, to change it use
     * setScreenSize(int left, int top, int right, int bottom).
     *
     * @param center - the ball center point.
     * @param r - the ball radius size.
     * @param color - the ball color.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        if (r >= 1) {
            this.radius = r;
            this.centerP = pointLimitsCheck(center, r);
            this.ballColor = color;
            // initialize the ball velocity to 0 ,unless set otherwise the ball wont move.
            this.velocity = new Velocity(0, 0);
        } else {
            // radius is not valid - print a detailed Error message and exit.
            System.out.println("Error:One or more of the given ball Radiuses is illegel: smaller then 1.");
            System.exit(1);
        }

    }

    /**
     * creates a new ball with given center point parameters (x,y),radius,color, checks that the ball starting point
     * will be within the screen parameters(height,width).
     *
     * @param x - the ball center point x parameter.
     * @param y - the ball center point y parameter.
     * @param r - the ball radius size.
     * @param color - the ball color.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        if (r >= 1) {
            this.radius = r;
            this.ballColor = color;
            Point tempP = new Point(x, y);
            this.centerP = pointLimitsCheck(tempP, r);
            // initialize the ball velocity to 0 ,unless set otherwise the ball wont move.
            this.velocity = new Velocity(0, 0);
        } else {
            // radius is not valid - print a detailed Error message and exit.
            System.out.println("Error:One or more of the given ball Radiuses is illegel: smaller then 1.");
            System.exit(1);
        }
    }

    /**
     * checks that the point given is within the screen parameters(height,width) and if not returns a new changed point.
     *
     * @param p - the point(ball center) to check if the whole ball is within the screen parameters.
     * @param ballRadius - the ball radius size.
     * @return newP - a new point with changed (if needed) x and y parameters.
     */
    public Point pointLimitsCheck(Point p, int ballRadius) {
        int x = (int) p.getX();
        int y = (int) p.getY();
        /* this several conditions sets the point to be within the screen size. */
        if ((x - ballRadius) <= this.screenStartX) {
            x = ballRadius + this.screenStartX;
        }
        if ((y - ballRadius) <= this.screenStartY) {
            y = ballRadius + this.screenStartY;
        }
        if ((x + ballRadius) >= this.rightScreenLimit) {
            x = this.rightScreenLimit - ballRadius;
        }
        if ((y + ballRadius) >= this.bottomScreenLimit) {
            y = this.bottomScreenLimit - ballRadius;
        }
        Point newP = new Point(x, y);
        return newP;
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
        return (int) this.radius;
    }

    /**
     * change this ball color to the given color.
     *
     * @param col - the new color for the ball.
     */
    public void setColor(java.awt.Color col) {
        this.ballColor = col;
    }

    /** change this ball color to a random color. */
    public void setRandomColor() {
        this.ballColor = new Color((int) (Math.random() * 0x1000000));
    }

    /** @return this.ballColor - the ball color. */
    public java.awt.Color getColor() {
        return this.ballColor;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface - the drawing surface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
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
     * checks and returns true if the radius isn't valid(smaller then 1 or the whole ball (radius*2) is bigger||equal to
     * the screen size limits and wont fit).
     *
     * @return true if the radius isn't valid ,false otherwise.
     */
    public Boolean checkRadius() {
        if ((this.radius < 1) || (this.radius >= this.screenWidth / 2) || (this.radius >= this.screenHeight / 2)) {
            return true;
        }
        return false;
    }

    /**
     * sets this ball screen size height and width limits,and checks that the ball radius is valid with the new screen
     * size by parameters.
     *
     * @param bottom - this ball Screen Height.
     * @param left - this ball Screen Start Y parameter.
     * @param top - this ball Screen Start X parameter.
     * @param right - this ball Screen Width.
     */
    public void setScreenSize(int left, int top, int right, int bottom) {
        this.screenHeight = bottom;
        this.screenWidth = right;
        this.screenStartY = top;
        this.screenStartX = left;
        this.rightScreenLimit = this.screenStartX + this.screenWidth;
        this.bottomScreenLimit = this.screenStartY + this.screenHeight;
        if (this.checkRadius()) {
            // radius is not valid - print a detailed Error message and exit.
            System.out.println(
                    "Error:One or more of the given ball Radiuses is illegel: smaller then 1 or (bigger then/equal to) "
                            + "the screen limit.");
            System.exit(1);
        }
    }

    /**
     * sets this ball screen size height and width limits,and checks that the ball radius is valid with the new screen
     * size by points.
     *
     * @param startP - this ball Screen starting point (x = Start X parameter ,y = Start Y parameter).
     * @param size - this ball Screen Width and height (x = screen width , y = screen height).
     */
    public void setScreenSizeByPoints(Point startP, Point size) {
        this.screenStartY = (int) startP.getY();
        this.screenStartX = (int) startP.getX();
        this.screenHeight = (int) size.getY();
        this.screenWidth = (int) size.getX();
        this.rightScreenLimit = this.screenStartX + this.screenWidth;
        this.bottomScreenLimit = this.screenStartY + this.screenHeight;
        if (this.checkRadius()) {
            // radius is not valid - print a detailed Error message and exit.
            System.out.println(
                    "Error:One or more of the given ball Radiuses is illegel: smaller then 1 or (bigger then/equal to) "
                            + "the screen limit.");
            System.exit(1);
        }
    }

    /**
     * @return ScreenHeight - this ball Screen Height.
     */
    public int getScreenHeight() {
        return this.screenHeight;
    }

    /**
     * @return ScreenStartY - this ball Screen Start Y.
     */
    public int getScreenStartY() {
        return this.screenStartY;
    }

    /**
     * @return ScreenWidth - this ball Screen Width.
     */
    public int getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * @return ScreenStartX - this ball Screen Start X.
     */
    public int getScreenStartX() {
        return this.screenStartX;
    }

    /**
     * moves the ball to a new point with position (x+dx, y+dy),makes sure that it stays within the screen parameters.
     */
    public void moveOneStep() {
        double x = this.centerP.getX();
        double y = this.centerP.getY();
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        /*
         * this several conditions changes the ball moving direction when touching a wall, forcing it to move within the
         * screen limit.
         */
        if (x - this.radius <= this.screenStartX) {
            this.velocity.setDx(Math.abs(dx));
        }
        if (x + this.radius >= this.rightScreenLimit) {
            this.velocity.setDx(-Math.abs(dx));
        }
        if (y - this.radius <= this.screenStartY) {
            this.velocity.setDy(Math.abs(dy));
        }
        if (y + this.radius >= this.bottomScreenLimit) {
            this.velocity.setDy(-Math.abs(dy));
        }
        /* move the ball (its center) and make sure its stays within the screen limits. */
        Point p = this.getVelocity().applyToPoint(this.centerP);
        this.centerP = pointLimitsCheck(p, this.radius);
    }
}