
/**
 */
import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

/**
 * AbstractArtDrawing class.
 */
public class AbstractArtDrawing {
    private int screenStartX = 0; // left for future use
    private int screenStartY = 0; // left for future use
    private int screenHeight = 300;
    private int screenWidth = 400;
    private int arrayLen = 10;

    /**
     * this class creates gui window with set parameters(400X300), generates number(arrayLen) of random lines and draws
     * them(with intersection and middle points).
     */
    public void drawRandomLines() {
        // Create a window with the title "Random Lines ", which is 400 pixels wide and 300 pixels high.
        GUI gui = new GUI("Random Lines", screenWidth, screenHeight);
        DrawSurface d = gui.getDrawSurface();
        Line[] lineArray = new Line[arrayLen];
        // create and draw a random line and insert him into the lineArray.
        for (int i = 0; i < lineArray.length; i++) {
            Line newLine = generateRandomLine();
            lineArray[i] = newLine;
            drawLine(lineArray[i], d);
        }
        // draws the intersection points of the lines in lineArray.
        drawIntersectionPoints(lineArray, d);
        gui.show(d);
    }

    /**
     * generates a new random line with starting and ending points within the set parameters (400X300).
     *
     * @return randLine - the newly generated line.
     */
    public Line generateRandomLine() {
        Random rand = new Random(); // create a random-number generator.
        int x1 = rand.nextInt(screenWidth) + 1; // get integer in range 1-screenWidth.
        int x2 = rand.nextInt(screenWidth) + 1; // get integer in range 1-screenWidth.
        int y1 = rand.nextInt(screenHeight) + 1; // get integer in range 1-screenHeight.
        int y2 = rand.nextInt(screenHeight) + 1; // get integer in range 1-screenHeight.
        // start point(x1,y1) end point(x2,y2)
        Line randLine = new Line(x1, y1, x2, y2);
        return randLine;
    }

    /**
     * draws on the given board (d) the given line (l) in black and its middle point as a blue circle with radius 3.
     *
     * @param l - the given random line to draw.
     * @param d - the given board to draw the line and points on.
     */
    public void drawLine(Line l, DrawSurface d) {
        d.setColor(Color.black);
        d.drawLine((int) l.start().getX(), (int) l.start().getY(), (int) l.end().getX(), (int) l.end().getY());
        d.setColor(Color.blue);
        d.fillCircle((int) l.middle().getX(), (int) l.middle().getY(), 3);
    }

    /**
     * draws on the given board (d) for each line in the given lineArray its intersection points with the other lines as
     * a red circle with radius 3.
     *
     * @param lineArray - an array that holds the random generated lines.
     * @param d - the given board to draw the line and points on.
     */
    public void drawIntersectionPoints(Line[] lineArray, DrawSurface d) {
        d.setColor(Color.red);
        for (int i = 0; i < lineArray.length; i++) {
            for (int j = 0; j < lineArray.length; j++) {
                if (lineArray[i].isIntersecting(lineArray[j])) {
                    Point interPoint = lineArray[i].intersectionWith(lineArray[j]);
                    d.fillCircle((int) interPoint.getX(), (int) interPoint.getY(), 3);
                }
            }
        }
    }

    /**
     * the AbstractArtDrawing main method, runs the program.
     *
     * @param args - no user arguments.
     */
    public static void main(String[] args) {
        AbstractArtDrawing example = new AbstractArtDrawing();
        example.drawRandomLines();
    }
}
