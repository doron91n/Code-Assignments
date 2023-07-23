
/**
 * a UnaryExpression class (super class for: Cos,Sin,Neg).
 *
 */
public abstract class UnaryExpression extends BaseExpression {

    private Expression var;

    // ************************************* BaseExpression methods ******************************/
    /**
     * checks the given expression ex and returns true if its a number ,false otherwise.
     *
     * @param ex - the given expression to check if its a number.
     * @return true if ex is a number ,false otherwise.
     */
    @Override
    protected Boolean isNumber(Expression ex) {
        return super.isNumber(ex);
    }

    // ************************************* UnaryExpression get methods ******************************/

    /**
     * @return - this expression variable Expression.
     */
    protected Expression getExpression() {
        return this.var;
    }

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the provided expression
     * (Does not modify the current expression).
     *
     * @param vari - the variable to replace with the provided expression.
     * @param expression - the expression to replace with the provided variable.
     * @return assignedExpression - the newly modified expression after replacing the given var to the given expression.
     */
    protected Expression assign(String vari, Expression expression) {
        Expression assignedExpression = this.getExpression().assign(vari, expression);
        return assignedExpression;
    }
}
