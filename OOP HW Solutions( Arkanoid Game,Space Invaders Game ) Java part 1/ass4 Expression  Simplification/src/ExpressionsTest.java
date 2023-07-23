import java.util.Map;
import java.util.TreeMap;

/**
 * a ExpressionsTest class - runs the example.
 *
 */
public class ExpressionsTest {
    /**
     * the main that runs the test.
     *
     * @param args - not used.
     */
    public static void main(String[] args) {
        // Create the expression (2x) + (sin(4y)) + (e^x).
        Expression e1 = new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "y"))), new Pow("e", "x"));
        // Print the expression (2x) + (sin(4y)) + (e^x).
        System.out.println(e1);
        // Print the value of the expression with (x=2,y=0.25,e=2.71).
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("x", (double) 2);
        assignment.put("y", 0.25);
        assignment.put("e", 2.71);
        try {
            double d1 = e1.evaluate(assignment);
            System.out.println(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Print the differentiated expression according to x.
        Expression e1d = e1.differentiate("x");
        System.out.println(e1d);
        // Print the value of the differentiated expression according to x with the assignment above.
        try {
            double d2 = e1d.evaluate(assignment);
            System.out.println(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Print the simplified differentiated expression.
        System.out.println(e1d.simplify());
        // (2x) + (sin(4x)) + (e^x)-(7x^2)
        Expression e2 = new Minus(new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "x"))), new Pow("e", "x")),
                new Mult(7, new Pow("x", 2)));
        System.out.println(e2.simplify());
        System.out.println(e2.differentiate("x"));
        System.out.println(e2.differentiate("x").simplify());
    }
}
