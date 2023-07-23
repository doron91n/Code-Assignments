
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * a Game class.
 *
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private double screenWidth = 1000; // default value
    private double screenHeight = 700; // default value
    // the screen blocks height for (top,under) screen limit and width for (right,left) screen limit.
    private double screenLimitBlock = 30; // default value
    private Color screenColor = Color.BLUE; // default value
    private GUI gui;

    /**
     * sets this game with given parameters.
     *
     * @param screenLimitB - this game screen limit blocks width for (right/left sides) and height for (top/bottom).
     * @param screenW - this game screen width.
     * @param screenH - this game screen height.
     * @param screenC - this game screen main color.
     */
    public Game(double screenLimitB, double screenW, double screenH, Color screenC) {
        this.screenColor = screenC;
        this.screenHeight = screenH;
        this.screenWidth = screenW;
        this.screenLimitBlock = screenLimitB;
    }

    /**
     * @return this game screen Width;
     */
    public double getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * @return this game screen Height;
     */
    public double getScreenHeight() {
        return this.screenHeight;
    }

    /**
     * @return this game screen Limit Block height for (top,under) screen limit and width for (right,left) screen limit
     */
    public double getScreenLimitBlock() {
        return this.screenLimitBlock;
    }

    /**
     * add the given Collidable object (c) to this game environment.
     *
     * @param c - the new Collidable object to be added to this Game Environment collidableList.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add a new sprite to this game sprite list.
     *
     * @param s - the new sprite to be added to the sprite list.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * @return this Game game environment;
     */
    public GameEnvironment getGameEnvironment() {
        return this.environment;
    }

    /**
     * @return this Game Gui ;
     */
    public GUI getGameGui() {
        return this.gui;
    }

    /**
     * generates and returns a random color ,different from the given screen color(the color we want to avoid when
     * generating the random color).
     *
     * @return randColor - the newly random generated color will be different from the given screen color.
     */
    public Color generateRandomColor() {
        Color randColor;
        do {
            randColor = new Color((int) (Math.random() * 0x1000000));
        } while (randColor.equals(this.screenColor));
        return randColor;
    }

    /**
     * Initialize a new game: create the Blocks,Balls,Paddle and add them to the game.
     */
    public void initialize() {
        gui = new GUI("Arkanoid", (int) this.screenWidth, (int) this.screenHeight);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        // create and add two balls to the sprite collection.
        int ballRadius = 10;
        Color ballColor = Color.black;
        Point ballCenter = new Point(400, 400);
        Point ballCenter2 = new Point(100, 200);
        Ball ball1 = new Ball(ballCenter, ballRadius, ballColor);
        Ball ball2 = new Ball(ballCenter2, ballRadius, ballColor);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(25, 12));
        ball2.setBallRandomVelocity();
        ball1.addToGame(this);
        ball2.addToGame(this);
        // create and add the paddle (player) to this game - will be in the middle bottom of the screen.
        double paddleWidth = 300;
        double paddleHeight = 20;
        Point paddleStartPoint = new Point((this.screenWidth - paddleWidth) / 2,
                this.screenHeight - screenLimitBlock - paddleHeight);
        Paddle paddle = new Paddle(new Rectangle(paddleStartPoint, paddleWidth, paddleHeight), keyboard);
        paddle.addToGame(this);
        // create this game blocks in a certain pattern and this game screen limit blocks.
        createBlocksList(11);
        addScreenLimitBlocks();

    }

    /**
     * create the screen limit blocks (dynamically changes based on screen size) and add them to this game Collidables.
     */
    public void addScreenLimitBlocks() {
        // create and add screen limit blocks will display "x" on them.
        Block topBlock = new Block(0, 0, this.screenWidth, screenLimitBlock);
        Block underBlock = new Block(0, this.screenHeight - screenLimitBlock, this.screenWidth, screenLimitBlock);
        Block rightBlock = new Block(this.screenWidth - screenLimitBlock, screenLimitBlock, screenLimitBlock,
                this.screenHeight - 2 * screenLimitBlock);
        Block leftBlock = new Block(0, screenLimitBlock, screenLimitBlock, this.screenHeight - 2 * screenLimitBlock);
        topBlock.addToGame(this);
        underBlock.addToGame(this);
        rightBlock.addToGame(this);
        leftBlock.addToGame(this);
        topBlock.setBlockHP(0);
        underBlock.setBlockHP(0);
        rightBlock.setBlockHP(0);
        leftBlock.setBlockHP(0);
    }

    /**
     * create and add blocks in a certain pattern to this game,each row will have the same random color.
     *
     * @param n - the number of blocks to be created on the first row.
     */
    public void createBlocksList(int n) {
        int lastRowNumberOfBlocks = 7;
        // i needs to be the initial n + lastRowNumberOfBlocks
        int i = 18;
        // each small block size
        double blockHeight = 30;
        double blockWidth = 70;
        // first block in the row.
        double y = (n + 1) * blockHeight - 120;
        double x = this.screenWidth - screenLimitBlock - blockWidth;
        Color blockColor = generateRandomColor();
        while (i > n) {
            Point blockEnd = new Point(x + blockWidth, y + blockHeight);
            Point blockStart = new Point(x, y);
            // makes sure there are no blocks created outside of the screen limits.
            if ((blockEnd.getX() <= (this.screenWidth - this.screenLimitBlock))
                    && (blockStart.getX() >= this.screenLimitBlock)
                    && (blockEnd.getY() <= (this.screenHeight - this.screenLimitBlock))
                    && (blockStart.getY() >= this.screenLimitBlock)) {
                Block newBlock = new Block(new Rectangle(blockStart, blockWidth, blockHeight));
                newBlock.setColor(blockColor);
                newBlock.addToGame(this);
                // change the first row (the longest one) blockHP to 2 the rest are default to 1.
                if (n == 7) {
                    newBlock.setBlockHP(2);
                }
                x = x - blockWidth;
            }
            i--;
        }
        if (n > lastRowNumberOfBlocks) {
            createBlocksList(n - 1);
        }
    }

    /**
     * Run the game - start the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            d.setColor(screenColor);
            d.fillRectangle(0, 0, (int) this.screenWidth, (int) this.screenHeight);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
