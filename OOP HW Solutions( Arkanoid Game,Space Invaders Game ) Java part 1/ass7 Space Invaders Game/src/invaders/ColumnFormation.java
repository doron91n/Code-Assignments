
package invaders;

import java.util.LinkedList;
import java.util.List;

import animation.GameLevel;
import game.ScoreTrackingListener;
import geometry.Point;
import remover.BallRemover;
import remover.BlockRemover;
import sprite.Block;

/**
 * a ColumnFormation class.
 *
 * 
 */
public class ColumnFormation {
    private List<Enemy> enemyList = new LinkedList<Enemy>();
    private Point initialLeftP;
    private Point initialRightP;
    private GameLevel game;
    private BlockRemover blockRemover;
    private ScoreTrackingListener scoreTracker;
    private BallRemover ballRemover;
    private Formation formation;

    /**
     * construct a new empty column formation.
     *
     * @param form - the formation this column belong to.
     */
    public ColumnFormation(Formation form) {
        this.formation = form;
    }

    /**
     * construct a new column with given column.
     *
     * @param col - the column formation to copy to this column.
     */
    public ColumnFormation(ColumnFormation col) {
        this.game = col.game;
        this.initialLeftP = col.initialLeftP;
        this.initialRightP = col.initialRightP;
        this.scoreTracker = col.scoreTracker;
        this.blockRemover = col.blockRemover;
        this.ballRemover = col.ballRemover;
        this.formation = col.formation;
        for (Enemy e : col.enemyList) {
            this.enemyList.add(e);
        }
    }

    /**
     * create and add a new enemy to this column enemy list.
     *
     * @param b - the block to make a new enemy off.
     * @param blockR - the game block remover.
     * @param scoreT - the game score tracker.
     * @param g - the game the enemy will belong to.
     * @param enemyShot - the shot for this enemy.
     * @param ballR - the game ball remover.
     * @param formationSpeed - the whole formation this enemy will belong to initial movement speed.
     */
    public void addEnemy(Block b, BlockRemover blockR, ScoreTrackingListener scoreT, GameLevel g, Shot enemyShot,
            BallRemover ballR, double formationSpeed) {
        // the first enemy positions in the column formation.
        if (getEnemylist().isEmpty()) {
            initialLeftP = b.getCollisionRectangle().getTopLine().start();
            initialRightP = b.getCollisionRectangle().getTopLine().end();
        }
        Enemy e = new Enemy(b, enemyShot, this.formation, this, formationSpeed);
        e.addHitListener(blockR);
        e.addHitListener(ballR);
        e.addHitListener(scoreT);
        e.addToGame(g);
        this.enemyList.add(this.enemyList.size(), e);
        this.game = g;
        this.blockRemover = blockR;
        this.ballRemover = ballR;
        this.scoreTracker = scoreT;
    }

    /**
     * @return this column enemy list.
     */
    public List<Enemy> getEnemylist() {
        return this.enemyList;
    }

    /**
     * @return this column first enemy top left point.
     */
    public Point getInitialLeftP() {
        return this.initialLeftP;
    }

    /**
     * @return this column first enemy top right point.
     */
    public Point getInitialRightP() {
        return this.initialRightP;
    }

    /**
     * sets the whole column movement speed to given speed.
     *
     * @param s - the column movement speed to set.
     */
    public void setInitialColSpeed(double s) {
        List<Enemy> l = new LinkedList<Enemy>(getEnemylist());
        for (Enemy e : l) {
            e.setInitialSpeed(s);
        }
        this.enemyList = l;
    }

    /**
     * removes the given enemy from this column list.
     *
     * @param e - the enemy to remove.
     */
    public void removeFromColumn(Enemy e) {
        if (!getEnemylist().isEmpty()) {
            List<Enemy> l = new LinkedList<Enemy>(getEnemylist());
            if (l.contains(e)) {
                l.remove(e);
            }
            this.enemyList = l;
        }
    }

    /**
     * @return the column first enemy (the highest ) current top right point.
     */
    public Point getColumnCurrentTopRightP() {
        return getEnemylist().get(0).getCollisionRectangle().getTopLine().start();
    }

    /**
     * @return the column first enemy (the highest ) current top left point.
     */
    public Point getColumnCurrentTopLeftP() {
        return getEnemylist().get(0).getCollisionRectangle().getTopLine().end();
    }

    /**
     * @return the column last enemy (the lowest ).
     */
    public Enemy getColumnLastEnemy() {
        return getEnemylist().get(getEnemylist().size() - 1);
    }

    /**
     * move the whole column to given x,y parameters.
     *
     * @param x - the given x parameter to move this column to.
     * @param y - the given y parameter to move this column to.
     */
    public void moveColumn(double x, double y) {
        List<Enemy> l = new LinkedList<Enemy>(getEnemylist());
        for (Enemy e : l) {
            e.move(x, y);
        }
        this.enemyList = l;
    }

    /**
     * return this whole column to its starting position.
     */
    public void resetColumnPos() {
        List<Enemy> l = new LinkedList<Enemy>(getEnemylist());
        for (Enemy e : l) {
            if (e != null) {
                e.resetEnemyPos();
            }
        }
        this.enemyList = l;
    }

    /**
     * increase the whole column movement speed by 10%.
     */
    public void increaseColSpeed() {
        List<Enemy> l = new LinkedList<Enemy>(getEnemylist());
        for (Enemy e : l) {
            e.increaseSpeed();
        }
        this.enemyList = l;
    }

    /**
     * @return a new shot from this column last enemy (the lowest).
     */
    public Shot shoot() {
        if (!getEnemylist().isEmpty()) {
            return getColumnLastEnemy().shoot();
        }
        return null;
    }
}
