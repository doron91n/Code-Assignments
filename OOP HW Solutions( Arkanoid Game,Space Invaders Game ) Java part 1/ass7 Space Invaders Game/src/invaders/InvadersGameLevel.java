
package invaders;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import animation.AnimationRunner;
import animation.GameLevel;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Counter;
import game.ScoreTrackingListener;
import level.LevelInformation;
import sprite.Block;

/**
 * a InvadersGameLevel class.
 *
 */
public class InvadersGameLevel extends GameLevel {
    private Formation enemyFormation;
    private Player player;
    private String waveNum = "";
    private double enemyFormationInitialSpeed;
    private double playerShotCoolDown = 0;

    /**
     * sets this game level with given Level Information.
     *
     * @param levelInformation - this game level needed information for construction.
     * @param animationRunner - the game level animation runner, runs the level.
     * @param gameScore - a counter for the total game score,carries over different levels.
     * @param playerLives - a counter for the total player remaining lives,carries over different levels.
     * @param k - the animation keyboard.
     * @param wave - the number of waves (levels) the player already beat+1.
     * @param initialSpeed - the enemy formation initial move Speed.
     */
    public InvadersGameLevel(LevelInformation levelInformation, AnimationRunner animationRunner, Counter gameScore,
            Counter playerLives, KeyboardSensor k, int wave, double initialSpeed) {
        super(levelInformation, animationRunner, gameScore, playerLives, k);
        this.waveNum = " " + wave;
        this.enemyFormationInitialSpeed = initialSpeed;
        setRemovesBallsHittintWalls();
    }

    /**
     * @return this Invaders Game Level enemy formation.
     */
    public Formation getFormation() {
        return this.enemyFormation;
    }

    /**
     * @return a string representing the wave number the player is playing.
     */
    @Override
    public String wave() {
        return this.waveNum;
    }

    /**
     * not creating balls.
     */
    @Override
    public void createBalls() {
        ;
    }

    /**
     * create and add the paddle (player) to this game - will be in the middle bottom of the screen.
     */
    @Override
    public void createPaddle() {
        super.createPaddle();
        Shot playerShot = new Shot(super.getPlayer().getTopMidP(), 3, Color.blue, false);
        this.player = new Player(super.getPlayer(), playerShot);
    }

    /**
     * @return this Invaders Game Level player (paddle).
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * resets the player shot time and make the player shoot.
     */
    public void playerShoot() {
        this.playerShotCoolDown = 0.35;
        getPlayer().shoot();
    }

    /**
     * create and add blocks in a certain pattern to this level.
     *
     * @param scoreTracker - in charge of keeping game score.
     */
    @Override
    public void createBlocks(ScoreTrackingListener scoreTracker) {
        List<Block> blockList = getLevelInfo().blocks();
        List<Block> shieldList = new Shields().createShields();
        // sorts the list of blocks to columns.
        Collections.sort(blockList, new Comparator<Block>() {
            @Override
            public int compare(Block block1, Block block2) {
                if (block1.getTopLeftX() > block2.getTopLeftX()) {
                    return 1;
                }
                if ((block1.getTopLeftX() == block2.getTopLeftX())) {
                    return 0;
                }
                // if (block1.getTopLeftX()< block2.getTopLeftX())
                return -1;
            }
        });
        this.enemyFormation = new Formation(this, blockList, scoreTracker, this.enemyFormationInitialSpeed);
        for (Block shield : shieldList) {
            shield.addToGame(this);
            shield.addHitListener(getBlockRemover());
            shield.addHitListener(getBallRemover());
        }
    }

    /**
     * Run the level - start the animation loop,will run until level is cleared or player is out of lives.
     */
    @Override
    public void run() {
        // create and add paddle to the game.
        do {
            resetLevel();
            // plays one turn for this level.
            playOneTurn();
        } while ((getPlayerLivesCounter().getValue() != 0)
                && (getLevelInfo().numberOfBlocksToRemove() != getRemovedBlocksNumCounter().getValue()));
    }

    /**
     * resets the level upon ending a turn,enemy formation returns to start position,and restarts the enemy random
     * column shoot timer.
     */
    public void resetLevel() {
        getPlayer().resetPaddle();
        getFormation().resetFormationPos();
        getFormation().startShotsTimer();
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.playerShotCoolDown -= dt;
        getSprites().drawAllOn(d);
        getSprites().notifyAllTimePassed(dt);
        getFormation().moveFormation();
        if ((getKeyBoard().isPressed("space")) && (this.playerShotCoolDown < 0.0D)) {
            playerShoot();
        }
        if (getFormation().reachedShields() || getPlayer().getPlayerWasHit()) {
            getPlayerLivesCounter().decrease(1);
            stopGame();
        }
        // upon removing all needed number of aliens to clear the level.
        if (getLevelInfo().numberOfBlocksToRemove() == getRemovedBlocksNumCounter().getValue()) {
            stopGame();
        }
        // the animation stopping conditions: out of lives | out of aliens.
        if ((getGameBlockCounter().getValue() == 0) || (getPlayerLivesCounter().getValue() == 0)) {
            stopGame();
        }
        // pause the game if P is pressed.
        if (this.getKeyBoard().isPressed("p")) {
            getRunner().run(new KeyPressStoppableAnimation(getKeyBoard(), "space", new PauseScreen(getSprites())));
        }
    }

    /**
     * stops the game animation run when the game stopping conditions are met.
     */
    @Override
    public void stopGame() {
        super.stopGame();
        getFormation().stopShotsTimer();
        resetLevel();
    }
}
