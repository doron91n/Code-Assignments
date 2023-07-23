import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * a BinaryExpression class (super class for: Plus,Minus,Pow,Log,Div,Mult).
 *
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression leftE;
    private Expression rightE;

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

    // ************************************* BinaryExpression get methods ******************************/

    /**
     * @return - this expression left side expression (leftExpression , rightExpression ).
     */
    protected Expression getLeftExpression() {
        return this.leftE;
    }

    /**
     * @return - this expression right side expression (leftExpression , rightExpression ).
     */
    protected Expression getRightExpression() {
        return this.rightE;
    }

    /**
     * @return varList - a list of the variables in the expression.
     */
    protected List<String> getVariables() {
        List<String> varList = new ArrayList<String>();
        Set<String> varSet = new HashSet<String>();
        varSet.addAll(this.getLeftExpression().getVariables());
        varSet.addAll(this.getRightExpression().getVariables());
        varList.clear();
        varList.addAll(varSet);
        return varList;
    }
}
