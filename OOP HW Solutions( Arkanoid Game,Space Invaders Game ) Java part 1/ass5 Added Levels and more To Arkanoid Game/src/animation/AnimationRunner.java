package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * a AnimationRunner class.
 *
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    // the screen blocks height for (top,under) screen limit and width for (right,left) screen limit.
    private int screenLimitBlock = 30; // default value
    private int animaScreenWidth;
    private int animaScreenHeight;

    /**
     * AnimationRunner Constructor, runs the animation.
     *
     * @param guiX - the animation GUI.
     * @param framesPerSec - the animation frames rate Per Second.
     * @param screenWidth - the animation screen width.
     * @param screenHeight - the animation screen height.
     */
    public AnimationRunner(int framesPerSec, GUI guiX, int screenWidth, int screenHeight) {
        this.framesPerSecond = framesPerSec;
        this.gui = guiX;
        this.animaScreenWidth = screenWidth;
        this.animaScreenHeight = screenHeight;
    }

    /**
     * runs the given animation.
     *
     * @param animation - the animation to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (!animation.shouldStop()) {
            DrawSurface d = this.gui.getDrawSurface();
            long startTime = System.currentTimeMillis(); // timing
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * closing the animation GUI window screen.
     */
    public void closeAnimation() {
        this.gui.close();
    }

    /**
     * @return this animation screen Width.
     */
    public double getScreenWidth() {
        return this.animaScreenWidth;
    }

    /**
     * @return this animation screen Height.
     */
    public double getScreenHeight() {
        return this.animaScreenHeight;
    }

    /**
     * @return this animation screen Limit Block height for (top,under) screen limit and width for (right,left) screen
     *         limit.
     */
    public double getScreenLimitBlock() {
        return this.screenLimitBlock;
    }
}