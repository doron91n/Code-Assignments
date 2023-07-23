package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * a KeyPressStoppableAnimation class.
 *
 *
 */
public class KeyPressStoppableAnimation implements Animation {
    private boolean isAlreadyPressed;
    private boolean stop;
    private KeyboardSensor keyboard;
    private String stopKey;
    private Animation animationToRun;

    /**
     * creates an animation with keyboard behavior.
     *
     * @param sensor - the animation keyboard sensor.
     * @param key - the key to press in order to stop the animation.
     * @param animation - the animation to run until a specific key is pressed.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.stopKey = key;
        this.animationToRun = animation;
        this.isAlreadyPressed = true;
    }

    /**
     * in charge of the game logic,perform one frame.
     *
     * @param d - the drawing surface.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (!this.keyboard.isPressed(this.stopKey)) {
            this.isAlreadyPressed = false;
        }
        this.animationToRun.doOneFrame(d, dt);
        if (this.keyboard.isPressed(this.stopKey) && !this.isAlreadyPressed) {
            this.stop = true;
        }
    }

    /**
     * in charge of the game animation stopping condition.
     *
     * @return true if the stopping condition is met false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}