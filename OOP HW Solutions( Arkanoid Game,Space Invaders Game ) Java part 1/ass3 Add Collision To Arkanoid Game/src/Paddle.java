
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * a paddle class.
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle paddleShape;
    private double gameScreenWidth;
    private double gameScreenHeight;
    // gameScreenLimitBlock- the screen blocks width for (right,left) screen limit.
    private double gameScreenLimitBlock;
    private int paddleSpeed = 10; // default value unless set otherwise.
    private KeyboardSensor keyboard;

    /**
     * sets this Paddle shape to given Rectangle.
     *
     * @param paddle - this paddle Rectangle shape.
     * @param kBoard - this paddle Keyboard Sensor to use when moving.
     */
    public Paddle(Rectangle paddle, KeyboardSensor kBoard) {
        setPaddleShape(paddle);
        this.keyboard = kBoard;
    }

    /**
     * sets this Paddle shape by given rectangle parameters starting point (upperLeft) and its width,height.
     *
     * @param upperLeft - this Paddle shape (rectangle) starting point,the upper left point.
     * @param width - this Paddle shape (rectangle) width.
     * @param height - this Paddle shape (rectangle) height.
     * @param kBoard - this paddle Keyboard Sensor to use when moving.
     */
    public Paddle(Point upperLeft, double width, double height, KeyboardSensor kBoard) {
        this(new Rectangle(upperLeft, width, height), kBoard);
    }

    /**
     * sets this Paddle shape by given rectangle parameters starting point (upperLeft) and end point (underRight).
     *
     * @param upperLeft - this Paddle shape (rectangle) starting point,the upper left point.
     * @param underRight - this Paddle shape (rectangle) end point,the under right point.
     * @param kBoard - this paddle Keyboard Sensor to use when moving.
     */
    public Paddle(Point upperLeft, Point underRight, KeyboardSensor kBoard) {
        this(new Rectangle(upperLeft, underRight), kBoard);
    }

    /**
     * sets this Paddle shape by given rectangle parameters x1,y1 (upperLeft point) and x2,y2 (underRight point).
     * example - Paddle(50,50,200,200) will be a Paddle that start at upperLeft(50,50) and ends at underRight(250,250).
     *
     * @param x1 - this Paddle shape (rectangle) starting point,the upper left point x parameter.
     * @param y1 - this Paddle shape (rectangle) starting point,the upper left point y parameter.
     * @param x2 - this Paddle shape (rectangle) end point,the under right point x parameter.
     * @param y2 - this Paddle shape (rectangle) end point,the under right point y parameter.
     * @param kBoard - this paddle Keyboard Sensor to use when moving.
     */
    public Paddle(double x1, double y1, double x2, double y2, KeyboardSensor kBoard) {
        this(new Rectangle(x1, y1, x2, y2), kBoard);
    }

    /**
     * set this paddle moving speed.
     *
     * @param speed - the desired paddle moving speed.
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * set this paddle shape (Rectangle) and its color (black).
     *
     * @param paddleBlock - the Block to be this paddle shape.
     */
    public void setPaddleShape(Rectangle paddleBlock) {
        this.paddleShape = paddleBlock;
        this.paddleShape.setColor(Color.ORANGE);
    }

    /**
     * checks which key was pressed (RIGHT/LEFT) and move this paddle accordingly. enter - enlarge paddle width by
     * 5,return -reduce paddle width by 5. P.S: uncomment the needed section to allow this paddle the ability to move
     * UP/DOWN.
     */
    public void move() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        // return-reduce paddle width, enter-enlarge paddle width.
        if (this.keyboard.isPressed(KeyboardSensor.RETURN_KEY)) {
            reducePaddleSize();
        }
        if (this.keyboard.isPressed(KeyboardSensor.ENTER_KEY)) {
            enlargePaddleSize();
        }
        // uncomment this section to allow this paddle up/down movement.
        /*
         * // down and up is for for more mobility when testing or for future use. if
         * (this.keyboard.isPressed(KeyboardSensor.DOWN_KEY)) { moveDown(); } if
         * (this.keyboard.isPressed(KeyboardSensor.UP_KEY)) { moveUp(); }
         */
    }

    /** if the return key(backspace) was pressed reduce the paddle width by 5,minimum paddle width is 100. */
    public void reducePaddleSize() {
        Rectangle rect = this.getCollisionRectangle();
        if ((rect.getUpperLeft().getX() + rect.getWidth()) > rect.getUpperLeft().getX() + 100) {
            setPaddleShape(new Rectangle(rect.getUpperLeft(), rect.getWidth() - 5, rect.getHeight()));
        }
    }

    /** if the enter key was pressed enlarge the paddle width by 5,maximum paddle width is the game screen width. */
    public void enlargePaddleSize() {
        Rectangle rect = this.getCollisionRectangle();
        if (this.gameScreenWidth - this.gameScreenLimitBlock > rect.getUpperLeft().getX() + rect.getWidth()) {
            setPaddleShape(new Rectangle(rect.getUpperLeft(), rect.getWidth() + 5, rect.getHeight()));
        }
    }

    /**
     * if the left key was pressed ,moves this paddle left at this paddle moving speed and make sure its not leaving the
     * screen limits.
     */
    public void moveLeft() {
        Rectangle rect = this.getCollisionRectangle();
        Point newUpperLeftP = null;
        /*
         * makes sure that the paddle can reach the end of the screen, if screenWidth/speed is not a round number then
         * we lower the speed at that last section (the reminder) to 1 so we can reach the edge.
         */
        if ((rect.getUpperLeft().getX() - this.paddleSpeed) >= this.gameScreenLimitBlock) {
            newUpperLeftP = new Point(rect.getUpperLeft().getX() - this.paddleSpeed, rect.getUpperLeft().getY());
        } else {
            newUpperLeftP = new Point(rect.getUpperLeft().getX() - 1, rect.getUpperLeft().getY());
        }
        // makes sure when paddle moves left he wont leave screen limits.
        if (this.gameScreenLimitBlock <= newUpperLeftP.getX()) {
            setPaddleShape(new Rectangle(newUpperLeftP, rect.getWidth(), rect.getHeight()));
        }
    }

    /**
     * if the right key was pressed ,moves this paddle right at this paddle moving speed and make sure its not leaving
     * the screen limits.
     */
    public void moveRight() {
        Rectangle rect = this.getCollisionRectangle();
        Point newUpperLeftP = null;
        /*
         * makes sure that the paddle can reach the end of the screen, if screenWidth/speed is not a round number then
         * we lower the speed at that last section (the reminder) to 1 so we can reach the edge.
         */
        if ((this.gameScreenWidth - this.gameScreenLimitBlock) >= (rect.getWidth() + rect.getUpperLeft().getX()
                + this.paddleSpeed)) {
            newUpperLeftP = new Point(rect.getUpperLeft().getX() + this.paddleSpeed, rect.getUpperLeft().getY());
        } else {
            newUpperLeftP = new Point(rect.getUpperLeft().getX() + 1, rect.getUpperLeft().getY());
        }
        // makes sure when paddle moves right he wont leave screen limits.
        if ((this.gameScreenWidth - this.gameScreenLimitBlock) >= (rect.getWidth() + newUpperLeftP.getX())) {
            setPaddleShape(new Rectangle(newUpperLeftP, rect.getWidth(), rect.getHeight()));
        }
    }

    /**
     * if the up key was pressed ,moves this paddle up at this paddle moving speed and make sure its not leaving the
     * screen limits.
     */
    public void moveUp() {
        Rectangle rect = this.getCollisionRectangle();
        Point newUpperLeftP = null;
        /*
         * makes sure that the paddle can reach the end of the screen, if screenWidth/speed is not a round number then
         * we lower the speed at that last section (the reminder) to 1 so we can reach the edge.
         */
        if (this.gameScreenLimitBlock <= rect.getUpperLeft().getY() - this.paddleSpeed) {
            newUpperLeftP = new Point(rect.getUpperLeft().getX(), rect.getUpperLeft().getY() - this.paddleSpeed);
        } else {
            newUpperLeftP = new Point(rect.getUpperLeft().getX(), rect.getUpperLeft().getY() - 1);
        }
        // makes sure when paddle moves up he wont leave screen limits.
        if (this.gameScreenLimitBlock <= newUpperLeftP.getY()) {
            setPaddleShape(new Rectangle(newUpperLeftP, rect.getWidth(), rect.getHeight()));
        }
    }

    /**
     * if the down key was pressed ,moves this paddle down at this paddle moving speed and make sure its not leaving the
     * screen limits.
     */
    public void moveDown() {
        Rectangle rect = this.getCollisionRectangle();
        Point newUpperLeftP = null;
        /*
         * makes sure that the paddle can reach the end of the screen, if screenWidth/speed is not a round number then
         * we lower the speed at that last section (the reminder) to 1 so we can reach the edge.
         */
        if (this.gameScreenHeight - this.gameScreenLimitBlock >= rect.getUpperLeft().getY() + rect.getHeight()
                + this.paddleSpeed) {
            newUpperLeftP = new Point(rect.getUpperLeft().getX(), rect.getUpperLeft().getY() + this.paddleSpeed);
        } else {
            newUpperLeftP = new Point(rect.getUpperLeft().getX(), rect.getUpperLeft().getY() + 1);
        }
        // makes sure when paddle moves down he wont leave screen limits.
        if (this.gameScreenHeight - this.gameScreenLimitBlock >= newUpperLeftP.getY() + rect.getHeight()) {
            setPaddleShape(new Rectangle(newUpperLeftP, rect.getWidth(), rect.getHeight()));
        }
    }

    /************************************** Collidable interface methods **********************************************/

    /**
     * @return the "collision shape" of the object paddle (Rectangle).
     */
    public Rectangle getCollisionRectangle() {
        return this.paddleShape;
    }

    /**
     * Notify the object (paddle) that we collided with it at collisionPoint with the given velocity(currentVelocity).
     * this paddle is divided to 5 equal regions that if hit will return a different velocity(based on a angle). region
     * 1 is the leftmost 1/5,region 3 is the middle,region 5 is the rightmost 1/5 of this paddle width. region 1 angle =
     * 300|region 2 angle = 330|region 3 angle = 0|region 4 angle = 30|region 5 angle = 60.
     *
     * @param collisionPoint - the collision point at which an object (ball) collided with this collidable object
     *            (paddle).
     * @param currentVelocity - the velocity at which an object (ball) collided with this collidable object (paddle).
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double paddleWidth = this.getCollisionRectangle().getWidth();
        Point paddleTopLeftP = this.getCollisionRectangle().getUpperLeft();
        Point endPRegion1 = new Point(paddleTopLeftP.getX() + (paddleWidth / 5), paddleTopLeftP.getY());
        Point endPRegion2 = new Point(endPRegion1.getX() + (paddleWidth / 5), paddleTopLeftP.getY());
        Point endPRegion3 = new Point(endPRegion2.getX() + (paddleWidth / 5), paddleTopLeftP.getY());
        Point endPRegion4 = new Point(endPRegion3.getX() + (paddleWidth / 5), paddleTopLeftP.getY());
        Point endPRegion5 = new Point(endPRegion4.getX() + (paddleWidth / 5), paddleTopLeftP.getY());
        Line region1 = new Line(paddleTopLeftP, endPRegion1);
        Line region2 = new Line(endPRegion1, endPRegion2);
        Line region3 = new Line(endPRegion2, endPRegion3);
        Line region4 = new Line(endPRegion3, endPRegion4);
        Line region5 = new Line(endPRegion4, endPRegion5);
        double speed = currentVelocity.getSpeed();
        if (region1.pointInRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(300, speed);
        }
        if (region2.pointInRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(330, speed);
        }
        if (region3.pointInRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(0, speed);
        }
        if (region4.pointInRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(30, speed);
        }
        if (region5.pointInRange(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(60, speed);
        }
        // if the paddle wasn't hit at the top then return currentVelocity with -dx || -dy based on where it hit.
        double dX = currentVelocity.getDx();
        double dY = currentVelocity.getDy();
        if ((this.paddleShape.getLeftLine().pointInRange(collisionPoint))
                || (this.paddleShape.getRightLine().pointInRange(collisionPoint))) {
            dX = -dX;
        }
        if (this.paddleShape.getUnderLine().pointInRange(collisionPoint)) {
            dY = -dY;
        }
        return new Velocity(dX, dY);
    }

    /************************************** Sprite interface methods *************************************************/

    /**
     * draw this paddle (Rectangle) on the given DrawSurface.
     *
     * @param d - the drawing surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.paddleShape.getColor());
        int startX = (int) this.paddleShape.getUpperLeft().getX();
        int startY = (int) this.paddleShape.getUpperLeft().getY();
        int endX = (int) this.paddleShape.getWidth();
        int endY = (int) this.paddleShape.getHeight();
        d.fillRectangle(startX, startY, endX, endY);
    }

    /**
     * notify the sprite(paddle) that time has passed and invoke a certain change(move the paddle if keys were pressed).
     */
    public void timePassed() {
        move();
    }

    /**
     * Add this paddle to the game.
     *
     * @param g - the Game to add this paddle(player) to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
        this.gameScreenLimitBlock = g.getScreenLimitBlock();
        this.gameScreenWidth = g.getScreenWidth();
        this.gameScreenHeight = g.getScreenHeight();
    }

}
