import java.util.List;
import java.util.Map;

/**
 * a Pow class.
 *
 */
public class Pow extends BinaryExpression implements Expression {
    private Expression base;
    private Expression exponent;
    private String className = "Pow";

    // ************************************* Pow Expression constructor methods ******************************/

    /**
     * this Pow Expression class constructor with given expression,expression.
     *
     * @param leftExpression - this expression base expression (base ^ exponent ).
     * @param rightExpression - this expression exponent expression (base ^ exponent ).
     */
    Pow(Expression leftExpression, Expression rightExpression) {
        this.exponent = rightExpression;
        this.base = leftExpression;
    }

    /**
     * this Pow Expression class constructor with given Expression,String.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(Expression base, String exponent) {
        this(base, new Var(exponent));
    }

    /**
     * this Pow Expression class constructor with given String,Expression.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(String base, Expression exponent) {
        this(new Var(base), exponent);
    }

    /**
     * this Pow Expression class constructor with given Expression,double.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(Expression base, double exponent) {
        this(base, new Num(exponent));
    }

    /**
     * this Pow Expression class constructor with given double,Expression.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(double base, Expression exponent) {
        this(new Num(base), exponent);
    }

    /**
     * this Pow Expression class constructor with given string,string.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(String base, String exponent) {
        this(new Var(base), new Var(exponent));
    }

    /**
     * this Pow Expression class constructor with given double,double.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(double base, double exponent) {
        this(new Num(base), new Num(exponent));
    }

    /**
     * this Pow Expression class constructor with given string,double.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(String base, double exponent) {
        this(new Var(base), new Num(exponent));
    }

    /**
     * this Pow Expression class constructor with given double,string.
     *
     * @param base - this expression base expression (base ^ exponent ).
     * @param exponent - this expression exponent expression (base ^ exponent ).
     */
    Pow(double base, String exponent) {
        this(new Num(base), new Var(exponent));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Pow Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            result = Math.pow(this.getLeftExpression().evaluate(assignment),
                    this.getRightExpression().evaluate(assignment));
        } catch (Exception e) {
            throw new Exception("Failed to evaluate Pow Expression  " + e.getMessage());
        }
        return result;
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate() throws Exception {
        return super.evaluate();
    }

    /**
     * @return s - a nice string representation of this expression.
     */
    @Override
    public String toString() {
        String s = "(" + this.getLeftExpression().toString() + "^" + this.getRightExpression().toString() + ")";
        return s;
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
        Expression newLeft = this.getLeftExpression().assign(var, expression);
        Expression newRight = this.getRightExpression().assign(var, expression);
        Pow assignedExpression = new Pow(newLeft, newRight);
        return assignedExpression;
    }

    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     *
     * @param var - the variable to differentiate this expression by.
     * @return - the expression Differential relative to variable `var` .
     */
    @Override
    public Expression differentiate(String var) {
        Expression leftD = getLeftExpression().differentiate(var);
        Expression rightD = getRightExpression().differentiate(var);
        // differentiate f(t)^g(x) with respect to x -> f(t) dont contain var(x).
        if (getRightExpression().getVariables().contains(var)) {
            return new Mult(this, new Plus(new Mult(rightD, new Log("e", getLeftExpression())),
                    new Div(new Mult(leftD, getRightExpression()), getLeftExpression())));
        }
        // differentiate f(x)^g(t) with respect to x -> f(x)'*g(t)*(f(x)^(g(t)-1)),g(t) dont contain var(x).
        return new Mult(getRightExpression(), new Pow(getLeftExpression(), new Minus(getRightExpression(), 1)));
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression rightEx = getRightExpression().simplify();
        Expression lefttEx = getLeftExpression().simplify();
        // simplfy ((x^y)^z) to (x^(y*z))
        if (lefttEx.getClassName().equals(this.getClassName())) {
            return new Pow(((Pow) lefttEx).getLeftExpression().simplify(),
                    new Mult(((Pow) lefttEx).getRightExpression(), rightEx).simplify());
        }
        if (lefttEx.toString() == "NaN") {
            lefttEx = getLeftExpression();
        }
        if (rightEx.toString() == "NaN") {
            rightEx = getRightExpression();
        }
        try {
            // both Expression are numbers return their result.
            if (this.isNumber(lefttEx) && this.isNumber(rightEx)) {
                return new Num(Math.pow(lefttEx.evaluate(), rightEx.evaluate()));
            }
            // if the right Expression is 0 return number 1 (x^0 = 1 for all x).
            if (rightEx.evaluate() == 0) {
                return new Num(1);
            }
            // if the right Expression is 1 return the left Expression (x^1 = x for all x).
            if (rightEx.evaluate() == 1) {
                return lefttEx;
            }
        } catch (Exception e) {
            ;
        }
        try {
            // if the left Expression is 1 return number 1 (1^x = 1 for all x).
            if (lefttEx.evaluate() == 1) {
                return new Num(1);
            }
            // if the left Expression is 0 return number 0 (0^x = 0 for all x).
            if (lefttEx.evaluate() == 0) {
                return new Num(0);
            }
        } catch (Exception e) {
            ;
        }
        // if the left Expression is -1 return number 1 (for an even exponent) / -1(for an odd exponent).
        try {
            if (lefttEx.evaluate() == -1) {
                if ((rightEx.evaluate() % 2) == 1) {
                    return new Num(-1);
                } else {
                    return new Num(1);
                }
            }
        } catch (Exception e) {
            ;
        }
        return new Pow(lefttEx, rightEx);
    }

    // ************************************* BinaryExpression methods ******************************/

    /**
     * @return - a list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return super.getVariables();
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

    /**
     * @return - this expression base expression (base ^ exponent ).
     */
    @Override
    public Expression getLeftExpression() {
        return this.base;
    }

    /**
     * @return - this expression exponent expression (base ^ exponent ).
     */
    @Override
    public Expression getRightExpression() {
        return this.exponent;
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
            if (ex.getClassName().equals(this.getClassName())) {
                if ((this.getLeftExpression().simplify().expressionComper(((Pow) ex).getLeftExpression().simplify()))
                        && (this.getRightExpression().simplify()
                                .expressionComper(((Pow) ex).getRightExpression().simplify()))) {
                    return true;
                }
            }
            if (ex.toString().equals(this.toString())) {
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
