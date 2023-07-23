import java.util.List;
import java.util.Map;

/**
 * a Div class.
 *
 */
public class Div extends BinaryExpression implements Expression {
    private Expression numerator; // the top part (left Expression).
    private Expression denominator; // the lower part,can`t be 0 (right Expression).
    private String className = "Div";

    /************************************* Div Expression constructor methods ******************************/

    /**
     * this Div Expression class constructor with given expression,expression.
     *
     * @param leftExpression - this expression numerator expression (numerator / denominator ).
     * @param rightExpression - this expression denominator expression (numerator / denominator ).
     */
    Div(Expression leftExpression, Expression rightExpression) {
        this.denominator = rightExpression;
        this.numerator = leftExpression;
    }

    /**
     * this Div Expression class constructor with given expression,String.
     *
     * @param leftExpression - this expression numerator expression (numerator / denominator ).
     * @param right - this expression denominator expression (numerator / denominator ).
     */
    Div(Expression leftExpression, String right) {
        this(leftExpression, new Var(right));
    }

    /**
     * this Div Expression class constructor with given String,expression.
     *
     * @param left - this expression numerator expression (numerator / denominator ).
     * @param rightExpression - this expression denominator expression (numerator / denominator ).
     */
    Div(String left, Expression rightExpression) {
        this(new Var(left), rightExpression);
    }

    /**
     * this Div Expression class constructor with given expression,double.
     *
     * @param leftExpression - this expression numerator expression (numerator / denominator ).
     * @param right - this expression denominator expression (numerator / denominator ).
     */
    Div(Expression leftExpression, double right) {
        this(leftExpression, new Num(right));
    }

    /**
     * this Div Expression class constructor with given double,expression.
     *
     * @param left - this expression numerator expression (numerator / denominator ).
     * @param rightExpression - this expression denominator expression (numerator / denominator ).
     */
    Div(double left, Expression rightExpression) {
        this(new Num(left), rightExpression);
    }

    /**
     * this Div Expression class constructor with given String,String.
     *
     * @param left - this expression numerator expression (numerator / denominator ).
     * @param right - this expression denominator expression (numerator / denominator ).
     */
    Div(String left, String right) {
        this(new Var(left), new Var(right));
    }

    /**
     * this Div Expression class constructor with given double,double.
     *
     * @param left - this expression numerator expression (numerator / denominator ).
     * @param right - this expression denominator expression (numerator / denominator ).
     */
    Div(double left, double right) {
        this(new Num(left), new Num(right));
    }

    /**
     * this Div Expression class constructor with given double,String.
     *
     * @param left - this expression numerator expression (numerator / denominator ).
     * @param right - this expression denominator expression (numerator / denominator ).
     */
    Div(double left, String right) {
        this(new Num(left), new Var(right));
    }

    /**
     * this Div Expression class constructor with given String,double.
     *
     * @param left - this expression numerator expression (numerator / denominator ).
     * @param right - this expression denominator expression (numerator / denominator ).
     */
    Div(String left, double right) {
        this(new Var(left), new Num(right));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Div Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            if (this.getRightExpression().evaluate() != 0) {
                result = (this.getLeftExpression().evaluate(assignment)
                        * (1 / this.getRightExpression().evaluate(assignment)));
            }
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Div Expression " + e.getMessage());
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
        String s = "(" + this.getLeftExpression().toString() + " / " + this.getRightExpression().toString() + ")";
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
        Div assignedExpression = new Div(newLeft, newRight);
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
        // differentiate f(x)/g(x) with respect to x -> (f(x)'*g(x)- g(x)'*f(x))/g(x)^2.
        if (getRightExpression().getVariables().contains(var)) {
            return new Div(new Minus(new Mult(leftD, getRightExpression()), new Mult(rightD, getLeftExpression())),
                    new Pow(getRightExpression(), 2));
        }
        // differentiate f(x)/g(t) with respect to x -> f(x)'/g(t) , (g(t) is an expression that dont contain x ).
        return new Div(leftD, getRightExpression());
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression leftSimple = getLeftExpression().simplify();
        Expression rightSimple = getRightExpression().simplify();
        if (leftSimple.toString() == "NaN") {
            leftSimple = getLeftExpression();
        }
        if (rightSimple.toString() == "NaN") {
            rightSimple = getRightExpression();
        }
        // if both Expression are the same dividing returns 1.
        if (leftSimple.expressionComper(rightSimple)) {
            return new Num(1);
        }
        try {
            // Expression numerator is 0, return number 0 (0/x = 0 for all x).
            if (leftSimple.evaluate() == 0) {
                return new Num(0);
            }
            // both Expression are numbers return their result.
            if (this.isNumber(leftSimple) && this.isNumber(rightSimple)) {
                return new Num(leftSimple.evaluate() / rightSimple.evaluate());
            }
            // Expression denominator is 0, leave x/0.
            if (rightSimple.evaluate() == 0) {
                rightSimple = getRightExpression();
            }
        } catch (Exception e) {
            ;
        }
        try {
            // Expression denominator is 1, return only the left Expression.
            if (rightSimple.evaluate() == 1) {
                return leftSimple;
            }
        } catch (Exception e) {
            ;
        }
        return new Div(leftSimple, rightSimple);
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
                // compare ex and this by their left/right expressions
                if ((this.simplify().toString().equals(ex.simplify().toString())) || (((Div) ex).getRightExpression()
                        .simplify().expressionComper(this.getRightExpression().simplify())
                        && ((Div) ex).getLeftExpression().simplify()
                                .expressionComper(this.getLeftExpression().simplify()))) {
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

    // ************************************* BinaryExpression methods ******************************/
    /**
     * @return - this expression left side expression, numerator (numerator / denominator ).
     */
    @Override
    public Expression getLeftExpression() {
        return this.numerator;
    }

    /**
     * @return - this expression right side expression ,denominator (numerator / denominator ).
     */
    @Override
    public Expression getRightExpression() {
        return this.denominator;
    }

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
}
