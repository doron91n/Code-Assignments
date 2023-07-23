package main;

import java.util.ArrayList;
import java.util.List;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import level.LevelFour;
import level.LevelInformation;
import level.LevelOne;
import level.LevelThree;
import level.LevelTwo;

/**
 * a Ass5Game class - assignment 5 main function (creates and runs the game).
 *
 */
public class Ass5Game {
    /**
     * the Ass5Game main - creates and runs the game levels.
     *
     * @param args - user entered arguments.
     */
    public static void main(String[] args) {
        Ass5Game g = new Ass5Game();
        g.runGame(args);
    }

    /**
     * the main function creates levelInformation list based on user entered arguments,if entered number is between 1 to
     * 4 then that level is entered into the level run list, if no valid input provided runs all 4 levels in order.
     *
     * @param args - user entered arguments, each number provided refers to a game level, valid levels are 1 to 4.
     */
    public void runGame(String[] args) {
        int screenWidth = 800;
        int screenHeight = 600;
        GUI gui = new GUI("Arkanoid", screenWidth, screenHeight);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(60, gui, screenWidth, screenHeight);
        GameFlow game = new GameFlow(runner, keyboard);
        List<LevelInformation> argsLevels = new ArrayList<LevelInformation>();
        // create a levelInformation list containing all valid levels numbers among given args.
        for (int i = 0; i < args.length; i++) {
            int x = 0;
            try {
                x = (int) Double.parseDouble(args[i]);
            } catch (Exception e) {
                ;
            }
            if (x == 1) {
                argsLevels.add(new LevelOne());
            }
            if (x == 2) {
                argsLevels.add(new LevelTwo());
            }
            if (x == 3) {
                argsLevels.add(new LevelThree());
            }
            if (x == 4) {
                argsLevels.add(new LevelFour());
            }
        }
        // create a levelInformation list containing all 4 levels.
        List<LevelInformation> allLevels = new ArrayList<LevelInformation>();
        allLevels.add(new LevelOne());
        allLevels.add(new LevelTwo());
        allLevels.add(new LevelThree());
        allLevels.add(new LevelFour());
        if (argsLevels.isEmpty()) {
            game.runLevels(allLevels);
        } else {
            game.runLevels(argsLevels);
        }
    }
}
