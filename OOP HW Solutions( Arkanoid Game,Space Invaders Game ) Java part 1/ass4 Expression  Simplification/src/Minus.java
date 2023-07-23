import java.util.List;
import java.util.Map;

/**
 * a Minus class.
 *
 */
public class Minus extends BinaryExpression implements Expression {
    private Expression leftE;
    private Expression rightE;
    private String className = "Minus";

    /************************************* Minus Expression constructor methods ******************************/

    /**
     * this Minus Expression class constructor with given expression,expression.
     *
     * @param leftExpression - this expression left side expression (leftExpression - rightExpression ).
     * @param rightExpression - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(Expression leftExpression, Expression rightExpression) {
        this.rightE = rightExpression;
        this.leftE = leftExpression;
    }

    /**
     * this Minus Expression class constructor with given expression,String.
     *
     * @param leftExpression - this expression left side expression (leftExpression - rightExpression ).
     * @param right - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(Expression leftExpression, String right) {
        this(leftExpression, new Var(right));
    }

    /**
     * this Minus Expression class constructor with given String,expression.
     *
     * @param left - this expression left side expression (leftExpression - rightExpression ).
     * @param rightExpression - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(String left, Expression rightExpression) {
        this(new Var(left), rightExpression);
    }

    /**
     * this Minus Expression class constructor with given expression,double.
     *
     * @param leftExpression - this expression left side expression (leftExpression - rightExpression ).
     * @param right - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(Expression leftExpression, double right) {
        this(leftExpression, new Num(right));
    }

    /**
     * this Minus Expression class constructor with given double,expression.
     *
     * @param left - this expression left side expression (leftExpression - rightExpression ).
     * @param rightExpression - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(double left, Expression rightExpression) {
        this(new Num(left), rightExpression);
    }

    /**
     * this Minus Expression class constructor with given string,string.
     *
     * @param left - this expression left side expression (leftExpression - rightExpression ).
     * @param right - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(String left, String right) {
        this(new Var(left), new Var(right));
    }

    /**
     * this Minus Expression class constructor with given double,double.
     *
     * @param left - this expression left side expression (leftExpression - rightExpression ).
     * @param right - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(double left, double right) {
        this(new Num(left), new Num(right));
    }

    /**
     * this Minus Expression class constructor with given double,string.
     *
     * @param left - this expression left side expression (leftExpression - rightExpression ).
     * @param right - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(double left, String right) {
        this(new Num(left), new Var(right));
    }

    /**
     * this Minus Expression class constructor with given string,double.
     *
     * @param left - this expression left side expression (leftExpression - rightExpression ).
     * @param right - this expression right side expression (leftExpression - rightExpression ).
     */
    Minus(String left, double right) {
        this(new Var(left), new Num(right));
    }

    // ************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Minus Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            result = (this.getLeftExpression().evaluate(assignment) - this.getRightExpression().evaluate(assignment));
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Minus Expression  " + e.getMessage());
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
        String s = "(" + this.getLeftExpression().toString() + " - " + this.getRightExpression().toString() + ")";
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
        Minus assignedExpression = new Minus(newLeft, newRight);
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
        return new Minus(getLeftExpression().differentiate(var), getRightExpression().differentiate(var));
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression rightEx = getRightExpression().simplify();
        Expression lefttEx = getLeftExpression().simplify();
        if (lefttEx.toString() == "NaN") {
            lefttEx = getLeftExpression();
        }
        if (rightEx.toString() == "NaN") {
            rightEx = getRightExpression();
        }
        try {
            // if both Expression are numbers return their result.
            if (this.isNumber(rightEx) && this.isNumber(lefttEx)) {
                return new Num(lefttEx.evaluate() - rightEx.evaluate());
            }
            // if the left Expression is 0 remove the 0 and leave negative of the other Expression only.
            if (lefttEx.evaluate() == 0) {
                return new Neg(rightEx);
            }
        } catch (Exception e) {
            ;
        }
        try {
            // if both Expression are the same return 0.
            if (lefttEx.expressionComper(rightEx)) {
                return new Num(0);
            }
            // if the right Expression is 0 remove the 0 and leave the other Expression only.
            if (rightEx.evaluate() == 0) {
                return lefttEx;
            }
        } catch (Exception e) {
            ;
        }
        // (x-(-y))=(x+y)
        if (rightEx.getClassName().equals("Neg")) {
            return new Plus(lefttEx, ((Neg) rightEx).getExpression());
        }
        return new Minus(lefttEx, rightEx);
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
                if ((this.getLeftExpression().simplify().expressionComper(((Minus) ex).getLeftExpression().simplify()))
                        && (this.getRightExpression().simplify()
                                .expressionComper(((Minus) ex).getRightExpression().simplify()))) {
                    return true;
                }
                if (this.toString().equals(ex.toString())) {
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
     * @return - this expression left side expression (leftExpression - rightExpression ).
     */
    @Override
    public Expression getLeftExpression() {
        return this.leftE;
    }

    /**
     * @return - this expression right side expression (leftExpression - rightExpression ).
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
