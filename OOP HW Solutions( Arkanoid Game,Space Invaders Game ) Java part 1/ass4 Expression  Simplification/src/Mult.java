
import java.util.List;
import java.util.Map;

/**
 * a Mult class.
 *
 */
public class Mult extends CommutativeExpression implements Expression {
    private Expression leftE;
    private Expression rightE;
    private String className = "Mult";

    // ************************************* Mult Expression constructor methods ******************************/

    /**
     * this Mult Expression class constructor with given expression,expression.
     *
     * @param leftExpression - this expression left side expression (leftExpression * rightExpression ).
     * @param rightExpression - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(Expression leftExpression, Expression rightExpression) {
        this.rightE = rightExpression;
        this.leftE = leftExpression;
    }

    /**
     * this Mult Expression class constructor with given expression,String.
     *
     * @param leftExpression - this expression left side expression (leftExpression * rightExpression ).
     * @param right - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(Expression leftExpression, String right) {
        this(leftExpression, new Var(right));
    }

    /**
     * this Mult Expression class constructor with given String,expression.
     *
     * @param left - this expression left side expression (leftExpression * rightExpression ).
     * @param rightExpression - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(String left, Expression rightExpression) {
        this(new Var(left), rightExpression);
    }

    /**
     * this Mult Expression class constructor with given expression,double.
     *
     * @param leftExpression - this expression left side expression (leftExpression * rightExpression ).
     * @param right - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(Expression leftExpression, double right) {
        this(leftExpression, new Num(right));
    }

    /**
     * this Mult Expression class constructor with given double,expression.
     *
     * @param left - this expression left side expression (leftExpression * rightExpression ).
     * @param rightExpression - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(double left, Expression rightExpression) {
        this(new Num(left), rightExpression);
    }

    /**
     * this Mult Expression class constructor with given string,string.
     *
     * @param left - this expression left side expression (leftExpression * rightExpression ).
     * @param right - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(String left, String right) {
        this(new Var(left), new Var(right));
    }

    /**
     * this Mult Expression class constructor with given double,double.
     *
     * @param left - this expression left side expression (leftExpression * rightExpression ).
     * @param right - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(double left, double right) {
        this(new Num(left), new Num(right));
    }

    /**
     * this Mult Expression class constructor with given double,string.
     *
     * @param left - this expression left side expression (leftExpression * rightExpression ).
     * @param right - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(double left, String right) {
        this(new Num(left), new Var(right));
    }

    /**
     * this Mult Expression class constructor with given string,double.
     *
     * @param left - this expression left side expression (leftExpression * rightExpression ).
     * @param right - this expression right side expression (leftExpression * rightExpression ).
     */
    Mult(String left, double right) {
        this(new Var(left), new Num(right));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Mult Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            result = (this.getLeftExpression().evaluate(assignment) * this.getRightExpression().evaluate(assignment));
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Mult Expression  " + e.getMessage());
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
        String s = "(" + this.getLeftExpression().toString() + " * " + this.getRightExpression().toString() + ")";
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
        Mult assignedExpression = new Mult(newLeft, newRight);
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
        // differentiate f(x)*g(x) with respect to x -> f(x)'*g(x) + f(x)*g(x)'.
        return new Plus(new Mult(leftD, getRightExpression()), new Mult(rightD, getLeftExpression()));
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression leftSimple = getLeftExpression().simplify();
        Expression rightSimple = getRightExpression().simplify();
        try {
            // both Expression are numbers return their result.
            if (this.isNumber(leftSimple) && this.isNumber(rightSimple)) {
                return new Num(leftSimple.evaluate() * rightSimple.evaluate());
            }
            // if one Expression is 0 return 0.
            if (rightSimple.evaluate() == 0.0) {
                return new Num(0);
            }
            // if one Expression is 1 return the other Expression only.
            if (rightSimple.evaluate() == 1.0) {
                return leftSimple;
            }
        } catch (Exception e) {
            ;
        }
        // commutative Expression Simplify
        if ((leftSimple.getClassName().equals("Num") && rightSimple.getClassName().equals("Mult"))
                || (rightSimple.getClassName().equals("Num") && leftSimple.getClassName().equals("Mult"))) {
            return commutativeMultSimplify(new Mult(leftSimple, rightSimple));
        }
        if (leftSimple.toString() == "NaN") {
            leftSimple = getLeftExpression();
        }
        if (rightSimple.toString() == "NaN") {
            rightSimple = getRightExpression();
        }

        // if one Expression is 0 return 0.
        try {
            if (leftSimple.evaluate() == 0.0) {
                return new Num(0);
            }
            // if one Expression is 1 return the other Expression only.
            if (leftSimple.evaluate() == 1.0) {
                return rightSimple;
            }
        } catch (Exception e) {
            ;
        }
        // keep all numbers on the left side of the Mult Expression.
        if (this.isNumber(rightSimple)) {
            return new Mult(rightSimple, leftSimple);
        }
        return new Mult(leftSimple, rightSimple);
    }

    // ************************************* Compare interface methods ******************************/
    /**
     * compares this expression to given expression, compare support all commutative options(even nested)
     * examples:((x + z) * y) equals (y * (z + x)) || ((x + z) * (y + t)) equals ((t + y) * (z + x)).
     *
     * @param ex - the Expression to compare to this Expression.
     * @return true if ex is equal to this expression,false otherwise.
     */
    @Override
    public boolean expressionComper(Expression ex) {
        if (ex.getVariables().containsAll(this.getVariables())) {
            if (ex.getClassName().equals(this.getClassName())) {
                Var s1 = new Var("(" + getLeftExpression().toString() + " * " + getRightExpression().toString() + ")");
                Var s2 = new Var("(" + getRightExpression().toString() + " * " + getLeftExpression().toString() + ")");
                if (s1.toString().equals(ex.toString()) || s2.toString().equals(ex.toString())
                        || this.toString().equals(ex.toString())) {
                    return true;
                }
                // both right and left Expressions are Plus
                if (((((Mult) ex).getRightExpression().expressionComper(this.getRightExpression()))
                        && (((Mult) ex).getLeftExpression().expressionComper(this.getLeftExpression())))
                        || ((((Mult) ex).getRightExpression().expressionComper(this.getLeftExpression()))
                                && (((Mult) ex).getLeftExpression().expressionComper(this.getRightExpression())))) {
                    return true;
                }
            }
            return commutativeMultExpressionComper(ex, this);
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
     * @return - this expression left side expression (leftExpression * rightExpression ).
     */
    @Override
    public Expression getLeftExpression() {
        return this.leftE;
    }

    /**
     * @return - this expression right side expression (leftExpression * rightExpression ).
     */
    @Override
    public Expression getRightExpression() {
        return this.rightE;
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
