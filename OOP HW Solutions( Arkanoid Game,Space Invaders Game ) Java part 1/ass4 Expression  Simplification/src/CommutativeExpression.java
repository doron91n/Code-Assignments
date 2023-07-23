import java.util.Map;

/**
 * a CommutativeExpression class, for (Plus,Mult).
 *
 */
public class CommutativeExpression extends BinaryExpression {
    /**
     * evaluate the expression with given parameters from the assignment map.
     *
     * @param assignment - the map containing variables and their value to assign in this expression.
     * @return the evaluated result, NaN if evaluation is not possible.
     * @throws Exception - "Error: Failed to evaluate Expression" in case the evaluate fails.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return 0;
    }

    /**
     * a simplified version of the given thisEx expression.
     *
     * @param thisEx - the given Mult Expression to simplify.
     * @return a simplified version of the thisEx expression.
     */
    public Expression commutativeMultSimplify(Mult thisEx) {
        Expression thisExL = thisEx.getLeftExpression().simplify();
        Expression thisExR = thisEx.getRightExpression().simplify();
        // simplfy (4*(2*y)) to (8*y)) - all possible combinations.
        if (thisExL.getClassName().equals("Num") && thisExR.getClassName().equals("Mult")) {
            Expression thisExRR = ((Mult) thisExR).getRightExpression();
            Expression thisExRL = ((Mult) thisExR).getLeftExpression();
            if (thisExRL.getClassName().equals("Num")) {
                return new Mult(new Mult(thisExL, thisExRL), thisExRR).simplify();
            }
            if (thisExRR.getClassName().equals("Num")) {
                return new Mult(new Mult(thisExL, thisExRR), thisExRL).simplify();
            }
        }
        // simplfy ((2*y)*4) to (8*y)) - all possible combinations.
        if (thisExR.getClassName().equals("Num") && thisExL.getClassName().equals("Mult")) {
            Expression thisExLL = ((Mult) thisExL).getLeftExpression();
            Expression thisExLR = ((Mult) thisExL).getRightExpression();

            if (thisExLR.getClassName().equals("Num")) {
                return new Mult(new Mult(thisExR, thisExLR), thisExLL).simplify();
            }
            if (thisExLL.getClassName().equals("Num")) {
                return new Mult(new Mult(thisExR, thisExLL), thisExLR).simplify();
            }
        }
        return new Mult(thisExL, thisExR);
    }

    /**
     * a simplified version of the given thisEx expression.
     *
     * @param thisEx - the given Plus Expression to simplify .
     * @return a simplified version of the thisEx expression.
     */
    public Expression commutativePlusSimplify(Plus thisEx) {
        Expression thisExL = thisEx.getLeftExpression().simplify();
        Expression thisExR = thisEx.getRightExpression().simplify();
        // simplfy (4+(2+y)) to (8+y)) - all possible combinations.
        if (thisExL.getClassName().equals("Num") && thisExR.getClassName().equals("Plus")) {
            Expression thisExRR = ((Plus) thisExR).getRightExpression().simplify();
            Expression thisExRL = ((Plus) thisExR).getLeftExpression().simplify();
            try {
                if (thisExRL.getClassName().equals("Num")) {
                    return new Plus(new Plus(thisExL, thisExRL).evaluate(), thisExRR).simplify();
                }
                if (thisExRR.getClassName().equals("Num")) {
                    return new Plus(new Plus(thisExL, thisExRR).evaluate(), thisExRL).simplify();
                }
            } catch (Exception e) {
                ;
            }
        }
        // simplfy ((2+y)+4) to (8+y)) - all possible combinations.
        if (thisExR.getClassName().equals("Num") && thisExL.getClassName().equals("Plus")) {
            Expression thisExLL = ((Plus) thisExL).getLeftExpression().simplify();
            Expression thisExLR = ((Plus) thisExL).getRightExpression().simplify();
            try {
                if (thisExLR.getClassName().equals("Num")) {
                    return new Plus(new Plus(thisExR, thisExLR).evaluate(), thisExLL).simplify();
                }
                if (thisExLL.getClassName().equals("Num")) {
                    return new Plus(new Plus(thisExR, thisExLL).evaluate(), thisExLR).simplify();
                }
            } catch (Exception e) {
                ;
            }
        }
        // ((2x) + (4x)) => 6*x - all possible combinations.
        if (thisExR.getClassName().equals("Mult") && thisExL.getClassName().equals("Mult")) {
            Expression thisExRR = ((Mult) thisExR).getRightExpression().simplify();
            Expression thisExRL = ((Mult) thisExR).getLeftExpression().simplify();
            Expression thisExLL = ((Mult) thisExL).getLeftExpression().simplify();
            Expression thisExLR = ((Mult) thisExL).getRightExpression().simplify();
            if (thisExLR.expressionComper(thisExRR)) {
                if (thisExLL.getClassName().equals("Num") && thisExRL.getClassName().equals("Num")) {
                    return new Mult(new Plus(thisExLL, thisExRL), thisExLR).simplify();
                }
            }
        }
        // (x^3)+(x^3) = 2(x^3)
        if (thisExR.getClassName().equals(thisExL.getClassName())) {
            if (thisExR.expressionComper(thisExL)) {
                return new Mult(2, thisExR).simplify();
            }
        }
        return new Plus(thisExL, thisExR);
    }

