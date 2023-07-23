package main;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import animation.AnimationRunner;
import animation.MenuAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import construction.LevelSpecificationReader;
import game.HighScoresTable;
import invaders.InvadersGameFlow;
import level.LevelInformation;
import menu.Menu;
import menu.Task;

/**
 * a Ass7Game MainSpaceInvaders class - assignment 6 main function (creates and runs the game).
 *
 * 
 */
public class MainSpaceInvaders {
    /**
     * empty constructor.
     */
    public MainSpaceInvaders() {
    }

    /**
     * the main - creates and runs the game levels.
     *
     * @param args - user entered arguments.
     */
    public static void main(String[] args) {
        String levelPath = "space_invaders_level.txt";
        MainSpaceInvaders g = new MainSpaceInvaders();
        try {
            g.runGame(levelPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * creates the game.
     *
     * @param levelPath - the game level Path.
     * @throws Exception -"Error: Failed to create Level ".
     */
    public void runGame(String levelPath) throws Exception {
        int screenWidth = 800;
        int screenHeight = 600;
        double frameRate = 60;
        int playerHP = 3;
        String gameTitle = "Space-Invaders";
        GUI gui = new GUI(gameTitle, screenWidth, screenHeight);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        DialogManager dialog = gui.getDialogManager();
        AnimationRunner runner = new AnimationRunner(frameRate, gui, screenWidth, screenHeight);
        HighScoresTable h = HighScoresTable.loadFromFile(new File("highscores"));
        InvadersGameFlow game = new InvadersGameFlow(runner, keyboard, h.createShowScoresTask(runner, keyboard), dialog,
                playerHP);
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelPath);
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(gameTitle, keyboard, runner, false);
        try {
            Task<Void> runLevels = null;
            List<LevelInformation> levels = LevelSpecificationReader.fromReader(new InputStreamReader(is));
            if (!levels.isEmpty()) {
                runLevels = new Task<Void>() {
                    @Override
                    public Void run() {
                        try {
                            game.runLevels(levels);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }
            menu.addSelection("s", "Start New Game", runLevels);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Failed to create Level");
        } finally {
            if (is != null) {
                is.close();
            }
        }
        menu.addSelection("h", "Check High-Scores", h.createShowScoresTask(runner, keyboard));
        menu.addSelection("q", "Quit Game", new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        });
        while (true) {
            runner.run(menu);
            // wait for user selection
            menu.getStatus().run();
        }
    }
}
