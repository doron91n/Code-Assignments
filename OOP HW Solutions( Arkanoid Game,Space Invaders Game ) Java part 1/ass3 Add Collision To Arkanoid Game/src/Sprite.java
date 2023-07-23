
import biuoop.DrawSurface;

/**
 * a Sprite interface.
 *
 */
public interface Sprite {
    /**
     * draws the sprite to the given draw surface screen.
     *
     * @param d - the surface on which the sprite will be drawn.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed and invoke a certain change.
     */
    void timePassed();

    /**
     * adds this sprite to the given game (g).
     *
     * @param g - the game to add this sprite to.
     */
    void addToGame(Game g);
}
