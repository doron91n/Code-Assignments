package level;

import java.util.List;

import game.Velocity;
import sprite.Block;
import sprite.Sprite;

/**
 * a LevelInformation interface.
 *
 *
 */
public interface LevelInformation {
    /**
     * @return the number of balls created at this level.
     */
    int numberOfBalls();

    /**
     * @return a list containing the initial velocity for each ball created in the game.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return the paddle movement speed.
     */
    int paddleSpeed();

    /**
     * @return the paddle width.
     */
    int paddleWidth();

    /**
     * @return the level name.
     */
    String levelName();

    /**
     * @return a sprite with the background of the level.
     * @throws Exception - "Error: Failed to load level background".
     */
    Sprite getBackground() throws Exception;

    /**
     * @return a list of The Blocks that make up this level, each block contains its size, color and location.
     */
    List<Block> blocks();

    /**
     * @return the number of blocks that should be removed to clear this level.
     */
    int numberOfBlocksToRemove();
}
