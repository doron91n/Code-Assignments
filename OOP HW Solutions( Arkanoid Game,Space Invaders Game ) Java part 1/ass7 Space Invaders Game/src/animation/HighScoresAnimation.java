package animation;

import java.awt.Color;
import java.awt.Image;
import java.util.List;

import biuoop.DrawSurface;
import game.HighScoresTable;
import game.Parsers;
import game.ScoreInfo;

/**
 * a HighScoresAnimation class.
 *
 * 
 */
public class HighScoresAnimation implements Animation {
    private boolean stop;
    private HighScoresTable gameScores;
    private Image background;

    /**
     * construct an animation for displaying the game High Scores Table.
     *
     * @param scores - the game High Scores Table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.gameScores = scores;
        Parsers p = new Parsers();
        try {
            this.background = (Image) p.getBackground("image(my_screens_Background/trophy.jpg)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.drawImage(d.getWidth() - 250, d.getHeight() - 200, this.background);
        List<ScoreInfo> l = this.gameScores.getHighScores();
        String line = "__________________________";
        d.setColor(Color.white);
        d.drawText(80 + d.getWidth() / 4, 60, "High-Scores", 40);
        d.drawText(d.getWidth() / 4, d.getHeight() - 40, "press Space Key to continue", 30);
        d.drawText(80, 130, line, 40);
        d.drawText(150, 120, "Player Name", 30);
        d.drawText(450, 120, "Final Score", 30);
        d.setColor(Color.ORANGE);
        d.drawText(80 + d.getWidth() / 4, 61, "High-Scores", 40);
        d.setColor(Color.MAGENTA.darker());
        d.drawText(150, 121, "Player Name", 30);
        d.drawText(450, 121, "Final Score", 30);
        d.drawText(80, 131, line, 40);
        for (int i = 0; i < l.size(); i++) {
            line = Integer.toString(l.get(i).getScore());
            d.setColor(Color.white);
            d.drawText(170, 170 + (i * 35), l.get(i).getName(), 25);
            d.drawText(470, 170 + (i * 35), line, 25);
            d.drawText(100, 170 + (i * 35), "# " + (i + 1), 25);
            d.setColor(Color.cyan);
            d.drawText(170, 171 + (i * 35), l.get(i).getName(), 25);
            d.drawText(470, 171 + (i * 35), line, 25);
            d.drawText(100, 171 + (i * 35), "# " + (i + 1), 25);

        }
        d.setColor(Color.GRAY);
        d.drawText(d.getWidth() / 4, d.getHeight() - 41, "press Space Key to continue", 30);

    }

    /**
     * in charge of the animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * @return this High Scores table.
     */
    public HighScoresTable getScoresTable() {
        return this.gameScores;
    }
}
