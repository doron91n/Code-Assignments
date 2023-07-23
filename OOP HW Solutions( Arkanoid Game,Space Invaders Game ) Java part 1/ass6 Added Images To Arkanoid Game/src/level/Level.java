package level;

import java.util.ArrayList;
import java.util.List;

import game.Velocity;
import sprite.Block;
import sprite.Sprite;

/**
 * Level class.
 *
 */
public class Level implements LevelInformation {
    private String levelName;
    private List<Velocity> ballVelocityList;
    private List<Block> blockList;
    private int ballsNum;
    private int paddleWidth;
    private int paddleSpeed;
    private int blocksToClear;
    private String background;

    /**
     * construct a new level with given parameters.
     *
     * @param name - the level name.
     * @param ballVel - a list with the level balls velocity's.
     * @param blocks - a list with the level blocks.
     * @param blockNum - the number of blocks needed to destroy in order to clear the level.
     * @param paddleWid - the level paddle width.
     * @param paddleSpeedx - the level paddle speed.
     * @param lvlBack - a string representing the level background,can be a solid color or an image.
     */
    public Level(String name, List<Velocity> ballVel, List<Block> blocks, int blockNum, int paddleWid, int paddleSpeedx,
            String lvlBack) {
        this.levelName = name;
        this.ballVelocityList = ballVel;
        this.blockList = blocks;
        this.blocksToClear = blockNum;
        this.paddleWidth = paddleWid;
        this.paddleSpeed = paddleSpeedx;
        this.ballsNum = ballVel.size();
        this.background = lvlBack;
    }

    /**
     * @return the number of balls created at this level.
     */
    @Override
    public int numberOfBalls() {
        return this.ballsNum;
    }

    /**
     * @return a list containing the initial velocity for each ball created in the level.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return new ArrayList<Velocity>(this.ballVelocityList);
    }

    /**
     * @return the paddle movement speed.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return the paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return the level name.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * @return a sprite with the background of the level.
     * @throws Exception - "Error: Failed to load level background".
     */
    @Override
    public Sprite getBackground() throws Exception {
        try {
            return new LevelBackground(this.background);
        } catch (Exception e) {
            throw new Exception("Error: Failed to load level background");
        }
    }

    /**
     * @return blockList - a list of The Blocks that make up this level, each block contains its size, color and
     *         location.
     */
    @Override
    public List<Block> blocks() {
        List<Block> copy = new ArrayList<Block>(this.blockList.size());
        for (Block block : this.blockList) {
            copy.add(new Block(block));
        }
        return copy;
    }

    /**
     * @return the number of blocks that should be removed to clear this level.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksToClear;
    }
}
