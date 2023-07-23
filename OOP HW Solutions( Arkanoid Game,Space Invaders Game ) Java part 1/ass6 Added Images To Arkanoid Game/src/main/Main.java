package main;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import animation.AnimationRunner;
import animation.MenuAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import construction.LevelSetsReader;
import game.GameFlow;
import game.HighScoresTable;
import menu.Menu;
import menu.Task;

/**
 * a Ass6Game Main class - assignment 6 main function (creates and runs the game).
 *
 */
public class Main {
    /**
     * empty constructor.
     */
    public Main() {
    }

    /**
     * the main - creates and runs the game levels.
     *
     * @param args - user entered arguments.
     */
    public static void main(String[] args) {
        String levelSetsPath = "level_sets.txt";
        if (args.length > 0) {
            levelSetsPath = args[0];
        }
        Main g = new Main();
        try {
            g.runGame(levelSetsPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * creates the game.
     *
     * @param levelSetsPath - the game level Sets Path.
     * @throws Exception -"Error: Failed to create Sub-Menu with Level Sets".
     */
    public void runGame(String levelSetsPath) throws Exception {
        int screenWidth = 800;
        int screenHeight = 600;
        double frameRate = 60;
        String gameTitle = "Arkanoid";
        GUI gui = new GUI(gameTitle, screenWidth, screenHeight);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        DialogManager dialog = gui.getDialogManager();
        AnimationRunner runner = new AnimationRunner(frameRate, gui, screenWidth, screenHeight);
        HighScoresTable h = HighScoresTable.loadFromFile(new File("highscores"));
        GameFlow game = new GameFlow(runner, keyboard, h.createShowScoresTask(runner, keyboard), dialog);
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsPath);
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(gameTitle, keyboard, runner, false);
        try {
            LevelSetsReader.fromReader(new InputStreamReader(is), game, keyboard, runner, menu);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Failed to create Sub-Menu with Level Sets");
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
