
package invaders;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.ScoreTrackingListener;
import geometry.Point;
import sprite.Block;

/**
 *
 */
public class Formation {
    private List<ColumnFormation> colList = new ArrayList<ColumnFormation>();
    private List<Shot> shotsList = new ArrayList<Shot>();
    private double formationDx = 1;
    private double formationSpeed;
    private Timer enemytimer;
    private InvadersGameLevel game;
    private TimerTask shootTask = null;
    private boolean formationCanShoot = false;

    /**
     * construct a new enemy formation with the ability to move and fire shots, When the formation hits the left or
     * right side of the screen, the aliens in the formation:
     * (a) go down; (b) change direction; and (c) increase their speed by 10%. The formation is considered to
     * hit the side of the screen when the left-most (or right-most) alien in the formation hits the side of the screen.
     *
     * @param g - the game the formation belong to.
     * @param blockList - the enemies (blocks) list.
     * @param scoreTracker - the game score tracker, each enemy is 100 points.
     * @param enemyFormationInitialSpeed - the initial movement speed for the formation.
     */
    public Formation(InvadersGameLevel g, List<Block> blockList, ScoreTrackingListener scoreTracker,
            double enemyFormationInitialSpeed) {
        this.game = g;
        this.formationSpeed = enemyFormationInitialSpeed;
        ColumnFormation col = new ColumnFormation(this);
        int i = 0;
        for (Block block : blockList) {
            Point shotPos = new Point(block.blockMidP().getX(), block.blockMidP().getY() + 10);
            Shot enemyShot = new Shot(shotPos, 3, Color.RED, true);
            col.addEnemy(block, g.getBlockRemover(), scoreTracker, g, enemyShot, g.getBallRemover(),
                    this.formationSpeed);
            g.getGameBlockCounter().increase(1);
            i++;
            if ((i != 0) && (i % 5 == 0)) {
                addCoulmn(col);
                col = new ColumnFormation(this);
            }
        }
    }

    /**
     * adds the new column to this formation enemy column list.
     *
     * @param col - the new enemy column to be added to this formation.
     */
    public void addCoulmn(ColumnFormation col) {
        this.colList.add(this.colList.size(), col);
    }

    /**
     * @return this formation enemy columns list.
     */
    public List<ColumnFormation> getColumnlist() {
        return this.colList;
    }

    /**
     * @return true if the formation can shoot(0.5 seconds passed since last shot), false otherwise.
     */
    public boolean getFormationCanShoot() {
        return this.formationCanShoot;
    }

    /**
     * sets this formation can shoot to true.
     */
    public void setFormationCanShoot() {
        this.formationCanShoot = true;
    }

    /**
     * @return the game level the formation belongs to.
     */
    public InvadersGameLevel getGame() {
        return this.game;
    }

    /**
     * clears all enemy shots created from the game.
     */
    public void clearShots() {
        for (Shot s : new ArrayList<Shot>(this.shotsList)) {
            s.removeFromGame(getGame());
            this.shotsList.remove(s);
        }
        this.shotsList = new ArrayList<Shot>();
    }

    /**
     * creates and starts a timer responsible to shoot every 0.5 seconds.
     */
    public void startShotsTimer() {
        Timer t = new Timer();
        if (this.shootTask != null) {
            this.shootTask.cancel();
        }
        this.shootTask = new TimerTask() {
            @Override
            public void run() {
                Formation.this.setFormationCanShoot();
            }
        };
        t.scheduleAtFixedRate(this.shootTask, 2500, 500);
        this.enemytimer = t;
    }

    /**
     * stops this formation shots timer.
     */
    public void stopShotsTimer() {
        this.enemytimer.cancel();
        clearShots();
    }

    /**
     * chooses a random column to shoot every 0.5 seconds, the shot will come from the lowest enemy in the column.
     */
    public void shoot() {
        if (getFormationCanShoot()) {
            Random rand = new Random();
            Shot s = null;
            // get a random column to shoot
            int randomCol = rand.nextInt((getColumnlist().size() - 1) + 1) + 0;
            s = new Shot(getColumnlist().get(randomCol).shoot());
            // add all the enemies as uncollidables for this enemy shot
            for (ColumnFormation col : new ArrayList<ColumnFormation>(getColumnlist())) {
                if (!col.getEnemylist().isEmpty()) {
                    for (Enemy e : col.getEnemylist()) {
                        s.addUncollideAble(e);
                    }
                }
            }
            if (s != null) {
                s.addToGame(this.getGame());
                this.shotsList.add(s);
                this.formationCanShoot = false;
            }
        }
    }

    /**
     * resets the whole formation to its starting speed and position.
     */
    public void resetFormationPos() {
        this.formationDx = 1;
        for (ColumnFormation col : getColumnlist()) {
            col.resetColumnPos();
        }
    }

    /**
     * increase the whole formation movement speed by 10%.
     */
    public void increaseFormationSpeed() {
        for (ColumnFormation col : getColumnlist()) {
            col.increaseColSpeed();
        }
    }

    /**
     * sets the whole formation initial movement speed to given speed.
     *
     * @param s - the initial movement speed for the formation.
     */
    public void setInitialFormationSpeed(double s) {
        for (ColumnFormation col : getColumnlist()) {
            col.setInitialColSpeed(s);
        }
    }

    /**
     * removes given enemy from the formation (and column formation) and if the column is empty removes it.
     *
     * @param e - the enemy to be removed from this formation.
     */
    public void removeFromFormation(Enemy e) {
        for (ColumnFormation col : new ArrayList<ColumnFormation>(getColumnlist())) {
            if (col.getEnemylist().contains(e)) {
                col.removeFromColumn(e);
            }
            if (col.getEnemylist().isEmpty()) {
                getColumnlist().remove(col);
            }
        }
    }

    /**
     * @return true if the formation lowest enemy reached the game shields or where they used to be if they were
     *         destroyed(a turn stopping condition) false otherwise.
     */
    public boolean reachedShields() {
        for (ColumnFormation col : getColumnlist()) {
            if (col.getColumnLastEnemy().enemyMidP().getY() >= 510) {
                return true;
            }
        }
        return false;
    }

    /**
     * Coordinates the whole formation movement and enemy shots,When the formation hits the left or right side of the
     * screen, the aliens in the formation (a) go down; (b) change direction; and (c) increase their speed by 10%. The
     * formation is considered to hit the side of the screen when the left-most (or right-most) alien in the formation
     * hits the side of the screen.
     */
    public void moveFormation() {
        if (!getColumnlist().isEmpty()) {
            ColumnFormation lastCol = getColumnlist().get(getColumnlist().size() - 1);
            ColumnFormation firstCol = getColumnlist().get(0);
            Point lastColRightP = lastCol.getColumnCurrentTopRightP();
            Point firstColLeftP = firstCol.getColumnCurrentTopLeftP();
            boolean changedDx = false;
            shoot();
            for (ColumnFormation col : getColumnlist()) {
                if (col != null) {
                    if ((lastColRightP.getX() >= 735) || (firstColLeftP.getX() <= 65)) {
                        col.moveColumn(0, 10);
                        col.increaseColSpeed();
                        if (!changedDx) {
                            this.formationDx = -this.formationDx;
                            changedDx = true;
                        }
                    }
                    col.moveColumn(this.formationDx, 0);
                }
            }
        }
    }
}
