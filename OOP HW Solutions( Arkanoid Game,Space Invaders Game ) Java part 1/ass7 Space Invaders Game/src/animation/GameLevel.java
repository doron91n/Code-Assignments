package animation;

import java.awt.Color;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import game.Counter;
import game.GameEnvironment;
import game.ScoreTrackingListener;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;
import indicator.BlocksIndicator;
import indicator.LevelIndicator;
import indicator.LivesIndicator;
import indicator.ScoreIndicator;
import level.LevelInformation;
import remover.BallRemover;
import remover.BlockRemover;
import sprite.Ball;
import sprite.Block;
import sprite.Paddle;
import sprite.Sprite;
import sprite.SpriteCollection;

/**
 * a GameLevel class.
 *
 * 
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private double screenWidth;
    private double screenHeight;
    // the screen blocks height for (top,under) screen limit and width for (right,left) screen limit.
    private double screenLimitBlock;
    private Color screenColor = Color.BLUE; // default value
    private Counter remainingBlocks = new Counter();
    private Counter blocksToClear = new Counter();
    private Counter removedBlocksNum = new Counter();
    private Counter remainingBalls = new Counter();
    private Counter score;
    private Counter playerLivesCount;
    private AnimationRunner runner;
    private boolean running;
    private boolean removesBallsHittintWalls = false;
    private LevelInformation levelInfo;
    private KeyboardSensor keyboard;
    private int levelBlocksCreated = 0;
    private BallRemover ballRemover;
    private BlockRemover blockRemover;
    private Paddle player;

    /**
     * sets this game level with given Level Information.
     *
     * @param levelInformation - this game level needed information for construction.
     * @param animationRunner - the game level animation runner, runs the level.
     * @param gameScore - a counter for the total game score,carries over different levels.
     * @param playerLives - a counter for the total player remaining lives,carries over different levels.
     * @param k - the animation keyboard.
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner animationRunner, Counter gameScore,
            Counter playerLives, KeyboardSensor k) {
        this.levelInfo = levelInformation;
        this.playerLivesCount = playerLives;
        this.score = gameScore;
        this.runner = animationRunner;
        this.screenWidth = getRunner().getScreenWidth();
        this.screenHeight = getRunner().getScreenHeight();
        this.screenLimitBlock = getRunner().getScreenLimitBlock();
        this.keyboard = k;
    }

    /**
     * @return this game level information.
     */
    public LevelInformation getLevelInfo() {
        return this.levelInfo;
    }

    /**
     * @return a string to be added to this level name description.
     */
    public String wave() {
        return "";
    }

    /**
     * @return this game level player (Paddle).
     */
    public Paddle getPlayer() {
        return this.player;
    }

    /**
     * @return this game level ball remover.
     */
    public BallRemover getBallRemover() {
        return this.ballRemover;
    }

    /**
     * @return this game level block remover.
     */
    public BlockRemover getBlockRemover() {
        return this.blockRemover;
    }

    /**
     * sets this game balls to be removed from the game upon hitting this game screen blocks.
     */
    public void setRemovesBallsHittintWalls() {
        this.removesBallsHittintWalls = true;
    }

    /**
     * @return true if every ball that hits the game screen blocks is removed false otherwise.
     */
    public boolean getRemovesBallsHittintWalls() {
        return this.removesBallsHittintWalls;
    }

    /**
     * @return this game level screen Width.
     */
    public double getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * @return this game level screen Height.
     */
    public double getScreenHeight() {
        return this.screenHeight;
    }

    /**
     * @return this game level screen Limit Block height for (top,under) screen limit and width for (right,left) screen
     *         limit.
     */
    public double getScreenLimitBlock() {
        return this.screenLimitBlock;
    }

    /**
     * add the given Collidable object (c) to this game level environment.
     *
     * @param c - the new Collidable object to be added to this Game Environment collidableList.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * remove the given Collidable object (c) from this game level environment.
     *
     * @param c - the Collidable object to be removed from this Game Environment collidableList.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * @return this game level animation runner.
     */
    public AnimationRunner getRunner() {
        return this.runner;
    }

    /**
     * @return this game level sprite collection.
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }

    /**
     * add a new sprite to this game level sprite list.
     *
     * @param s - the new sprite to be added to the sprite list.
     */
    public void addSprite(Sprite s) {
        getSprites().addSprite(s);
    }

    /**
     * remove the given sprite from this game level sprite list.
     *
     * @param s - the sprite to be removed from this Game sprite list.
     */
    public void removeSprite(Sprite s) {
        getSprites().removeSprite(s);
    }

    /**
     * @return this game level environment.
     */
    public GameEnvironment getGameEnvironment() {
        return this.environment;
    }

    /**
     * @return this game level Block Counter.
     */
    public Counter getGameBlockCounter() {
        return this.remainingBlocks;
    }

    /**
     * @return this game level Block to clear Counter.
     */
    public Counter getGameBlockToClearCounter() {
        return this.blocksToClear;
    }

    /**
     * @return this game level removed Blocks number Counter.
     */
    public Counter getRemovedBlocksNumCounter() {
        return this.removedBlocksNum;
    }

    /**
     * @return this game level Balls Counter.
     */
    public Counter getGameBallCounter() {
        return this.remainingBalls;
    }

    /**
     * @return this game score Counter.
     */
    public Counter getGameScoreCounter() {
        return this.score;
    }

    /**
     * @return this game Player Lives Counter.
     */
    public Counter getPlayerLivesCounter() {
        return this.playerLivesCount;
    }

    /**
     * @return the animation keyboard.
     */
    public KeyboardSensor getKeyBoard() {
        return this.keyboard;
    }

    /**
     * create the screen limit blocks (dynamically changes based on screen size) and add them to this game Collidables.
     */
    public void addScreenLimitBlocks() {
        Block topBlock = new Block(0, 0, this.screenWidth, this.screenLimitBlock + 15);
        Block rightBlock = new Block(this.screenWidth - this.screenLimitBlock, this.screenLimitBlock,
                this.screenLimitBlock, this.screenHeight - this.screenLimitBlock);
        Block leftBlock = new Block(0, this.screenLimitBlock, this.screenLimitBlock,
                this.screenHeight - this.screenLimitBlock);
        // dead region is a special block (placed beneath the paddle(player)) that if hit ,the ball hitting is removed.
        Block deadRegionBlock = new Block(0, this.screenHeight + 1, this.screenWidth, this.screenLimitBlock);
        topBlock.addToGame(this);
        rightBlock.addToGame(this);
        leftBlock.addToGame(this);
        deadRegionBlock.addToGame(this);
        deadRegionBlock.addHitListener(getBallRemover());
        if (getRemovesBallsHittintWalls()) {
            topBlock.addHitListener(getBallRemover());
            rightBlock.addHitListener(getBallRemover());
            leftBlock.addHitListener(getBallRemover());
        }
    }

    /**
     * create and add blocks in a certain pattern to this level.
     *
     * @param scoreTracker - in charge of keeping game score, each block hit is 5 p and block destruction is 10 p.
     */
    public void createBlocks(ScoreTrackingListener scoreTracker) {
        List<Block> blockList = getLevelInfo().blocks();
        for (Block block : blockList) {
            block.addToGame(this);
            block.addHitListener(getBlockRemover());
            block.addHitListener(scoreTracker);
            getGameBlockCounter().increase(1);
            this.levelBlocksCreated++;
        }
    }

    /**
     * create and add the paddle (player) to this game - will be in the middle bottom of the screen.
     */
    public void createPaddle() {
        double paddleWidth = getLevelInfo().paddleWidth();
        int paddleSpeed = getLevelInfo().paddleSpeed();
        double paddleHeight = 20;
        Point paddleStartPoint = new Point((this.screenWidth - paddleWidth) / 2, this.screenHeight - 10 - paddleHeight);
        Paddle paddle = new Paddle(new Rectangle(paddleStartPoint, paddleWidth, paddleHeight), this.getKeyBoard(),
                paddleSpeed);
        this.player = paddle;
    }

    /**
     * create and add balls to this game level.
     */
    public void createBalls() {
        int n = getLevelInfo().numberOfBalls();
        int ballRadius = 8;
        int i = 0;
        Color ballColor = Color.LIGHT_GRAY;
        List<Velocity> velocityList = getLevelInfo().initialBallVelocities();
        while (i < n) {
            // all balls are created in the middle of the screen above the paddle.
            Ball ball = new Ball(new Point(this.screenWidth / 2, this.screenHeight - 80), ballRadius, ballColor);
            getGameBallCounter().increase(1);
            ball.setVelocity(velocityList.get(i));
            ball.addToGame(this);
            i++;
        }
    }

    /**
     * Initialize a new game level: create the Blocks and the level background and add them to the game.
     *
     * @throws Exception -"Error: Failed to load level background."
     */
    public void initialize() throws Exception {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        getGameBlockToClearCounter().increase(getLevelInfo().numberOfBlocksToRemove());
        // add the game score,player remaining lives,level name to this game level.
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(getGameScoreCounter());
        Rectangle livesRect = new Rectangle(0, 0, this.screenWidth / 8, this.screenLimitBlock + 10);
        Rectangle blocksRect = new Rectangle(this.screenWidth / 8, 0, 50 + this.screenWidth / 8,
                this.screenLimitBlock + 10);
        Rectangle scoreRect = new Rectangle(50 + this.screenWidth / 4, 0, 50 + this.screenWidth / 8,
                this.screenLimitBlock + 10);
        Rectangle levelNameRect = new Rectangle(100 + (3 * this.screenWidth / 8), 0, 5 * this.screenWidth / 8,
                this.screenLimitBlock + 10);
        ScoreIndicator scoreInd = new ScoreIndicator(getGameScoreCounter(), scoreRect);
        LivesIndicator livesInd = new LivesIndicator(getPlayerLivesCounter(), livesRect);
        LevelIndicator levelInd = new LevelIndicator(getLevelInfo().levelName() + wave(), levelNameRect);
        BlocksIndicator blocksNum = new BlocksIndicator(getGameBlockToClearCounter(), blocksRect);
        this.blockRemover = new BlockRemover(this, getGameBlockCounter(), getRemovedBlocksNumCounter(),
                getGameBlockToClearCounter());
        this.ballRemover = new BallRemover(this, getGameBallCounter());
        // add level background
        try {
            this.addSprite(getLevelInfo().getBackground());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Failed to load level background.");
        }
        // create this game blocks in a certain pattern and this game screen limit blocks.
        addScreenLimitBlocks();
        createBlocks(scoreTracker);
        scoreInd.addToGame(this);
        livesInd.addToGame(this);
        levelInd.addToGame(this);
        blocksNum.addToGame(this);
        createPaddle();
        getPlayer().addToGame(this);

    }

    /**
     * @return the number of blocks created for this level.
     */
    public int getLevelBlocksCreated() {
        return this.levelBlocksCreated;
    }

    /**
     * Run the level - start the animation loop,will run until level is cleared or player is out of lives.
     */
    public void run() {
        do {
            // plays one turn for this level.
            playOneTurn();
            // if all balls were lost in the oneTurn then player looses 1 life.
            if ((getPlayerLivesCounter().getValue() > 0) && (getGameBallCounter().getValue() == 0)) {
                getPlayerLivesCounter().decrease(1);
            }
            // if all created blocks were destroyed then stop playing level.
            if (getLevelBlocksCreated() == getRemovedBlocksNumCounter().getValue()) {
                break;
            }
        } while ((getPlayerLivesCounter().getValue() != 0)
                && (getLevelInfo().numberOfBlocksToRemove() != getRemovedBlocksNumCounter().getValue()));
    }

    /**
     * plays this level once, every new turn starts with a x seconds delay and the balls and paddle are reset.
     */
    public void playOneTurn() {
        // reset paddle(player) position.
        getPlayer().resetPaddle();
        // create and add number of balls to the game.
        createBalls();
        // count-down before turn starts count start at 3 and last for 2 seconds.
        getRunner().run(new CountdownAnimation(2, 3, getSprites(), this.screenColor));
        this.running = true;
        getRunner().run(this);
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        getSprites().drawAllOn(d);
        getSprites().notifyAllTimePassed(dt);
        // upon removing all needed number of blocks to clear the level, player get 100 points to his score.
        if (getLevelInfo().numberOfBlocksToRemove() == this.getRemovedBlocksNumCounter().getValue()) {
            getGameScoreCounter().increase(100);
            stopGame();
        }
        // the animation stopping conditions: out of balls | out of lives | out of blocks.
        if ((getGameBlockCounter().getValue() == 0) || (getPlayerLivesCounter().getValue() == 0)
                || (getGameBallCounter().getValue() == 0)) {
            stopGame();
        }
        // pause the game if P is pressed.
        if (this.getKeyBoard().isPressed("p")) {
            getRunner().run(new KeyPressStoppableAnimation(this.getKeyBoard(), "space", new PauseScreen(getSprites())));
        }
    }

    /**
     * stops the game animation run when the game stopping conditions are met.
     */
    public void stopGame() {
        this.running = false;
    }

    /**
     * in charge of the game animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}
