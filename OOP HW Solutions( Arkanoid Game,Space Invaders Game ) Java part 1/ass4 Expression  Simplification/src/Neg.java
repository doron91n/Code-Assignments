import java.util.List;
import java.util.Map;

/**
 * a Neg class.
 *
 */
public class Neg extends UnaryExpression implements Expression {
    private Mult var;
    private String className = "Neg";
    private Expression varExpression;

    // ************************************* Neg Expression constructor methods ******************************/

    /**
     * this Neg Expression class constructor with given Expression,the negative of the given expression.
     *
     * @param variable - this Neg var (Expression) .
     */
    Neg(Expression variable) {
        this.var = new Mult(-1, variable);
        this.varExpression = variable;
    }

    /**
     * this Neg Expression class constructor with given String,the negative of the given String.
     *
     * @param variable - this Neg var (String) .
     */
    Neg(String variable) {
        this(new Var(variable));
    }

    /**
     * this Neg Expression class constructor with given double,the negative of the given double.
     *
     * @param variable - this Neg var (double) .
     */
    Neg(double variable) {
        this(new Num(variable));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result- the evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Neg Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            result = (getExpression().evaluate(assignment));
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Neg Expression  " + e.getMessage());
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
        return getExpression().getVariables();
    }

    /**
     * @return - a nice string representation of this expression.
     */
    @Override
    public String toString() {
        return new String("(-" + this.varExpression.toString() + ")");
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
        return new Neg(assignedExpression);
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
            return new Neg(getExpression().differentiate(variable));
        }
        return new Num(0);
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            double result = this.var.evaluate();
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
        return new Neg(simpleExpression);
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
        if (ex.getVariables().containsAll(this.getVariables())) {
            if (this.toString().equals(ex.toString())) {
                return true;
            }
            if ((ex.getClassName().equals(this.getClassName()))
                    && this.getExpression().expressionComper(((Neg) ex).getExpression())) {
                return true;
            }
        }
        // sin(-x) = -sin(x)
        if (ex.getClassName().equals("Sin") && ((Sin) ex).getExpression().getClassName().equals("Neg")
                && this.getExpression().getClassName().equals("Sin")) {
            if (ex.expressionComper(this)) {
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

    // ************************************* UnaryExpression get methods ******************************/
    /**
     * @return - this expression variable Expression.
     */
    @Override
    public Expression getExpression() {
        return this.varExpression;
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

}
