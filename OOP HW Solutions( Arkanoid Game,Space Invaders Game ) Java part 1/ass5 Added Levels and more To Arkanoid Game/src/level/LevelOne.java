package level;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import background.DirectHit;
import game.Velocity;
import sprite.Block;
import sprite.Sprite;

/**
 * Level one class - Direct Hit.
 *
 */
public class LevelOne implements LevelInformation {
    private int ballsNum = 1;
    private int paddleSpeed = 15;
    private int paddleWidth = 100;
    private int blocksToClear = 1;
    private String name = "Direct Hit";

    /**
     * @return the number of balls created at this level.
     */
    @Override
    public int numberOfBalls() {
        return this.ballsNum;
    }

    /**
     * @return a list containing the initial velocity for each ball created in the game.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballVelocityList = new ArrayList<Velocity>();
        ballVelocityList.add(Velocity.fromAngleAndSpeed(0, 10));
        return ballVelocityList;
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
        return this.name;
    }

    /**
     * @return a sprite with the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return new DirectHit();
    }

    /**
     * @return blockList - a list of The Blocks that make up this level, each block contains its size, color and
     *         location.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<Block>();
        Block block = new Block(380, 160, 40, 40);
        block.setColor(Color.red);
        blockList.add(block);
        return blockList;
    }

    /**
     * @return the number of blocks that should be removed to clear this level.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksToClear;
    }
}
