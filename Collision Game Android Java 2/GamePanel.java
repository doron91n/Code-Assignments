package fluffybun.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread m_thread;
    private Rect m_screen_rect= new Rect();
    private Player m_player;
    private Point m_player_point;
    private ObstacleManager m_obstacles_manager;
    private boolean m_moving_player = false;
    private boolean m_game_over = false;
    private long m_game_over_time;
    private static long m_game_over_wait_time = 2000; // 2 sec
    private Paint m_default_paint= new Paint(Color.GREEN);
private MovingObject m_moving_ball;
    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        restGame();
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        this.m_thread = new MainThread(getHolder(), this);
        this.m_thread.setRunning(true);
        this.m_thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        while (true) {
            try {
                this.m_thread.setRunning(false);
                this.m_thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void restGame() {
        this.m_moving_ball=new MovingObject();
        this.m_game_over = false;
        this.m_moving_player=false;
        this.m_thread = new MainThread(getHolder(), this);
        // player creation
        this.m_player = new Player();
        this.m_player_point = this.m_player.getPlayerPoint();
        this.m_player.resetPlayerPosition();
        // obstacles creation (player_gap, obstacle_gap, obstacle_height)
        this.m_obstacles_manager = new ObstacleManager(200, 350, 75);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (!this.m_game_over && this.m_player.getPlayerShape().contains((int) event.getX(), (int) event.getY())) {
                    this.m_moving_player = true;
                }

                long elapsed_time = System.currentTimeMillis() - this.m_game_over_time;
                // if its game over and we waited 2 sec we can reset the game
                if (this.m_game_over && elapsed_time >= m_game_over_wait_time) {
                    restGame();
                }
                break;

            case MotionEvent.ACTION_UP:
                this.m_moving_player = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!this.m_game_over && this.m_moving_player) {
                    this.m_player_point.set((int) event.getX(), (int) event.getY());
                }
        }
        return true;
    }

    public void update() {
        if (!this.m_game_over) {

            this.m_moving_ball.update();

            this.m_player.update(this.m_player_point);
            this.m_obstacles_manager.update();
            // game over upon player collision with obstacle
            if (this.m_obstacles_manager.playerCollision(this.m_player)) {
                this.m_game_over = true;
                this.m_game_over_time = System.currentTimeMillis();
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        this.m_player.draw(canvas);
        this.m_moving_ball.draw(canvas);
        this.m_obstacles_manager.draw(canvas);
        if(this.m_game_over){
            this.m_default_paint.setColor(Color.MAGENTA);
            this.m_default_paint.setTextSize(80);
            drawCenterText(canvas,"GAME OVER");
        }
    }

    public void drawCenterText(Canvas canvas, String text){
        this.m_default_paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(this.m_screen_rect);
        int clip_height=this.m_screen_rect.height();
        int clip_width=this.m_screen_rect.width();
        this.m_default_paint.getTextBounds(text,0,text.length(),this.m_screen_rect);
        float x=(clip_width/2f)-(this.m_screen_rect.width()/2f) -this.m_screen_rect.left;
        float y=(clip_height/2f)+(this.m_screen_rect.height()/2f) -this.m_screen_rect.bottom;
        canvas.drawText(text,x,y, this.m_default_paint);
    }
}