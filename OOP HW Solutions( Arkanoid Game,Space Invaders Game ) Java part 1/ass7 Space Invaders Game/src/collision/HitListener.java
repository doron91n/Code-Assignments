package collision;

import sprite.Ball;
import sprite.Block;

/**
 * a HitListener interface.
 *
 * 
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit - the object that being Hit by hitter(ball).
     * @param hitter - the Ball that's doing the hitting on the beingHit object.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
