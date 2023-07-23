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
    private LevelInformation levelInfo;
    private KeyboardSensor keyboard;
    private int levelBlocksCreated = 0;

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
        this.screenWidth = this.runner.getScreenWidth();
        this.screenHeight = this.runner.getScreenHeight();
        this.screenLimitBlock = this.runner.getScreenLimitBlock();
        this.keyboard = k;
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
     * add a new sprite to this game level sprite list.
     *
     * @param s - the new sprite to be added to the sprite list.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * remove the given sprite from this game level sprite list.
     *
     * @param s - the sprite to be removed from this Game sprite list.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
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
     *
     * @param ballRemover - removes balls that hit the dead region(below the player-paddle).
     */
    public void addScreenLimitBlocks(BallRemover ballRemover) {
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
        deadRegionBlock.addHitListener(ballRemover);
    }

    /**
     * create and add blocks in a certain pattern to this level.
     *
     * @param scoreTracker - in charge of keeping game score, each block hit is 5 p and block destruction is 10 p.
     * @param blockRemover - in charge of removing blocks and keep count of how many are left in the game.
     */
    public void createBlocks(BlockRemover blockRemover, ScoreTrackingListener scoreTracker) {
        List<Block> blockList = this.levelInfo.blocks();
        for (Block block : blockList) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTracker);
            getGameBlockCounter().increase(1);
            this.levelBlocksCreated++;
        }
    }

    /**
     * create and add the paddle (player) to this game - will be in the middle bottom of the screen.
     *
     * @return paddle - the newly created paddle (player).
     */
    public Paddle createPaddle() {
        double paddleWidth = this.levelInfo.paddleWidth();
        int paddleSpeed = this.levelInfo.paddleSpeed();
        double paddleHeight = 20;
        Point paddleStartPoint = new Point((this.screenWidth - paddleWidth) / 2, this.screenHeight - 10 - paddleHeight);
        Paddle paddle = new Paddle(new Rectangle(paddleStartPoint, paddleWidth, paddleHeight), this.getKeyBoard(),
                paddleSpeed);
        paddle.addToGame(this);
        return paddle;
    }

    /**
     * create and add balls to this game level.
     */
    public void createBalls() {
        int n = this.levelInfo.numberOfBalls();
        int ballRadius = 8;
        int i = 0;
        Color ballColor = Color.LIGHT_GRAY;
        List<Velocity> velocityList = this.levelInfo.initialBallVelocities();
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
        getGameBlockToClearCounter().increase(this.levelInfo.numberOfBlocksToRemove());
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
        LevelIndicator levelInd = new LevelIndicator(this.levelInfo.levelName(), levelNameRect);
        BlocksIndicator blocksNum = new BlocksIndicator(getGameBlockToClearCounter(), blocksRect);
        BlockRemover blockRemover = new BlockRemover(this, getGameBlockCounter(), getRemovedBlocksNumCounter(),
                getGameBlockToClearCounter());
        BallRemover ballRemover = new BallRemover(this, getGameBallCounter());
        // add level background
        try {
            this.addSprite(this.levelInfo.getBackground());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Failed to load level background.");
        }

        // create this game blocks in a certain pattern and this game screen limit blocks.
        addScreenLimitBlocks(ballRemover);
        createBlocks(blockRemover, scoreTracker);
        scoreInd.addToGame(this);
        livesInd.addToGame(this);
        levelInd.addToGame(this);
        blocksNum.addToGame(this);

    }

    /**
     * Run the level - start the animation loop,will run until level is cleared or player is out of lives.
     */
    public void run() {
        // create and add paddle to the game.
        Paddle paddle = createPaddle();
        do {
            // plays one turn for this level.
            playOneTurn(paddle);
            // if all balls were lost in the oneTurn then player looses 1 life.
            if ((getPlayerLivesCounter().getValue() > 0) && (getGameBallCounter().getValue() == 0)) {
                getPlayerLivesCounter().decrease(1);
            }
            // if all created blocks were destroyed then stop playing level.
            if (this.levelBlocksCreated == this.getRemovedBlocksNumCounter().getValue()) {
                break;
            }
        } while ((getPlayerLivesCounter().getValue() != 0)
                && (this.levelInfo.numberOfBlocksToRemove() != this.getRemovedBlocksNumCounter().getValue()));
    }

    /**
     * plays this level once, every new turn starts with a x seconds delay and the balls and paddle are reset.
     *
     * @param paddle - the game level paddle (player).
     */
    public void playOneTurn(Paddle paddle) {
        // reset paddle(player) position.
        paddle.resetPaddle();
        // create and add number of balls to the game.
        this.createBalls();
        // count-down before turn starts count start at 3 and last for 2 seconds.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites, this.screenColor));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        // upon removing all needed number of blocks to clear the level, player get 100 points to his score.
        if (this.levelInfo.numberOfBlocksToRemove() == this.getRemovedBlocksNumCounter().getValue()) {
            getGameScoreCounter().increase(100);
            this.running = false;
        }
        // the animation stopping conditions: out of balls | out of lives | out of blocks.
        if ((getGameBlockCounter().getValue() == 0) || (getPlayerLivesCounter().getValue() == 0)
                || (getGameBallCounter().getValue() == 0)) {
            this.running = false;
        }
        // pause the game if P is pressed.
        if (this.getKeyBoard().isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.getKeyBoard(), "space", new PauseScreen(this.sprites)));
        }
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
