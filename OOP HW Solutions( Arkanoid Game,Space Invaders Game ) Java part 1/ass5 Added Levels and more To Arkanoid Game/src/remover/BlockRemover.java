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

public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocksNum;
    private Counter removedBlocksNum;

    /**
     * BlockRemover is in charge of removing blocks from the game, as well as keeping count of the number of blocks
     * that remain.
     *
     * @param gameX - the Game the block belongs to.
     * @param remainingBlocksNumber - the number of remaining Blocks in the game.
     * @param removedBlocksNumber - the number of Blocks removed from the game.
     */
    public BlockRemover(GameLevel gameX, Counter remainingBlocksNumber, Counter removedBlocksNumber) {
        this.game = gameX;
        this.remainingBlocksNum = remainingBlocksNumber;
        this.removedBlocksNum = removedBlocksNumber;
    }

    /**
     * @return - the Game the block belongs to.
     */
    public GameLevel getGame() {
        return this.game;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should are removed from the game. remove this listener from
     * the removed block.
     *
     * @param beingHit - the object that being Hit by hitter(ball).
     * @param hitter - the Ball that's doing the hitting on the beingHit object.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(getGame());
            beingHit.removeHitListener(this);
            remainingBlocksNum.decrease(1);
            removedBlocksNum.increase(1);
        }
    }
}
