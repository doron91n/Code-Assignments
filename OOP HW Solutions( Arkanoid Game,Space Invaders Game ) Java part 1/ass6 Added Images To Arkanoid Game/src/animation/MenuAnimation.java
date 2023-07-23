
package animation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import menu.Menu;
import menu.MenuSelection;
import menu.Task;

/**
 * a MenuAnimation class.
 *
 * @param <T> - generic type.
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<MenuSelection<T>> selectionList = new ArrayList<MenuSelection<T>>();
    private Map<String, Boolean> keyPressed = new HashMap<String, Boolean>();
    private String gameTitle;
    private T selectedTask;
    private String selectedTaskKey;
    private KeyboardSensor keyboard;
    private boolean stop = false;
    private AnimationRunner runner;
    private MenuAnimation<T> subMenu;
    private boolean hasSubMenu = false;
    private boolean isSubMenu = false;
    private boolean firstRun = true;

    /**
     * create a menu for the game.
     *
     * @param title - the game title.
     * @param k - a keyboard sensor.
     * @param runnerX - the game animation runner.
     * @param subMenu - true if this menu is a sub-menu false otherwise.
     */
    public MenuAnimation(String title, KeyboardSensor k, AnimationRunner runnerX, boolean subMenu) {
        this.gameTitle = title;
        this.keyboard = k;
        this.runner = runnerX;
        this.isSubMenu = subMenu;
    }

    /**
     * adds a new selection to the menu animation.
     *
     * @param key - the key to press in order to choose the menu selection.
     * @param message - the menu selection description to print.
     * @param returnVal - the returned object if this menu selection is selected.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addSelection(String key, String message, Object returnVal) {
        MenuSelection<T> selection = new MenuSelection<T>(key, message, (T) returnVal);
        this.selectionList.add(selection);
        this.keyPressed.put(key, false);
    }

    /**
     * adds a sub menu selection to the menu animation.
     *
     * @param key - the key to press in order to choose the menu selection.
     * @param message - the menu selection description to print.
     * @param subMenuX - the returned sub-menu if this menu selection is selected.
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenuX) {
        this.subMenu = (MenuAnimation<T>) subMenuX;
        this.hasSubMenu = true;
        Task<Void> runSubMenu = new Task<Void>() {
            @SuppressWarnings("unchecked")
            @Override
            public Void run() {
                runner.run(subMenu);
                // wait for user selection
                ((Task<Void>) subMenu.getStatus()).run();
                return null;
            }
        };
        this.addSelection(key, message, runSubMenu);
    }

    /**
     * @return a object based on the selected menu selection.
     */
    @Override
    public T getStatus() {
        this.stop = false;
        this.clicked(this.selectedTaskKey, false);
        return this.selectedTask;
    }

    /**
     * mark the key in the menu key lists as pressed/not pressed.
     *
     * @param key - the key to mark as pressed/not pressed.
     * @param val - the boolean value true = pressed , false = not pressed.
     */
    public void clicked(String key, boolean val) {
        this.keyPressed.put(key, val);
    }

    /**
     * in charge of the menu logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        List<MenuSelection<T>> l = this.selectionList;
        d.setColor(Color.white);
        d.drawText(80 + d.getWidth() / 4, 100, this.gameTitle, 70);
        d.setColor(Color.GREEN);
        d.drawText(80 + d.getWidth() / 4, 101, this.gameTitle, 70);
        String msg = null;
        String key = null;
        for (int i = 0; i < l.size(); i++) {
            msg = l.get(i).getSelectionMsg();
            key = l.get(i).getSelectionKey();
            String s = "(" + key + ")" + "     " + msg;
            d.setColor(Color.white);
            d.drawText(100, 175 + (i * 35), s, 25);
            d.setColor(Color.GREEN);
            d.drawText(100, 176 + (i * 35), s, 25);
            if (this.keyboard.isPressed(key)) {
                if (this.isSubMenu && this.firstRun) {
                    this.firstRun = false;
                }
                if (!this.keyPressed.get(key).booleanValue()) {
                    this.clicked(key, true);
                    if (this.hasSubMenu) {
                        this.subMenu.clicked(key, true);
                    }
                    this.selectedTask = l.get(i).getSelectionVal();
                    this.selectedTaskKey = key;
                    this.stop = true;
                }
            } else {
                this.clicked(key, false);
            }
        }

    }

    /**
     * in charge of the menu animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
