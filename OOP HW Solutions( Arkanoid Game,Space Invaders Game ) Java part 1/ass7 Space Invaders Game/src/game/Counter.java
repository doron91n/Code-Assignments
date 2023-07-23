package game;

/**
 * a Counter class.
 *
 *
 */
public class Counter {
    private int count = 0; // default value.

    /**
     * add number to current count.
     *
     * @param number - the number to add to the current count.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number - the number to subtract from the current count.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * @return the current count.
     */
    public int getValue() {
        return this.count;
    }
}