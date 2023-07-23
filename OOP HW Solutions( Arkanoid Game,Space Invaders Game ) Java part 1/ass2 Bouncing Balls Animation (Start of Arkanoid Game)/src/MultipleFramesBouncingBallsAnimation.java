
/**
 */
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.util.Random;
import java.awt.Color;

/**
 * A MultipleFramesBouncingBallsAnimation class.
 */
public class MultipleFramesBouncingBallsAnimation {
    /**
     * the MultipleFramesBouncingBallsAnimation main method invokes the animation.
     *
     * @param args - user entered ball radius.
     */
    public static void main(String[] args) {
        MultipleFramesBouncingBallsAnimation g = new MultipleFramesBouncingBallsAnimation();
        g.animationCreate(args);
    }

    /**
     * the method , gets cmd line arguments from user(balls radius sizes) and creates multiple bouncing balls animation
     * , balls made from the first half of the arguments will bounce in a grey rectangle and the other half in a yellow
     * rectangle,all balls velocity is based on their size(the bigger the slower).
     *
     * @param arguments - user entered ball radius.
     */
    public void animationCreate(String[] arguments) {
        int screenStartX = 0; // left for future use
        int screenStartY = 0; // left for future use
        int screenHeight = 600;
        int screenWidth = 600;
        int len = arguments.length;
        /* create a new String array and insert all the arguments provided into it. */
        String[] arrayS = new String[len];
        for (int i = 0; i < len; i++) {
            arrayS[i] = arguments[i];
        }
        /* create a new int array and insert all the arguments provided into it after changing them from string. */
        int[] arrayRad = stringsToInts(arrayS);
        /*
         * create a ball array where the first half of the balls are confined to the grey rectangle and the other half
         * to the yellow rectangle.
         */
        Ball[] ballArray = new Ball[len];
        // the yellow rectangle limits points
        Point yellowStartPoint = new Point(450, 450);
        Point yellowSizePoint = new Point(150, 150);
        // the grey rectangle limits points
        Point greyStartPoint = new Point(50, 50);
        Point greySizePoint = new Point(450, 450);
        if (len < 2) {
            ballArray = ballArrayCreate(arrayRad, ballArray, 0, len, greyStartPoint, greySizePoint,
                    java.awt.Color.gray);
        } else {
            ballArray = ballArrayCreate(arrayRad, ballArray, 0, len / 2, greyStartPoint, greySizePoint,
                    java.awt.Color.gray);
            ballArray = ballArrayCreate(arrayRad, ballArray, len / 2, len, yellowStartPoint, yellowSizePoint,
                    java.awt.Color.yellow);
        }
        GUI gui = new GUI("MultipleFramesBouncingBallsAnimation", screenWidth, screenHeight);
        Sleeper sleeper = new Sleeper();
        /* run the MultipleFramesBouncingBalls animation */
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            d.setColor(java.awt.Color.gray);
            d.fillRectangle(50, 50, 450, 450);
            d.setColor(java.awt.Color.yellow);
            d.fillRectangle(450, 450, 150, 150);
            for (int i = 0; i < len; i++) {
                ballArray[i].moveOneStep();
                ballArray[i].drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }

    /**
     * generates and returns a new random point within the desired rectangle limits.
     *
     * @param radius - this new random generated point (ball center) radius.
     * @param startP - the screen starting point (x = Start X parameter ,y = Start Y parameter).
     * @param size - the screen size (x = screen width , y = screen height).
     * @return Point - a new random point (will be the center of the ball).
     */
    public Point generateRandomPoint(int radius, Point startP, Point size) {
        int x, y;
        Random rand = new Random(); // create a random-number generator
        x = rand.nextInt((int) size.getX()) + (int) startP.getX(); // get integer in range startP.x to width
        y = rand.nextInt((int) size.getY()) + (int) startP.getY(); // get integer in range startP.y to height
        return new Point(x, y);
    }

    /**
     * generates and returns a random color darker ,different from the given screen color.
     *
     * @param screenColor - the color we want to avoid when generating the random color.
     * @return randColor - the newly random generated color will be different from the given screen color.
     */
    public java.awt.Color generateRandomColor(java.awt.Color screenColor) {
        Color randColor;
        do {
            randColor = new Color((int) (Math.random() * 0x1000000)).darker();
        } while (randColor.equals(screenColor));
        return randColor;
    }

    /**
     * The function , gets a (String) array of numbers,creates a new (int) array and inserts the (String) numbers into
     * it, if given args(radius) are double,tries to cast them to int ,if the convert fails prints Error msg and exit.
     *
     * @param args - a String array (numbers).
     * @return numbersArray - a converted (int) version of the String array.
     */
    public int[] stringsToInts(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: No arguments entered.");
            System.exit(1);
        }
        int[] numbersArray = new int[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                numbersArray[i] = (int) Double.parseDouble(args[i]);
            }
        } catch (Exception e) {
            System.out.println("Error:The ball given Radius is illegel: only integers are allowed ");
            System.exit(1);
        }
        return numbersArray;
    }

    /**
     * fills given ball array by creating new balls with radius from the given int array , each ball will have a random
     * generated starting point and color(different from given screen color),each ball will be confined to the given
     * rectangle parameters (based on startP,size points )and its velocity with a random angle and speed based on its
     * radius size. (the bigger the radius the slower).
     *
     * @param array - a int array containing the balls radius sizes (the main cmd line args).
     * @param ballArray - a ball array that will contain the created balls.
     * @param start - the start index to insert the created balls into the ballArray.
     * @param end - the end index to insert the created balls into the ballArray.
     * @param startP - the ball screen(bouncing limits) starting point (x = Start X parameter ,y = Start Y parameter).
     * @param size - the ball screen(bouncing limits) size (x = screen width , y = screen height).
     * @param screenC - the ball screen background color.
     * @return ballArray - an array that contains the created balls .
     */

    public Ball[] ballArrayCreate(int[] array, Ball[] ballArray, int start, int end, Point startP, Point size,
            java.awt.Color screenC) {
        for (int i = start; i < end; i++) {
            // generate a random center point for the ball within given ball screen size limits.
            Point p = generateRandomPoint(array[i], startP, size);
            // generate a random color for the ball,the color will be different then the screen color.
            Color randColor = generateRandomColor(screenC);
            Ball ball = new Ball((int) p.getX(), (int) p.getY(), array[i], randColor);
            // sets the ball bouncing limits (confine to the given rectangle points)
            ball.setScreenSizeByPoints(startP, size);
            // sets the ball velocity with a random angle and speed based on its radius size.
            ball.setBallRandomVelocity();
            ballArray[i] = ball;
        }
        return ballArray;
    }
}
