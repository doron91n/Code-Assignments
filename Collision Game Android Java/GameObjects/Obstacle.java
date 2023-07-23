package fluffybun.game.GameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import fluffybun.game.UtilityBox;

public class Obstacle implements GameObject {
    private Rect m_left_rectangle;
    private Rect m_right_rectangle;
    private int m_color;
    private Paint m_rect_paint;


    public Obstacle(int rectangle_height, int start_x, int start_y, int player_gap, int color) {
        // left,top,right,bottom
        this.m_left_rectangle = new Rect(0, start_y, start_x, start_y + rectangle_height);
        this.m_right_rectangle = new Rect(start_x + player_gap, start_y, UtilityBox.SCREEN_WIDTH, start_y + rectangle_height);
        this.m_color = color;
        this.m_rect_paint = new Paint();
        this.m_rect_paint.setColor(this.m_color);
    }


    public Rect getShape() {
        return this.m_left_rectangle;
    }

    public void incrementY(float y) {
        this.m_left_rectangle.bottom += y;
        this.m_left_rectangle.top += y;
        this.m_right_rectangle.bottom += y;
        this.m_right_rectangle.top += y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.m_left_rectangle, this.m_rect_paint);
        canvas.drawRect(this.m_right_rectangle, this.m_rect_paint);

    }

    @Override
    public void update() {

    }

    @Override
    public int getID() {
        return 0;
    }

    public boolean playerCollision(Player player) {
        /*
        Rectangle player_shape = player.getPlayerShape();
        boolean left_collision_detected = player_shape.intersectingShape(this.m_left_rectangle);
        boolean right_collision_detected = player_shape.intersectingShape(this.m_right_rectangle);
        return left_collision_detected || right_collision_detected;*/
        return false;
    }

    @Override
    public void onDestroy(){
    }
}
