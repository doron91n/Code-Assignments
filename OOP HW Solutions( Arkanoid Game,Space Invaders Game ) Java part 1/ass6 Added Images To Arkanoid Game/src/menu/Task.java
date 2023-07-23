
package menu;

/**
 * a Task interface.
 *
 * @param <T> - generic type.
 */

public interface Task<T> {
    /**
     * @return performs a specified number of actions (tasks) and if needed returns an object of the specified type.
     */
    T run();
}
