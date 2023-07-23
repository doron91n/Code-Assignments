
package menu;

import animation.Animation;

/**
 * a Menu interface.
 *
 * @param <T> - generic type.
 */
public interface Menu<T> extends Animation {
    /**
     * adds a new selection to the menu animation.
     *
     * @param key - the key to press in order to choose the menu selection.
     * @param message - the menu selection description to print.
     * @param returnVal - the returned object if this menu selection is selected.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return a object based on the selected menu selection.
     */
    T getStatus();

    /**
     * adds a sub menu selection to the menu animation.
     *
     * @param key - the key to press in order to choose the menu selection.
     * @param message - the menu selection description to print.
     * @param subMenu - the returned sub-menu if this menu selection is selected.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}