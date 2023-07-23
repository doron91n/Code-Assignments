package collision;

/**
 * a HitNotifier interface.
 *
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     *
     * @param hl - the object to be added as a listener to hit events.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl - the listener object to be removed from hit events.
     */
    void removeHitListener(HitListener hl);
}
