package fluffybun.game.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

import fluffybun.game.UtilityBox;

public class ObstacleManager {

    private float m_speed = UtilityBox.SCREEN_HEIGHT / 10000.0f;
    // higher index in array = higher y value = lower on screen (0 top of screen)
    private ArrayList<Obstacle> m_obstacle_array;
    private int m_player_gap;
    private int m_obstacle_gap;
    private int m_obstacle_height;
    private long m_start_time;
    private long m_initial_start_time;


    public ObstacleManager(int player_gap, int obstacle_gap, int obstacle_height) {
        this.m_obstacle_array = new ArrayList<>();
        this.m_player_gap = player_gap;
        this.m_obstacle_gap = obstacle_gap;
        this.m_obstacle_height = obstacle_height;
        this.m_start_time = this.m_initial_start_time = System.currentTimeMillis();
        fillObstacles();
    }

    private void fillObstacles() {
        int current_y = (-5 * UtilityBox.SCREEN_HEIGHT) / 4;
        // while the last obstacle
        while (current_y < 0) {
            int random_start_x = (int) (Math.random() * (UtilityBox.SCREEN_WIDTH - this.m_player_gap));
            this.m_obstacle_array.add(new Obstacle(this.m_obstacle_height, random_start_x, current_y, this.m_player_gap, Color.BLACK));
            current_y += this.m_obstacle_gap + this.m_obstacle_height;
        }
    }

    public void update() {
        int elapsed_time = (int) (System.currentTimeMillis() - this.m_start_time);
        int increment_y_speed = (int) (elapsed_time * this.m_speed);
        this.m_start_time = System.currentTimeMillis();
        // update all obstacles top+ bottoms to new y values
        for (Obstacle obstacle : this.m_obstacle_array) {
            obstacle.incrementY(increment_y_speed);
        }
        // if the last obstacle in array left the screen generate new obstacle
        if (this.m_obstacle_array.get(this.m_obstacle_array.size() - 1).getShape().top >= UtilityBox.SCREEN_HEIGHT) {
            int random_start_x = (int) (Math.random() * (UtilityBox.SCREEN_WIDTH - this.m_player_gap));
            int next_start_y = this.m_obstacle_array.get(0).getShape().top - this.m_obstacle_height - this.m_obstacle_gap;
            // Obstacle(int rectangle_height,int start_x,int start_y,int player_gap)
            this.m_obstacle_array.add(0, new Obstacle(this.m_obstacle_height, random_start_x, next_start_y, this.m_player_gap, Color.BLACK));
            this.m_obstacle_array.remove(this.m_obstacle_array.size() - 1);
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle obstacle : this.m_obstacle_array) {
            obstacle.draw(canvas);
        }
    }

    public boolean playerCollision(Player player) {
        for (Obstacle obstacle : this.m_obstacle_array) {
            if (obstacle.playerCollision(player)) {
                return true;
            }
        }
        return false;
    }
}
