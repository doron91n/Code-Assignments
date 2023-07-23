package sprite;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * a SpriteCollection class
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
     * remove the given sprite from this sprite list.
     *
     * @param s - the sprite to be removed from the sprite list.
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * call timePassed() on all sprites in the sprite List.
     *
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        if (!this.spriteList.isEmpty()) {
            for (int i = 0; i < spriteList.size(); i++) {
                if (spriteList.get(i) != null) {
                    spriteList.get(i).timePassed(dt);
                }
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
