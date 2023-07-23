package construction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;

import animation.AnimationRunner;
import animation.MenuAnimation;
import biuoop.KeyboardSensor;
import game.GameFlow;
import level.LevelInformation;
import menu.Menu;
import menu.Task;

/**
 */
public class LevelSetsReader {
    /**
     * reads and creates levels sets from level set file.
     *
     * @param reader - a file reader relative to path.
     * @param game - the game .
     * @param keyboard - the game keyboard sensor.
     * @param runner - the game animation runner.
     * @param menu - the game menu.
     * @throws Exception - "Error: Failed to create Level-Set , missing parameters".
     */
    public static void fromReader(Reader reader, GameFlow game, KeyboardSensor keyboard, AnimationRunner runner,
            Menu<Task<Void>> menu) throws Exception {
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("Level-Sets", keyboard, runner, true);
        String selectionKey = null;
        String selectionMessage = null;
        String selectionPath = null;
        InputStream is = null;
        LineNumberReader br = null;
        try {
            br = new LineNumberReader(reader);
            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if ((line.trim().indexOf('#') == 0) || (line.trim().isEmpty())) {
                    continue;
                }
                if (br.getLineNumber() % 2 == 1) {
                    String[] pair = line.trim().split(":");
                    if (pair.length == 2) {
                        selectionKey = pair[0];
                        selectionMessage = pair[1];
                        i++;
                    } else {
                        throw new Exception("Error: Failed to create Level-Set , missing parameters.");
                    }
                }
                if (br.getLineNumber() % 2 == 0) {
                    selectionPath = line.trim();
                    i++;
                }
                if ((i % 2 == 0) && i != 0) {
                    if ((selectionKey == null) || (selectionMessage == null) || selectionPath == null) {
                        throw new Exception("Error: Failed to create Level-Set , missing parameters.");
                    } else {
                        Task<Void> runLevels = null;
                        is = ClassLoader.getSystemClassLoader().getResourceAsStream(selectionPath);
                        if (is == null) {
                            throw new Exception("Error: Failed to find level set definitions file.");
                        }
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
                        subMenu.addSelection(selectionKey, selectionMessage, runLevels);
                    }
                    selectionKey = null;
                    selectionMessage = null;
                    selectionPath = null;
                }
            }
            menu.addSubMenu("s", "Start New Game", subMenu);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error: Failed to read level sets file.");
        } finally {
            if (br != null) {
                br.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }
}
