package level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import background.WideEasy;
import game.Velocity;
import sprite.Block;
import sprite.Sprite;

/**
 * Level Two class - Wide Easy.
 *
 */
public class LevelTwo implements LevelInformation {
    private int ballsNum = 10;
    private int paddleSpeed = 15;
    private int paddleWidth = 600;
    private int blocksToClear = 10;
    private String name = "Wide Easy";
    private int screenH = 600;
    private int screenW = 800;

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
        int angle = 180;
        for (int i = 0; i < this.ballsNum; i++) {
            ballVelocityList.add(Velocity.fromAngleAndSpeed(angle, 15));
            angle -= 40;
        }
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
        return new WideEasy();
    }

    /**
     * @return blockList - a list of The Blocks that make up this level, each block contains its size, color and
     *         location.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<Block>();
        int h = this.screenH;
        int w = this.screenW;
        int i = 30;
        int j = 0;
        Color col = generateRandomColor();
        do {
            Block block = new Block(i, (h / 2) - 10, (w - 60) / 10, 30);
            if (j % 2 == 0) {
                col = generateRandomColor();
            }
            block.setColor(col);
            i += (w - 60) / 10;
            j++;
            blockList.add(block);
        } while (i <= (w - 60));
        return blockList;
    }

    /**
     * @return the number of blocks that should be removed to clear this level.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksToClear;
    }

    /**
     * generates and returns a random color ,different from the screen color(the color we want to avoid when
     * generating the random color).
     *
     * @return randColor - the newly random generated color will be different from the given screen color.
     */
    public Color generateRandomColor() {
        Color randColor;
        do {
            randColor = new Color((int) (Math.random() * 0x1000000));
        } while (randColor.equals(Color.white));
        return randColor;
    }
}
