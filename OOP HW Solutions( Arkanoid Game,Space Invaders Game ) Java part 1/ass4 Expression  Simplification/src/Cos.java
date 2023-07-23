import java.util.List;
import java.util.Map;

/**
 * a Cos class.
 *
 */
public class Cos extends UnaryExpression implements Expression {
    private Expression variable;
    private String className = "Cos";
    // ************************************* Cos Expression constructor methods ******************************/

    /**
     * this Cos Expression class constructor with given Expression.
     *
     * @param var - this Cos var (Expression) .
     */
    Cos(Expression var) {
        this.variable = var;
    }

    /**
     * this Cos Expression class constructor with given String.
     *
     * @param var - this Cos var (String) .
     */
    Cos(String var) {
        this(new Var(var));
    }

    /**
     * this Cos Expression class constructor with given double.
     *
     * @param var - this Cos var (double) .
     */
    Cos(double var) {
        this(new Num(var));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - the evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Cos Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            result = Math.cos(Math.toRadians(Math.toDegrees(this.getExpression().evaluate(assignment))));
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Cos Expression  " + e.getMessage());
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
        return this.getExpression().getVariables();
    }

    /**
     * @return s - a nice string representation of this expression.
     */
    @Override
    public String toString() {
        if (this.getExpression().toString().contains("(") && (this.getExpression().toString().contains(")"))) {
            return "cos" + this.getExpression().toString();
        }
        return "cos(" + this.getExpression().toString() + ")";
    }

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression
     * (Does not modify the current expression).
     *
     * @param var - the variable to replace with the provided expression.
     * @param expression - the expression to replace with the provided variable.
     * @return assignedExpression - the newly modified expression after replacing the given var to the given expression.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        Expression assignedExpression = super.assign(var, expression);
        return new Cos(assignedExpression);
    }

    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     *
     * @param var - the variable to differentiate this expression by.
     * @return - the expression Differential relative to variable `var` .
     */
    @Override
    public Expression differentiate(String var) {
        if (this.getExpression().getVariables().contains(var)) {
            Neg cosD = new Neg(new Sin(this.getExpression()));
            return new Mult(cosD, this.getExpression().differentiate(var));
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
        return new Cos(simpleExpression);
    }

    // ************************************* Compare interface methods ******************************/
    /**
     * compare this expression to given expression ex ,return true if equal.
     * handles cases where cos(-x) is equal to cos(x).
     *
     * @param ex - the Expression to be compared to this Expression.
     * @return true if ex is equal to this expression,false otherwise.
     */
    @Override
    public boolean expressionComper(Expression ex) {
        if (ex.getVariables().containsAll(this.getVariables())) {
            if (ex.getClassName().equals(this.getClassName())) {
                String s1 = "cos(" + new Neg(this.getExpression()).toString() + ")";
                String s2 = "cos" + new Neg(this.getExpression()).toString();
                String s3 = "cos(" + this.getExpression().toString() + ")";
                String s4 = "cos" + this.getExpression().toString();
                String s5 = "cos(" + new Neg(((Cos) ex).getExpression()).toString() + ")";
                String s6 = "cos" + new Neg(((Cos) ex).getExpression()).toString();
                String s7 = "cos(" + ((Cos) ex).getExpression().toString() + ")";
                String s8 = "cos" + ((Cos) ex).getExpression().toString();
                // cos(-x) = cos(x)
                if ((this.toString().equals(ex.toString())) || (this.toString().equals(s5))
                        || (this.toString().equals(s6)) || (this.toString().equals(s7)) || (this.toString().equals(s8))
                        || (ex.toString().equals(s1)) || (ex.toString().equals(s2)) || (ex.toString().equals(s3))
                        || (ex.toString().equals(s4))) {
                    return true;
                }
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
        return this.variable;
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
