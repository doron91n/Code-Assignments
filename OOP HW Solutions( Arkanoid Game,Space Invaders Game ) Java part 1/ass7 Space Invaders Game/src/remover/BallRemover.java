package remover;

import animation.GameLevel;
import collision.HitListener;
import game.Counter;
import sprite.Ball;
import sprite.Block;

/**
 * a BlockRemover class.
 *
 */

public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * BallRemover is in charge of removing balls from the game, as well as keeping count of the number of balls
     * that remain.
     *
     * @param gameX - the Game the ball belongs to.
     * @param remainingBallsNum - the number of remaining Balls in the game.
     */
    public BallRemover(GameLevel gameX, Counter remainingBallsNum) {
        this.game = gameX;
        this.remainingBalls = remainingBallsNum;
    }

    /**
     * @return - the Game the ball belongs to.
     */
    public GameLevel getGame() {
        return this.game;
    }

    /**
     * balls that hit the death region below the player (paddle) are removed from the game,removes this listener from
     * the removed ball.
     *
     * @param beingHit - the object (the death region) that being Hit by hitter(ball) .
     * @param hitter - the Ball that's doing the hitting on the beingHit object(the death region).
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(getGame());
        hitter.removeHitListener(this);
        remainingBalls.decrease(1);
    }

}
