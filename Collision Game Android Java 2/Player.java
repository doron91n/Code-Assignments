package fluffybun.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject {
    private Rect m_rectangle;
    private int m_color;
    private Paint m_rect_paint;
    private Point m_player_point;

    public Player() {
        this.m_rectangle = new Rect(100, 100, 200, 200);;
        this.m_color = Color.RED;
        this.m_rect_paint = new Paint();
        this.m_rect_paint.setColor(this.m_color);
        this.m_player_point = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        resetPlayerPosition();
    }
public void resetPlayerPosition(){
    update(this.m_player_point);

}
    public Rect getPlayerShape() {
        return this.m_rectangle;
    }
    public Point getPlayerPoint() {
        return this.m_player_point;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.m_rectangle, this.m_rect_paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        int rect_center_x = m_rectangle.width() / 2;
        int rect_center_y = m_rectangle.height() / 2;
        // left,top,right,bottom positions
        this.m_rectangle.set(point.x - rect_center_x, point.y - rect_center_y, point.x + rect_center_x, point.y + rect_center_y);
    }
}
