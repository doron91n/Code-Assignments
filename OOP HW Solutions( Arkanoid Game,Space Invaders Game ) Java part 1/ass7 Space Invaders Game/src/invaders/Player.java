
package invaders;

import java.util.ArrayList;
import java.util.List;

import game.Velocity;
import geometry.Point;
import sprite.Ball;
import sprite.Paddle;

/**
 * a player class.
 *
 * 
 */
public class Player extends Paddle {
    private Shot playerShot;
    private List<Shot> shotsList = new ArrayList<Shot>();
    private boolean playerWasHit = false;

    /**
     * player constructor.
     *
     * @param p - the paddle to create a player with.
     * @param shot - the player shot.
     */
    public Player(Paddle p, Shot shot) {
        super(p.getCollisionRectangle(), p.getKeyboard(), p.getSpeed());
        setGame(p.getGame());
        this.playerShot = new Shot(shot);
    }

    /**
     * @return true if this player(paddle) was hit, false otherwise.
     */
    public boolean getPlayerWasHit() {
        return this.playerWasHit;
    }

    /** reset this paddle position to the bottom middle of the screen. */
    @Override
    public void resetPaddle() {
        super.resetPaddle();
        this.playerWasHit = false;
        clearShots();
    }

    /**
     * removes all player created shots from the game.
     */
    public void clearShots() {
        for (Shot s : new ArrayList<Shot>(this.shotsList)) {
            if (s != null) {
                s.removeFromGame(getGame());
                this.shotsList.remove(s);
            }
        }
        this.shotsList = new ArrayList<Shot>();
    }

    /**
     * adds a new shot to the game and this player shots list with a position - the middle of the player.
     */
    public void shoot() {
        this.playerShot.setCenterP(new Point(getTopMidP().getX(), getTopMidP().getY() - 5));
        Shot s = new Shot(this.playerShot);
        this.shotsList.add(s);
        s.addToGame(getGame());
    }

    /**
     * Notify the object (paddle) that we collided with it at collisionPoint with the given velocity(currentVelocity).
     * this paddle is divided to 5 equal regions that if hit will return a different velocity(based on a angle). region
     * 1 is the leftmost 1/5,region 3 is the middle,region 5 is the rightmost 1/5 of this paddle width. region 1 angle =
     * 300|region 2 angle = 330|region 3 angle = 0|region 4 angle = 30|region 5 angle = 60.
     *
     * @param collisionPoint - the collision point at which an object (ball) collided with this collidable object
     *            (paddle).
     * @param currentVelocity - the velocity at which an object (ball) collided with this collidable object (paddle).
     * @param hitter - the Ball that's doing the hitting on this Collidable object.
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity v = super.hit(hitter, collisionPoint, currentVelocity);
        this.playerWasHit = true;
        hitter.removeFromGame(getGame());
        return v;
    }

}