    /**
     * compares the given thisEx expression to given expression ex, compare support all commutative options(even
     * nested).
     * example:((x + z) + y) equals (z + (y + x)),((x + z) + (t + y)) equals ((z + t) + (y + x)).
     *
     * @param thisEx - the Expression to compare to Expression ex to.
     * @param ex - the Expression to compare to thisEx Expression.
     * @return true if compared expressions are equal and false otherwise.
     */
    public boolean commutativePlusExpressionComper(Expression ex, Plus thisEx) {
        Plus p1 = thisEx, p2 = thisEx, p3 = thisEx, p4 = thisEx, p5 = thisEx, p6 = thisEx, p7 = thisEx, p8 = thisEx,
                p9 = thisEx, p10 = thisEx, p11 = thisEx, p12 = thisEx;
        Expression thisExL = thisEx.getLeftExpression();
        Expression thisExR = thisEx.getRightExpression();
        // both right and left are Plus , (x+y)+(t+z) = (z+x)+(y+t)
        if (this.getRightExpression().getClassName().equals("Plus")
                && this.getLeftExpression().getClassName().equals("Plus")) {
            Expression exL = ((Plus) ex).getLeftExpression();
            Expression exR = ((Plus) ex).getRightExpression();
            Expression thisExRR = ((Plus) thisEx.getRightExpression()).getRightExpression();
            Expression thisExRL = ((Plus) thisEx.getRightExpression()).getLeftExpression();
            Expression thisExLL = ((Plus) thisEx.getLeftExpression()).getLeftExpression();
            Expression thisExLR = ((Plus) thisEx.getLeftExpression()).getRightExpression();
            p1 = new Plus(thisExLL, thisExRR);
            p2 = new Plus(thisExRL, thisExLR);
            p3 = new Plus(thisExRR, thisExLR);
            p4 = new Plus(thisExLL, thisExRL);
            p5 = new Plus(thisExRR, thisExRL);
            p6 = new Plus(thisExLL, thisExLR);
            if ((p1.expressionComper(exL) && p2.expressionComper(exR))
                    || (p2.expressionComper(exL) && p1.expressionComper(exR))) {
                return true;
            }
            if ((p3.expressionComper(exL) && p4.expressionComper(exR))
                    || (p4.expressionComper(exL) && p3.expressionComper(exR))) {
                return true;
            }
            if ((p5.expressionComper(exL) && p6.expressionComper(exR))
                    || (p6.expressionComper(exL) && p5.expressionComper(exR))) {
                return true;
            }
        }

        // only right expression is plus - checks all combinations.
        if ((thisExR.getClassName().equals(thisEx.getClassName()))
                && (!thisExL.getClassName().equals(thisEx.getClassName()))) {
            Expression thisExRR = ((Plus) thisEx.getRightExpression()).getRightExpression();
            Expression thisExRL = ((Plus) thisEx.getRightExpression()).getLeftExpression();
            p1 = new Plus(thisExRL, new Plus(thisExL, thisExRR));
            p2 = new Plus(thisExRL, new Plus(thisExRR, thisExL));
            p3 = new Plus(thisExRR, new Plus(thisExL, thisExRL));
            p4 = new Plus(thisExRR, new Plus(thisExRL, thisExL));
            p5 = new Plus(thisExL, new Plus(thisExRR, thisExRL));
            p6 = new Plus(thisExL, new Plus(thisExRL, thisExRR));
            p7 = new Plus(new Plus(thisExL, thisExRR), thisExRL);
            p8 = new Plus(new Plus(thisExRR, thisExL), thisExRL);
            p9 = new Plus(new Plus(thisExL, thisExRL), thisExRR);
            p10 = new Plus(new Plus(thisExRL, thisExL), thisExRR);
            p11 = new Plus(new Plus(thisExRR, thisExRL), thisExL);
            p12 = new Plus(new Plus(thisExRL, thisExRR), thisExL);
        }
        // only left expression is plus - checks all combinations
        if ((!thisExR.getClassName().equals(thisEx.getClassName()))
                && (thisExL.getClassName().equals(thisEx.getClassName()))) {
            Expression thisExLL = ((Plus) thisEx.getLeftExpression()).getLeftExpression();
            Expression thisExLR = ((Plus) thisEx.getLeftExpression()).getRightExpression();
            p1 = new Plus(thisExLR, new Plus(thisExR, thisExLL));
            p2 = new Plus(thisExLR, new Plus(thisExLL, thisExR));
            p3 = new Plus(thisExLL, new Plus(thisExR, thisExLR));
            p4 = new Plus(thisExLL, new Plus(thisExLR, thisExR));
            p5 = new Plus(thisExR, new Plus(thisExLL, thisExLR));
            p6 = new Plus(thisExR, new Plus(thisExLR, thisExLL));
            p7 = new Plus(new Plus(thisExR, thisExLL), thisExLR);
            p8 = new Plus(new Plus(thisExLL, thisExR), thisExLR);
            p9 = new Plus(new Plus(thisExR, thisExLR), thisExLL);
            p10 = new Plus(new Plus(thisExLR, thisExR), thisExLL);
            p11 = new Plus(new Plus(thisExLL, thisExLR), thisExR);
            p12 = new Plus(new Plus(thisExLR, thisExLL), thisExR);
        }
        if (p1.toString().equals(ex.toString()) || p2.toString().equals(ex.toString())
                || p3.toString().equals(ex.toString()) || p4.toString().equals(ex.toString())
                || p5.toString().equals(ex.toString()) || p6.toString().equals(ex.toString())
                || p7.toString().equals(ex.toString()) || p8.toString().equals(ex.toString())
                || p9.toString().equals(ex.toString()) || p10.toString().equals(ex.toString())
                || p11.toString().equals(ex.toString()) || p12.toString().equals(ex.toString())) {
            return true;
        }
        return false;
    }

