package fluffybun.game.GameObjects;

import android.graphics.Canvas;

public interface GameObject {
    void draw(Canvas canvas);

    void update();

    int getID();
void onDestroy();
}
