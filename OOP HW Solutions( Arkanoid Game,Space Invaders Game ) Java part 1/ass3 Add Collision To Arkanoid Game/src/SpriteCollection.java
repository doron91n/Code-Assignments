
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * a SpriteCollection class.
 *
 */
public class SpriteCollection {
    private List<Sprite> spriteList = new ArrayList<Sprite>();

    /**
     * add a new sprite to this sprite list.
     *
     * @param s - the new sprite to be added to the sprite list.
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * call timePassed() on all sprites in the sprite List.
     */
    public void notifyAllTimePassed() {
        if (!this.spriteList.isEmpty()) {
            for (int i = 0; i < spriteList.size(); i++) {
                spriteList.get(i).timePassed();
            }
        }
    }

    /**
     * call drawOn(d) on all sprites in the sprite List.
     *
     * @param d - the drawing surface.
     */
    public void drawAllOn(DrawSurface d) {
        if (!this.spriteList.isEmpty()) {
            for (int i = 0; i < spriteList.size(); i++) {
                spriteList.get(i).drawOn(d);
            }
        }
    }
}
