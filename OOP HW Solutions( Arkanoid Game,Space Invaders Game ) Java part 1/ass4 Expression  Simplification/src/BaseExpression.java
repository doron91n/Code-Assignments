import java.util.Map;
import java.util.TreeMap;

/**
 * a BaseExpression abstract class.
 *
 */
public abstract class BaseExpression {
    /**
     * checks the given expression ex and returns true if its a number ,false otherwise.
     *
     * @param ex - the given expression to check if its a number.
     * @return true if ex is a number ,false otherwise.
     */
    protected Boolean isNumber(Expression ex) {
        if (ex.getClassName().equals("Num")) {
            return true;
        }
        return false;
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return - evaluated answer of this expression, a NaN value.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    public double evaluate() throws Exception {
        try {
            Map<String, Double> assignment = new TreeMap<String, Double>();
            return this.evaluate(assignment);
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Expression  " + e.getMessage());
        }
    }

    /**
     * evaluate the expression with given parameters from the assignment map.
     *
     * @param assignment - the map containing variables and their value to assign in this expression.
     * @return the evaluated result, NaN if evaluation is not possible.
     * @throws Exception - "Error: Failed to evaluate Expression" in case the evaluate fails.
     */
    public abstract double evaluate(Map<String, Double> assignment) throws Exception;
}
