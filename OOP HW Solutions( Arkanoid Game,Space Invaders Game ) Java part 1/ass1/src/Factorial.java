
public class Factorial {
    /**
     * The main function , gets a number (n) from user and prints the factorial
     * value of the number by calling the appropriate functions in recursive and
     * iterative form.
     * @param args -  n (long) the number we want the factorial value of.
     */
    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        if (n < 0) {
            System.out.println("number can`t be negative");
        } else {
            System.out.println("recursive: " + factorialRecursive(n));
            System.out.println("iterative: " + factorialIter(n));
        }
    }

    /**
     * calculates the factorial of recived number in iterative form.
     * @param n - (long) the number we want to calculate the factorial for.
     * @return iterativeFact - the calculated factorial.
     */
    public static long factorialIter(long n) {
        long iterativeFact = 1;
        if (n == 0) {
            iterativeFact = 1;
        } else {
            for (int i = 1; i <= n; i++) {
                iterativeFact *= i;
            }
        }
        return iterativeFact;
    }

    /**
     * calculates the factorial of recived number in recursive form.
     * @param n - (long) the number we want to calculate the factorial for.
     * @return this function calls itself recursively therefor at the end
     *         returns the calculated factorial.
     */
    public static long factorialRecursive(long n) {
        if (n <= 1) {
            return (1);
        } else {
            return (factorialRecursive(n - 1) * n);
        }
    }
}
