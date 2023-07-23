package invaders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import game.Counter;
import game.HighScoresTable;
import game.ScoreInfo;
import level.LevelInformation;
import menu.ShowHiScoresTask;

/**
 * a InvadersGameFlow class.
 *
 * 
 */
public class InvadersGameFlow {
    private Counter score = new Counter();
    private Counter playerLivesCount = new Counter();
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private HighScoresTable gameScores;
    private ShowHiScoresTask displayScores;
    private DialogManager dialogWindow;
    private int playerLives;

    /**
     * The InvadersGameFlow responsible for running all the game levels on the given Animation Runner given.
     *
     * @param ar - AnimationRunner runs each level animation.
     * @param k - the animation keyboard.
     * @param showScores - a task for displaying the game player high scores.
     * @param dialog - a DialogManager window to receive user input.
     * @param playerHp - the number of lives given to player.
     */
    public InvadersGameFlow(AnimationRunner ar, KeyboardSensor k, ShowHiScoresTask showScores, DialogManager dialog,
            int playerHp) {
        this.runner = ar;
        this.keyboard = k;
        this.gameScores = showScores.getScoresTable();
        this.displayScores = showScores;
        this.dialogWindow = dialog;
        this.playerLives = playerHp;
    }

    /**
     * runs all the levels inside the given levelInforamtion list.
     *
     * @param levels - the list containing all the game levels information.
     * @throws Exception - "Error: Failed to initialize level.".
     */
    public void runLevels(List<LevelInformation> levels) throws Exception {
        int lost = 0;
        this.playerLivesCount = new Counter();
        this.playerLivesCount.increase(this.playerLives);
        this.score = new Counter();
        // will run only if the player has lives left.
        if (this.playerLivesCount.getValue() > 0) {
            for (LevelInformation lvlInfo : levels) {
                double initialSpeed = 1.1;
                int waveNum = 1;
                do {
                    InvadersGameLevel level = new InvadersGameLevel(lvlInfo, this.runner, this.score,
                            this.playerLivesCount, this.keyboard, waveNum, initialSpeed);
                    try {
                        level.initialize();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("Error: Failed to initialize level.");
                    }
                    level.run();
                    // show the game over end screen when player is out of lives.
                    if (this.playerLivesCount.getValue() == 0) {
                        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space",
                                new EndScreen("Game Over", this.score.getValue())));
                        lost = 1;
                        break;
                    }
                    initialSpeed *= 1.2;
                    waveNum++;
                } while (this.playerLivesCount.getValue() > 0);
            }
        }
        // show the you win end screen when player finish playing and have remaining lives.
        if ((lost != 1) && (this.playerLivesCount.getValue() > 0)) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space",
                    new EndScreen("You Win!", this.score.getValue())));
        }
        if (this.gameScores.getRank(this.score.getValue()) <= this.gameScores.size()) {
            String name = this.dialogWindow.showQuestionDialog("Name", "What is your name?", "");
            this.gameScores.add(new ScoreInfo(name, this.score.getValue()));
            try {
                this.gameScores.save(new File("highscores"));
            } catch (IOException e) {
                ;
            }
        }
        this.displayScores.run();
    }
}
