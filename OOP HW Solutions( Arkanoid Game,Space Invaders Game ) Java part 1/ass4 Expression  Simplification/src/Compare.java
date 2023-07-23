/**
 * a Compare interface.
 *
 */
public interface Compare {
    /**
     * compare this expression to given expression ex ,return true if equal.
     *
     * @param ex - the Expression to be compared to this Expression.
     * @return true if ex is equal to this expression,false otherwise.
     */
    boolean expressionComper(Expression ex);

    /**
     * @return this Expression class name in string form.
     */
    String getClassName();
}
