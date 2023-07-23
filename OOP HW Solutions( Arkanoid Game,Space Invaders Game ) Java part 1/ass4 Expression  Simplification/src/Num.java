import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * a Num class.
 *
 */
public class Num implements Expression {
    private double num;
    private String className = "Num";

    /**
     * this Num Expression class constructor with given double number.
     *
     * @param number - this Num expression number value.
     */
    Num(double number) {
        this.num = number;
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return this Num expression num value, a.k.a the evaluated answer of this expression.
     * @throws Exception - no exception.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.num;
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return - evaluated answer of this expression a.k.a this number value.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate() throws Exception {
        return this.num;
    }

    /**
     * @return - a list of the variables in the expression,a Num expression is not a variable therefore returns an empty
     *         list.
     */
    @Override
    public List<String> getVariables() {
        return new ArrayList<String>();
    }

    /**
     * @return - a nice string representation of this expression.
     */
    @Override
    public String toString() {
        return Double.toString(this.num);
    }

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression
     * (Does not modify the current expression).
     *
     * @param var - the variable to replace with the provided expression.
     * @param expression - the expression to replace with the provided variable.
     * @return this expression , a Num expression cannot be assigned a different value/expression.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     *
     * @param var - the variable to differentiate this expression by.
     * @return - the expression Differential relative to variable `var` .
     */
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /************************************* Compare interface methods ******************************/
    /**
     * compare this expression to given expression ex ,return true if equal.
     *
     * @param ex - the Expression to be compared to this Expression.
     * @return true if ex is equal to this expression,false otherwise.
     */
    @Override
    public boolean expressionComper(Expression ex) {
        if (ex.toString().equals(this.toString())) {
            return true;
        }
        return false;
    }

    /**
     * @return this Expression class name in string form.
     */
    @Override
    public String getClassName() {
        return this.className;
    }

}