    /**
     * compares the given thisEx expression to given expression ex, compare support all commutative options(even
     * nested).
     * example:((x + z) + y) equals (z + (y + x)),((x + z) + (t + y)) equals ((z + t) + (y + x)).
     *
     * @param thisEx - the Expression to compare to Expression ex to.
     * @param ex - the Expression to compare to thisEx Expression.
     * @return true if compared expressions are equal and false otherwise.
     */
    public boolean commutativeMultExpressionComper(Expression ex, Mult thisEx) {
        Mult p1 = thisEx, p2 = thisEx, p3 = thisEx, p4 = thisEx, p5 = thisEx, p6 = thisEx, p7 = thisEx, p8 = thisEx,
                p9 = thisEx, p10 = thisEx, p11 = thisEx, p12 = thisEx;
        Expression thisExL = thisEx.getLeftExpression();
        Expression thisExR = thisEx.getRightExpression();
        // both right and left are mult , (x*y)*(t*z) = (z*x)*(y*t)
        if (this.getRightExpression().getClassName().equals("Mult")
                && this.getLeftExpression().getClassName().equals("Mult")) {
            Expression thisExLL = ((Mult) thisEx.getLeftExpression()).getLeftExpression();
            Expression thisExLR = ((Mult) thisEx.getLeftExpression()).getRightExpression();
            Expression thisExRR = ((Mult) thisEx.getRightExpression()).getRightExpression();
            Expression thisExRL = ((Mult) thisEx.getRightExpression()).getLeftExpression();
            Expression exL = ((Mult) ex).getLeftExpression();
            Expression exR = ((Mult) ex).getRightExpression();
            p1 = new Mult(thisExLL, thisExRR);
            p2 = new Mult(thisExRL, thisExLR);
            p3 = new Mult(thisExRR, thisExLR);
            p4 = new Mult(thisExLL, thisExRL);
            p5 = new Mult(thisExRR, thisExRL);
            p6 = new Mult(thisExLL, thisExLR);
            if ((p1.expressionComper(exL) && p2.expressionComper(exR))
                    || (p2.expressionComper(exL) && p1.expressionComper(exR))) {
                return true;
            }
            if ((p3.expressionComper(exL) && p4.expressionComper(exR))
                    || (p4.expressionComper(exL) && p3.expressionComper(exR))) {
                return true;
            }
            if ((p5.expressionComper(exL) && p6.expressionComper(exR))
                    || (p6.expressionComper(exL) && p5.expressionComper(exR))) {
                return true;
            }
        }
        // only right expression is Mult - checks all combinations.
        if ((thisExR.getClassName().equals(thisEx.getClassName()))
                && (!thisExL.getClassName().equals(thisEx.getClassName()))) {
            Expression thisExRR = ((Mult) thisEx.getRightExpression()).getRightExpression();
            Expression thisExRL = ((Mult) thisEx.getRightExpression()).getLeftExpression();
            p1 = new Mult(thisExRL, new Mult(thisExL, thisExRR));
            p2 = new Mult(thisExRL, new Mult(thisExRR, thisExL));
            p3 = new Mult(thisExRR, new Mult(thisExL, thisExRL));
            p4 = new Mult(thisExRR, new Mult(thisExRL, thisExL));
            p5 = new Mult(thisExL, new Mult(thisExRR, thisExRL));
            p6 = new Mult(thisExL, new Mult(thisExRL, thisExRR));
            p7 = new Mult(new Mult(thisExL, thisExRR), thisExRL);
            p8 = new Mult(new Mult(thisExRR, thisExL), thisExRL);
            p9 = new Mult(new Mult(thisExL, thisExRL), thisExRR);
            p10 = new Mult(new Mult(thisExRL, thisExL), thisExRR);
            p11 = new Mult(new Mult(thisExRR, thisExRL), thisExL);
            p12 = new Mult(new Mult(thisExRL, thisExRR), thisExL);
        }
        // only left expression is Mult - checks all combinations
        if ((!thisExR.getClassName().equals(thisEx.getClassName()))
                && (thisExL.getClassName().equals(thisEx.getClassName()))) {
            Expression thisExLL = ((Mult) thisEx.getLeftExpression()).getLeftExpression();
            Expression thisExLR = ((Mult) thisEx.getLeftExpression()).getRightExpression();
            p1 = new Mult(thisExLR, new Mult(thisExR, thisExLL));
            p2 = new Mult(thisExLR, new Mult(thisExLL, thisExR));
            p3 = new Mult(thisExLL, new Mult(thisExR, thisExLR));
            p4 = new Mult(thisExLL, new Mult(thisExLR, thisExR));
            p5 = new Mult(thisExR, new Mult(thisExLL, thisExLR));
            p6 = new Mult(thisExR, new Mult(thisExLR, thisExLL));
            p7 = new Mult(new Mult(thisExR, thisExLL), thisExLR);
            p8 = new Mult(new Mult(thisExLL, thisExR), thisExLR);
            p9 = new Mult(new Mult(thisExR, thisExLR), thisExLL);
            p10 = new Mult(new Mult(thisExLR, thisExR), thisExLL);
            p11 = new Mult(new Mult(thisExLL, thisExLR), thisExR);
            p12 = new Mult(new Mult(thisExLR, thisExLL), thisExR);
        }
        // the right Expression is plus - check all combinations.
        if (thisExR.getClassName().equals("Plus")) {
            Expression thisExRR = ((Plus) thisEx.getRightExpression()).getRightExpression();
            Expression thisExRL = ((Plus) thisEx.getRightExpression()).getLeftExpression();
            p1 = new Mult(thisExL, new Plus(thisExRR, thisExRL));
            p2 = new Mult(thisExL, new Plus(thisExRL, thisExRR));
            p3 = new Mult(new Plus(thisExRR, thisExRL), thisExL);
            p4 = new Mult(new Plus(thisExRL, thisExRR), thisExL);
        }
        // the Left Expression is plus - check all combinations.
        if (thisExL.getClassName().equals("Plus")) {
            Expression thisExLL = ((Plus) thisEx.getLeftExpression()).getLeftExpression();
            Expression thisExLR = ((Plus) thisEx.getLeftExpression()).getRightExpression();
            p1 = new Mult(thisExR, new Plus(thisExLR, thisExLL));
            p2 = new Mult(thisExR, new Plus(thisExLL, thisExLR));
            p3 = new Mult(new Plus(thisExLR, thisExLL), thisExR);
            p4 = new Mult(new Plus(thisExLL, thisExLR), thisExR);
        }
        if (p1.toString().equals(ex.toString()) || p2.toString().equals(ex.toString())
                || p3.toString().equals(ex.toString()) || p4.toString().equals(ex.toString())
                || p5.toString().equals(ex.toString()) || p6.toString().equals(ex.toString())
                || p7.toString().equals(ex.toString()) || p8.toString().equals(ex.toString())
                || p9.toString().equals(ex.toString()) || p10.toString().equals(ex.toString())
                || p11.toString().equals(ex.toString()) || p12.toString().equals(ex.toString())) {
            return true;
        }
        return false;
    }
}
