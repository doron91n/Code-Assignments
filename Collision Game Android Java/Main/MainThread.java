package fluffybun.game.Main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import fluffybun.game.GamePanel;

public class MainThread extends Thread {
    public static final int m_MAX_FPS = 30;
    public static Canvas m_canvas;
    private double m_AVG_FPS;
    private SurfaceHolder m_surface_holder;
    private GamePanel m_game_panel;
    private boolean m_running;

    public MainThread(SurfaceHolder surface_holder, GamePanel game_panel) {
        super();
        this.m_game_panel = game_panel;
        this.m_surface_holder = surface_holder;
    }

    public void setRunning(boolean running) {
        this.m_running = running;
    }

    @Override
    public void run() {
        long start_time;
        long time_millis;
        long wait_time;
        long frame_count = 0;
        long total_time = 0;
        long target_time = 1000 / m_MAX_FPS;

        while (m_running) {
            start_time = System.nanoTime();
            m_canvas = null;
            try {
                m_canvas = this.m_surface_holder.lockCanvas();
                synchronized (m_surface_holder) {
                    this.m_game_panel.update();
                    this.m_game_panel.draw(m_canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (m_canvas != null) {
                    try {
                        m_surface_holder.unlockCanvasAndPost(m_canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            time_millis = (System.nanoTime() - start_time) / 1000000;
            wait_time = target_time - time_millis;
            try {
                if (wait_time > 0) {
                    this.sleep(wait_time);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            total_time += System.nanoTime() - start_time;
            frame_count++;
            if (frame_count == m_MAX_FPS) {
                m_AVG_FPS = 1000 / ((total_time / frame_count) / 1000000);
                frame_count = 0;
                total_time = 0;
                //    System.out.println("Avg Fps:" + m_AVG_FPS);   ***********************************************************************
            }
        }
    }


}
