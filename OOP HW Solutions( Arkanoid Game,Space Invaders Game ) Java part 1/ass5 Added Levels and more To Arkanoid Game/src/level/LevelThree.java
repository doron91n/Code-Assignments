package level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import background.Green3;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;
import sprite.Block;
import sprite.Sprite;

/**
 * Level Three class - Green3.
 */
public class LevelThree implements LevelInformation {
    private int ballsNum = 2;
    private int paddleSpeed = 20;
    private int paddleWidth = 200;
    private int blocksToClear = 34;
    private String name = "Green3";
    private int screenH = 600;
    private int screenW = 800;
    private int screenLimitBlock = 30;

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
        int angle = 60;
        List<Velocity> ballVelocityList = new ArrayList<Velocity>();
        for (int i = 0; i < this.ballsNum; i++) {
            ballVelocityList.add(Velocity.fromAngleAndSpeed(angle, 13));
            angle -= 120;
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
        return new Green3();
    }

    /**
     * @return blockList - a list of The Blocks that make up this level, each block contains its size, color and
     *         location.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blockList = createBlocksList(11);
        blockList.addAll(createBlocksList(10));
        blockList.addAll(createBlocksList(9));
        blockList.addAll(createBlocksList(8));
        return blockList;
    }

    /**
     * create and add blocks in a certain pattern to this level,each row will have the same random color.
     *
     * @param n - the number of blocks to be created on each row.
     * @return blockList - the level block list each block has his color,position,size,hit-points.
     */
    public List<Block> createBlocksList(int n) {
        List<Block> blockList = new ArrayList<Block>();
        // i needs to be the initial n + lastRowNumberOfBlocks
        int i = 18;
        // each small block size
        double blockHeight = 30;
        double blockWidth = 70;
        // first block in the row.
        double y = (n + 1) * blockHeight - 120;
        double x = this.screenW - this.screenLimitBlock - blockWidth;
        Color blockColor = generateRandomColor();
        while (i > n) {
            Point blockEnd = new Point(x + blockWidth, y + blockHeight);
            Point blockStart = new Point(x, y);
            // makes sure there are no blocks created outside of the screen limits.
            if ((blockEnd.getX() <= (this.screenW - this.screenLimitBlock))
                    && (blockStart.getX() >= this.screenLimitBlock)
                    && (blockEnd.getY() <= (this.screenH - this.screenLimitBlock))
                    && (blockStart.getY() >= this.screenLimitBlock)) {
                Block newBlock = new Block(new Rectangle(blockStart, blockWidth, blockHeight));
                newBlock.setColor(blockColor);
                // change the first row (the longest one) blockHP to 2 the rest are default to 1.
                if (n == 8) {
                    newBlock.setBlockHP(2);
                }
                x = x - blockWidth;
                blockList.add(newBlock);
            }
            i--;
        }
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
        } while (randColor.equals(Color.green));
        return randColor;
    }
}
