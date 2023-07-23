import java.util.List;
import java.util.Map;

/**
 * a Log class.
 *
 */
public class Log extends BinaryExpression implements Expression {
    private Expression logBase;
    private Expression var;
    private String className = "Log";

    // ************************************* Log Expression constructor methods ******************************/

    /**
     * this Log Expression class constructor with given expression,expression.
     *
     * @param leftExpression - this expression base expression (log(base) variable ).
     * @param rightExpression - this expression exponent expression (log(base) variable ).
     */
    Log(Expression leftExpression, Expression rightExpression) {
        this.var = rightExpression;
        this.logBase = leftExpression;
    }

    /**
     * this Log Expression class constructor with given Expression,String.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(Expression base, String variable) {
        this(base, new Var(variable));
    }

    /**
     * this Log Expression class constructor with given String,Expression.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(String base, Expression variable) {
        this(new Var(base), variable);
    }

    /**
     * this Log Expression class constructor with given Expression,double.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(Expression base, double variable) {
        this(base, new Num(variable));
    }

    /**
     * this Log Expression class constructor with given double,Expression.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(double base, Expression variable) {
        this(new Num(base), variable);
    }

    /**
     * this Log Expression class constructor with given string,string.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(String base, String variable) {
        this(new Var(base), new Var(variable));
    }

    /**
     * this Log Expression class constructor with given double,double.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(double base, double variable) {
        this(new Num(base), new Num(variable));
    }

    /**
     * this Log Expression class constructor with given string,double.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(String base, double variable) {
        this(new Var(base), new Num(variable));
    }

    /**
     * this Log Expression class constructor with given double,string.
     *
     * @param base - this expression base expression (log(base) variable ).
     * @param variable - this expression variable expression (log(base) variable ).
     */
    Log(double base, String variable) {
        this(new Num(base), new Var(variable));
    }

    /************************************* Expression interface methods ******************************/

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result. If the
     * expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment - the map linking variables to their values.
     * @return result - evaluated answer of this expression.
     * @throws Exception - "Error: Failed to evaluate Log Expression " in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = Double.NaN;
        try {
            if ((this.logBase.evaluate() != 1) && (this.logBase.evaluate() > 0) && (this.var.evaluate() > 0)) {
                double baseLog = Math.log(this.getLeftExpression().evaluate(assignment));
                double varLog = Math.log(this.getRightExpression().evaluate(assignment));
                result = varLog / baseLog;
            }
        } catch (Exception e) {
            throw new Exception("Error: Failed to evaluate Log Expression  " + e.getMessage());
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
        String s = "log(" + this.getLeftExpression().toString() + ", " + this.getRightExpression().toString() + ")";
        return s;
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
        Expression newLeft = this.getLeftExpression().assign(variable, expression);
        Expression newRight = this.getRightExpression().assign(variable, expression);
        Log assignedExpression = new Log(newLeft, newRight);
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
        Expression rightD = getRightExpression().differentiate(variable);
        // differentiate Log(e,f(x))=ln(f(x)) -> f'(x)/f(x).
        if (getLeftExpression().getVariables().contains("e")) {
            return new Div(rightD, getRightExpression());
        }
        // differentiate Log(f(x),f(x))= 1 -> 0.
        if (getLeftExpression().expressionComper(getRightExpression())) {
            return new Num(0);
        }
        // differentiate Log(f(x),g(x)) -> (Log(e,f(x))/Log(e,g(x)))'.
        return new Div(new Log("e", getRightExpression()), new Log("e", getLeftExpression())).differentiate(variable);
    }

    /**
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Expression leftSimple = getLeftExpression().simplify();
        Expression rightSimple = getRightExpression().simplify();

        // log(z,mult(x,y)) = log(z,x)+log(z,y)
        if (rightSimple.getClassName().equals("Mult")) {
            return new Plus(new Log(leftSimple, ((Mult) rightSimple).getLeftExpression()),
                    new Log(leftSimple, ((Mult) rightSimple).getRightExpression()));
        }
        // log(z,Div(x,y)) = log(z,x)-log(z,y)
        if (rightSimple.getClassName().equals("Div")) {
            return new Minus(new Log(leftSimple, ((Div) rightSimple).getLeftExpression()),
                    new Log(leftSimple, ((Div) rightSimple).getRightExpression()));
        }
        // log(z,Pow(x,y)) = y*log(z,x)
        if (rightSimple.getClassName().equals("Pow")) {
            return new Mult(((Pow) rightSimple).getRightExpression(),
                    new Log(leftSimple, ((Pow) rightSimple).getLeftExpression()));
        }

        if (leftSimple.toString() == "NaN") {
            leftSimple = getLeftExpression();
        }
        if (rightSimple.toString() == "NaN") {
            rightSimple = getRightExpression();
        }
        try {
            // if both Expressions are numbers, simplify by calculating Log(num,num).
            if (isNumber(leftSimple) && isNumber(rightSimple)) {
                return new Num(this.evaluate());
            }
            // if both Expression are the same the result of log(x,x) is 1.
            if (leftSimple.expressionComper(rightSimple)) {
                return new Num(1);
            }
            // if the right Expression is 1 then the result of log(x,1) is 0 for all x.
            if (rightSimple.evaluate() == 1) {
                return new Num(0);
            }
        } catch (Exception e) {
            ;
        }
        return new Log(leftSimple, rightSimple);
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
        if (ex.getVariables().containsAll(this.getVariables())) {
            if (ex.getClassName().equals(this.getClassName())) {
                if ((this.getLeftExpression().simplify().expressionComper(((Log) ex).getLeftExpression().simplify()))
                        && (this.getRightExpression().simplify()
                                .expressionComper(((Log) ex).getRightExpression().simplify()))) {
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
     * @return - a list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    /**
     * @return - this expression base expression (log(base) variable ).
     */
    @Override
    public Expression getLeftExpression() {
        return this.logBase;
    }

    /**
     * @return - this expression exponent expression (log(base) variable ).
     */
    @Override
    public Expression getRightExpression() {
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
}
