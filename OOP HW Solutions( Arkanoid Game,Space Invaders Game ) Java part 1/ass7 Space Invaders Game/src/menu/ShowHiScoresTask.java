
package menu;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import game.HighScoresTable;

/**
 * a ShowHiScoresTask class.
 *
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private HighScoresAnimation highScores;
    private KeyboardSensor keyboard;

    /**
     * shows the game high Scores Table.
     *
     * @param k - the animation keyboard.
     * @param ar - the animation runner to run the high scores table animation.
     * @param highScoresAnimation - the high scores animation showing the game high Scores table.
     */
    public ShowHiScoresTask(AnimationRunner ar, HighScoresAnimation highScoresAnimation, KeyboardSensor k) {
        this.runner = ar;
        this.highScores = highScoresAnimation;
        this.keyboard = k;

    }

    /**
     * runs the high scores animation showing the game high Scores table.
     *
     * @return null.
     */
    @Override
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", this.highScores));
        return null;
    }

    /**
     * @return this High Scores table.
     */
    public HighScoresTable getScoresTable() {
        return this.highScores.getScoresTable();
    }
}