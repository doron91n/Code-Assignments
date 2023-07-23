package animation;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprite.SpriteCollection;

/**
 * a CountdownAnimation class.
 *
 *
 */
public class CountdownAnimation implements Animation {
    private boolean stop;
    private double numOfSeconds;
    private int startCountFrom;
    private int count;
    private SpriteCollection gameScreen;
    private Color screenColor;
    private Color alternativeColor = generateRandomColor(this.screenColor);

    /**
     * the CountdownAnimation will display the given gameScreen,for numOfSeconds seconds, and on top of them
     * it will show a count-down from countFrom back to 1, where each number will appear on the screen for
     * (numOfSeconds/ countFrom) seconds, before it is replaced with the next one.
     *
     * @param numOfSecond - the total time the count-down should last for.
     * @param countFrom - the number the count-down start from.
     * @param gameScreenX - the game sprite collection.
     * @param screenCol - the game screen color.
     */
    public CountdownAnimation(double numOfSecond, int countFrom, SpriteCollection gameScreenX, Color screenCol) {
        this.numOfSeconds = numOfSecond;
        this.startCountFrom = countFrom;
        this.gameScreen = gameScreenX;
        this.stop = false;
        this.count = this.startCountFrom;
        this.screenColor = screenCol;
    }

    /**
     * in charge of the animation logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        int millisecondsPerFrame = (int) ((1000 * this.numOfSeconds) / this.startCountFrom);
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.WHITE);
        // don't display the count 0.
        if (count != 0) {
            d.drawText(d.getWidth() / 2, 99 + d.getHeight() / 2, Integer.toString(this.count), 72);
            // makes sure the counter color is different from the screen color.
            if (!Color.MAGENTA.darker().equals(this.screenColor)) {
                d.setColor(Color.MAGENTA.darker());
            } else {
                d.setColor(alternativeColor);
            }
            d.drawText(d.getWidth() / 2, 100 + d.getHeight() / 2, Integer.toString(this.count), 72);
        }
        Sleeper sleeper = new Sleeper();
        if (this.startCountFrom != this.count) {
            sleeper.sleepFor(millisecondsPerFrame);
        }
        this.count--;
    }

    /**
     * in charge of the animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    @Override
    public boolean shouldStop() {
        if (this.count == -1) {
            this.stop = true;
        }
        return this.stop;
    }

    /**
     * generates and returns a random color darker ,different from the given screen color.
     *
     * @param screenCol - the color we want to avoid when generating the random color.
     * @return randColor - the newly random generated color will be different from the given screen color.
     */
    public Color generateRandomColor(Color screenCol) {
        Color randColor;
        do {
            randColor = new Color((int) (Math.random() * 0x1000000)).darker();
        } while (randColor.equals(screenCol));
        return randColor;
    }
}
