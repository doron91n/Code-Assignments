/**
 * the simplfy example.
 *
 */
public class SimplificationDemo {
    /**
     * main runs example.
     *
     * @param args - not used.
     */
    public static void main(String[] args) {
        // ((2x) + (4x)) => 6*x
        Expression ex1 = new Plus(new Mult(2, "x"), new Mult("x", 4));
        // (x^y)^z => x^(y*z)
        Expression ex2 = new Pow(new Pow("x", "y"), "z");
        // ((x + y) + t))-(y + (t + x)) = 0
        Expression ex3 = new Minus(new Plus(new Plus("x", "y"), new Plus("t", "z")),
                new Plus(new Plus("z", "y"), new Plus("t", "x")));
        // ((x * y) * t))-((y * (t * x)) = 0
        Expression ex4 = new Minus(new Mult(new Mult("x", "y"), new Mult("t", "z")),
                new Mult(new Mult("z", "y"), new Mult("t", "x")));
        // log(z,x^3) = 3 log(z,x)
        Expression ex5 = new Log("z", new Pow("x", 3));
        // log(z,x*3) = log(z,x) + log(z,3)
        Expression ex6 = new Log("z", new Mult("x", 3));
        // log(z,x/3) = log(z,x) - log(z,3)
        Expression ex7 = new Log("z", new Div("x", 3));
        // (-1)^200 = 1
        Expression ex8 = new Pow(-1, 200);
        // (-1)^201 = -1
        Expression ex9 = new Pow(-1, 201);
        // (1)^201 = 1
        Expression ex10 = new Pow(1, 201);
        // (5)^0 = 1
        Expression ex11 = new Pow(5, 0);
        // (0)^5 = 0
        Expression ex12 = new Pow(0, 5);
        // ((2*y)*4) to (8*y))
        Expression ex13 = new Mult(new Mult(2, "y"), 4);
        // ((2+y)+4) to (8+y))
        Expression ex14 = new Plus(new Plus(2, "y"), 4);
        // (x^3)+(x^3) = 2(x^3)
        Expression ex15 = new Plus(new Pow("x", 3), new Pow("x", 3));
        // sin(-x) - (- sin(x)) = 0 , because sin(-x) = -sin(x)
        Expression ex16 = new Minus(new Sin(new Neg("x")), new Neg(new Sin("x")));
        // cos(x) - cos(-x) = 0, because cos(x) = cos(-x)
        Expression ex17 = new Minus(new Cos(new Neg("x")), new Cos("x"));
        // ((2.0 + 5.0) + (4.0 + 4.0)) simplfy to: 15.0
        Expression ex18 = new Plus(new Plus(2, 5), new Plus(4, 4));
        // (x - (-y)) simplfy to: (x + y)
        Expression ex19 = new Minus("x", new Neg("y"));
        // (((2.0 * x) + (x * 4.0)) + ((2.0 * x) + (x * 4.0))) simplfy to: (12.0 * x)
        Expression ex20 = new Plus(new Plus(new Mult(2, "x"), new Mult("x", 4)),
                new Plus(new Mult(2, "x"), new Mult("x", 4)));
        System.out.println("ex1  " + ex1 + "   simplfy to:  " + ex1.simplify());
        System.out.println("ex2  " + ex2 + "   simplfy to:  " + ex2.simplify());
        System.out.println("ex3  " + ex3 + "   simplfy to:  " + ex3.simplify());
        System.out.println("ex4  " + ex4 + "   simplfy to:  " + ex4.simplify());
        System.out.println("ex5  " + ex5 + "   simplfy to:  " + ex5.simplify());
        System.out.println("ex6  " + ex6 + "   simplfy to:  " + ex6.simplify());
        System.out.println("ex7  " + ex7 + "   simplfy to:  " + ex7.simplify());
        System.out.println("ex8  " + ex8 + "   simplfy to:  " + ex8.simplify());
        System.out.println("ex9  " + ex9 + "   simplfy to:  " + ex9.simplify());
        System.out.println("ex10  " + ex10 + "   simplfy to:  " + ex10.simplify());
        System.out.println("ex11  " + ex11 + "   simplfy to:  " + ex11.simplify());
        System.out.println("ex12  " + ex12 + "   simplfy to:  " + ex12.simplify());
        System.out.println("ex13  " + ex13 + "   simplfy to:  " + ex13.simplify());
        System.out.println("ex14  " + ex14 + "   simplfy to:  " + ex14.simplify());
        System.out.println("ex15  " + ex15 + "   simplfy to:  " + ex15.simplify());
        System.out.println("ex16  " + ex16 + "   simplfy to:  " + ex16.simplify());
        System.out.println("ex17  " + ex17 + "   simplfy to:  " + ex17.simplify());
        System.out.println("ex18  " + ex18 + "   simplfy to:  " + ex18.simplify());
        System.out.println("ex19  " + ex19 + "   simplfy to:  " + ex19.simplify());
        System.out.println("ex20  " + ex20 + "   simplfy to:  " + ex20.simplify());

    }
}
