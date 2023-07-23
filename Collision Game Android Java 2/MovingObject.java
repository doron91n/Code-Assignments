package fluffybun.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class MovingObject implements GameObject{

    private int m_X_position;
    private int m_Y_position;
    private Point m_center_point;
    private int m_radius;
    private Paint m_default_paint;
    private int m_color;

     public MovingObject(){
this.m_X_position=300;
this.m_Y_position=300;
this.m_radius=20;
this.m_color= Color.RED;
this.m_default_paint=new Paint();
this.m_default_paint.setColor(this.m_color);
this.m_center_point=new Point(this.m_X_position,this.m_Y_position);
    }


    @Override
    public void draw(Canvas canvas){
canvas.drawCircle(this.m_X_position,this.m_Y_position,this.m_radius,this.m_default_paint);
    }
    @Override
    public void update() {
        this.m_Y_position+=10;
        this.m_X_position+=10;
    }

}
