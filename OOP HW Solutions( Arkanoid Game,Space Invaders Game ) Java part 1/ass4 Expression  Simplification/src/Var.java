import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * a Var class.
 *
 */
public class Var implements Expression {
    private String var;
    private String className = "Var";

    /**
     * this Var Expression class constructor with given string.
     *
     * @param variable - this variable var (string) .
     */
    Var(String variable) {
        this.var = variable;
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return newVar - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Var Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double newVar = Double.NaN;
        if (!assignment.isEmpty()) {
            if (assignment.containsKey(this.toString())) {
                newVar = assignment.get(this.toString());
            } else {
                throw new Exception("Error: Failed to evaluate Var Expression - no matching Var found ");
            }
        }
        return newVar;
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return - evaluated answer of this expression, a NaN value.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate() throws Exception {
        if (this.var.equals("e")) {
            return Math.E;
        }
        throw new Exception("Error: Failed to evaluate Var Expression - cannot calculate a var without its value");
    }

    /**
     * @return varList - a list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        List<String> varList = new ArrayList<String>();
        varList.add(this.toString());
        return varList;
    }

    /**
     * @return s - a nice string representation of this expression.
     */
    @Override
    public String toString() {
        return this.var;
    }

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression
     * (Does not modify the current expression).
     *
     * @param variable - the variable to replace with the provided expression.
     * @param expression - the expression to replace with the provided variable.
     * @return assignedExpression - the newly modified expression after replacing the given var to the given expression.
     */
    @Override
    public Expression assign(String variable, Expression expression) {
        Expression assignedExpression = this;
        if (variable.equals(this.toString())) {
            assignedExpression = expression;
        }
        return assignedExpression;
    }

    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     *
     * @param variable - the variable to differentiate this expression by.
     * @return - the expression Differential relative to variable `var` .
     */
    @Override
    public Expression differentiate(String variable) {
        if (this.var.equals(variable)) {
            return new Num(1);
        }
        return new Num(0);
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        return this;
    }

    // ************************************* Compare interface methods ******************************/
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
