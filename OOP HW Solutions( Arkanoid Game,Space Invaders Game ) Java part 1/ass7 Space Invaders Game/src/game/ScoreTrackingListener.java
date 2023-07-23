package game;

import collision.HitListener;
import sprite.Ball;
import sprite.Block;

/**
 * a ScoreTrackingListener class.
 *
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * the game Score counter, every time a block in the game is hit the player get 5 points and when the block is
     * destroyed the player get 15 points added to his game score (10 for destroying and 5 for the hit) .
     *
     * @param scoreCounter - the game score Counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * every time a block in the game is hit the player get 5 points and when the block is destroyed the player
     * get 15 points added to his game score (10 for destroying and 5 for the hit) .
     *
     * @param beingHit - the object that being Hit by hitter(ball).
     * @param hitter - the Ball that's doing the hitting on the beingHit object.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        int currentBlockHp = beingHit.getHitPoints();
        int destroyBlockValue = beingHit.getDestroyValue();
        int hitBlockValue = beingHit.getHitValue();
        if (currentBlockHp == 0) {
            this.currentScore.increase(destroyBlockValue);
        } else {
            this.currentScore.increase(hitBlockValue);
        }
    }
}