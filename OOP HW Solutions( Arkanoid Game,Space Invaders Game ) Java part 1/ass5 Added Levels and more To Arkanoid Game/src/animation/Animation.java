package animation;
import biuoop.DrawSurface;

/**
 * a Animation interface.
 *
 */
public interface Animation {
    /**
     * in charge of the game logic,perform one frame.
     *
     * @param d - the drawing surface.
     */
    void doOneFrame(DrawSurface d);

    /**
     * in charge of the game animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    boolean shouldStop();
}
