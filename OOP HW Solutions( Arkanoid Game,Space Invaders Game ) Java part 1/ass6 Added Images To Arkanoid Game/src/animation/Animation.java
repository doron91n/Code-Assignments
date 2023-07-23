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
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * in charge of the game animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    boolean shouldStop();
}
