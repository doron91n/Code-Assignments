package fluffybun.game.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;

import fluffybun.game.Geometry.Point;
import fluffybun.game.Geometry.Rectangle;
import fluffybun.game.UtilityBox;

public class Player implements GameObject {
    private Rectangle m_rectangle;
    private int m_color;
    private Point m_player_center_point;


    public Player() {
        this.m_color = Color.RED;
        Point m_player_upper_left_point = new Point(UtilityBox.SCREEN_WIDTH / 2, 3 * UtilityBox.SCREEN_HEIGHT / 4);
        this.m_rectangle = new Rectangle(m_player_upper_left_point, 50, 200, m_color);
        m_player_center_point = new Point(this.m_rectangle.getCenterPoint());
        resetPlayerPosition();
    }

    public void resetPlayerPosition() {
        update(this.m_player_center_point);

    }

    public Rectangle getPlayerShape() {
        return this.m_rectangle;
    }

    public Point getPlayerPoint() {
     //   System.out.println("getPlayerPoint returns Player center Point  " + this.m_rectangle.getCenterPoint().toString());
        return this.m_rectangle.getCenterPoint();
    }

    @Override
    public void draw(Canvas canvas) {
        this.m_rectangle.draw(canvas);
    }

    @Override
    public void update() {

    }

    @Override
    public int getID() {
        return 0;
    }

    public void changePlayerColor() {
        this.m_rectangle.setRandomColor();
    }

    public void update(Point new_center_point) {
     //   System.out.println("player update got new bf movebycenter center Point  " + new_center_point.toString() + " current player point is: " + this.m_rectangle.getCenterPoint().toString());

        // left,top,right,bottom positions
        this.m_rectangle.moveShapeByCenterPoint(new_center_point);
     //   System.out.println("player update after moveshapebycenter got new  center Point  " + new_center_point.toString() + " current player point is: " + this.m_rectangle.getCenterPoint().toString());

    }
    @Override
    public void onDestroy(){
    }
}
