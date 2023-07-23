package menu;

/**
 * a MenuSelection class.
 *
 * @param <T> - generic type.
 */
public class MenuSelection<T> {
    private String selectionKey;
    private String selectionMessage;
    private T selectionVal;

    /**
     * creates a new menu selection.
     *
     * @param key - the key to press in order to choose this menu selection.
     * @param message - this menu selection description.
     * @param returnVal - the returned object for this menu selection.
     */
    public MenuSelection(String key, String message, T returnVal) {
        this.selectionKey = key;
        this.selectionMessage = message;
        this.selectionVal = returnVal;
    }

    /**
     * @return a key to press in order to choose this menu selection.
     */
    public String getSelectionKey() {
        return this.selectionKey;
    }

    /**
     * @return this menu selection description.
     */
    public String getSelectionMsg() {
        return this.selectionMessage;
    }

    /**
     * @return this menu selection value.
     */
    public T getSelectionVal() {
        return this.selectionVal;
    }
}
