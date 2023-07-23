
package invaders;

import java.util.ArrayList;
import java.util.List;

import geometry.Point;
import sprite.Block;

/**
 * a shields creator class.
 *
 *
 */
public class Shields {
    /**
     * @return creates and returns a list of shields blocks of a specific amount and positions.
     */
    public List<Block> createShields() {
        double x = 90;
        double y = 500;
        Block shield = new Block(x, y, 5, 5);
        shield.setRandomColor();
        shield.setToNotCountHit();
        List<Block> l = new ArrayList<Block>();
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 30; i++) {
                shield = shield.changePosition(new Point(x, y));
                shield.setToNotCountHit();
                l.add(new Block(shield));
                shield = shield.changePosition(new Point(x, y + 5));
                shield.setToNotCountHit();
                l.add(new Block(shield));
                shield = shield.changePosition(new Point(x, y + 10));
                shield.setToNotCountHit();
                l.add(new Block(shield));
                x += 5;
            }
            x += 85;
        }
        return l;
    }
}
