import java.util.List;
import java.util.Map;

/**
 * a Sin class.
 *
 */
public class Sin extends UnaryExpression implements Expression {
    private Expression var;
    private String className = "Sin";
    // ************************************* Sin Expression constructor methods ******************************/

    /**
     * this Sin Expression class constructor with given Expression.
     *
     * @param variable - this Sin var (Expression) .
     */
    Sin(Expression variable) {
        this.var = variable;
    }

    /**
     * this Sin Expression class constructor with given String.
     *
     * @param variable - this Sin var (String) .
     */
    Sin(String variable) {
        this(new Var(variable));
    }

    /**
     * this Sin Expression class constructor with given double.
     *
     * @param variable - this Sin var (double) .
     */
    Sin(double variable) {
        this(new Num(variable));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - the evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Sin Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            result = Math.sin(Math.toRadians(Math.toDegrees(this.var.evaluate(assignment))));
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Sin Expression  " + e.getMessage());
        }
        return result;
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return - the evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate() throws Exception {
        return super.evaluate();
    }

    /**
     * @return - a list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return this.var.getVariables();
    }

    /**
     * @return s - a nice string representation of this expression.
     */
    @Override
    public String toString() {
        if (this.var.toString().contains("(") && (this.var.toString().contains(")"))) {
            return "sin" + this.var.toString();
        }
        return "sin(" + this.var.toString() + ")";
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
        Expression assignedExpression = super.assign(variable, expression);
        return new Sin(assignedExpression);
    }

    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     *
     * @param variable - the variable to differentiate this expression by.
     * @return - the expression Differential relative to variable `var` .
     */
    @Override
    public Expression differentiate(String variable) {
        if (this.getExpression().getVariables().contains(variable)) {
            Cos sinD = new Cos(this.getExpression());
            return new Mult(sinD, this.getExpression().differentiate(variable));
        }
        return new Num(0);
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            double result = this.evaluate();
            if (!Double.toString(result).equals("NaN")) {
                return new Num(result);
            }
        } catch (Exception e) {
            ;
        }
        Expression simpleExpression = getExpression().simplify();
        if (simpleExpression.toString() == "NaN") {
            simpleExpression = getExpression();
        }
        return new Sin(simpleExpression);
    }

    // ************************************* UnaryExpression get methods ******************************/
    /**
     * @return - this expression variable Expression.
     */
    @Override
    public Expression getExpression() {
        return this.var;
    }

    /**
     * checks the given expression ex and returns true if its a number ,false otherwise.
     *
     * @param ex - the given expression to check if its a number.
     * @return true if ex is a number ,false otherwise.
     */
    @Override
    public Boolean isNumber(Expression ex) {
        return super.isNumber(ex);
    }

    // ************************************* Compare interface methods ******************************/
    /**
     * compare this expression to given expression ex ,return true if equal.
     * handles cases where sin(-x) is equal to -sin(x).
     *
     * @param ex - the Expression to be compared to this Expression.
     * @return true if ex is equal to this expression,false otherwise.
     */
    @Override
    public boolean expressionComper(Expression ex) {
        if (ex.getVariables().containsAll(this.getVariables())) {
            String s1 = "sin(" + this.getExpression().toString() + ")";
            String s2 = "sin" + this.getExpression().toString();
            Sin s4 = new Sin("y");
            Sin s5 = new Sin("x");
            // sin(-x)= -sin(x)
            if ((ex.getClassName().equals("Neg")) && (this.getExpression().getClassName().equals("Neg"))) {
                s4 = new Sin(((Sin) ((Neg) ex).getExpression()).getExpression());
                s5 = new Sin(((Neg) this.getExpression()).getExpression());
                if (s5.getExpression().expressionComper(s4.getExpression())) {
                    return true;
                }
            }
            if (this.toString().equals(ex.toString()) || s1.equals(ex.toString()) || s2.equals(ex.toString())) {
                return true;
            }
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
