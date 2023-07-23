import java.util.List;
import java.util.Map;

/**
 * Expression interface.
 *
 */
public interface Expression extends Compare {
    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluate the expression and return the result.
     *
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    double evaluate() throws Exception;

    /**
     * @return - a list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * @return s - a nice string representation of this expression.
     */
    @Override
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression
     * (Does not modify the current expression).
     *
     * @param var - the variable to replace with the provided expression.
     * @param expression - the expression to replace with the provided variable.
     * @return - the newly modified expression after replacing the given var to the given expression.
     */

    Expression assign(String var, Expression expression);

    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     *
     * @param var - the variable to differentiate this expression by.
     * @return - the expression Differential relative to variable `var` .
     */
    Expression differentiate(String var);

    /**
     * @return a simplified version of the current expression.
     */
    Expression simplify();
}