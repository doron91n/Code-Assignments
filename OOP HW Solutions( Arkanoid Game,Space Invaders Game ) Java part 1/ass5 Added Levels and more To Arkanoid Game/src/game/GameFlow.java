package game;

import java.util.List;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.GameLevel;
import biuoop.KeyboardSensor;
import level.LevelInformation;

/**
 * a GameFlow class.
 *
 */
public class GameFlow {
    private Counter score = new Counter();
    private Counter playerLivesCount = new Counter();
    private AnimationRunner runner;
    private KeyboardSensor keyboard;

    /**
     * The GameFlow responsible for running all the game levels on the given Animation Runner given.
     *
     * @param ar - AnimationRunner runs each level animation.
     * @param k - the animation keyboard.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor k) {
        this.runner = ar;
        this.keyboard = k;
        // add player lives
        this.playerLivesCount.increase(7);
    }

    /**
     * runs all the levels inside the given levelInforamtion list.
     *
     * @param levels - the list containing all the game levels information.
     */
    public void runLevels(List<LevelInformation> levels) {
        int lost = 0;
        // will run only if the player has lives left.
        if (this.playerLivesCount.getValue() > 0) {
            for (LevelInformation lvlInfo : levels) {
                GameLevel level = new GameLevel(lvlInfo, this.runner, this.score, this.playerLivesCount, this.keyboard);
                level.initialize();
                level.run();
                // show the game over end screen when player is out of lives.
                if (this.playerLivesCount.getValue() == 0) {
                    this.runner.run(new EndScreen(this.keyboard, "Game Over. Your score is ", this.score.getValue()));
                    lost = 1;
                    break;
                }
            }
        }
        // show the you win end screen when player finish playing and have remaining lives.
        if ((lost != 1) && (this.playerLivesCount.getValue() > 0)) {
            this.runner.run(new EndScreen(this.keyboard, "You Win! Your score is ", this.score.getValue()));
        }
        this.runner.closeAnimation();
    }
}
