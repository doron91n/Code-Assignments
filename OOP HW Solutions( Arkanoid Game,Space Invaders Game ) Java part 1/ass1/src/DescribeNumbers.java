
public class DescribeNumbers {
    /**
     * The main function , gets a list of numbers in the commandline, and prints their minimum, maximum, and average
     * values.
     *
     * @param args - cmd line args (a list of numbers).
     */
    public static void main(String[] args) {
        /* checks that arguments where entered. */
        if (args.length < 1) {
            System.out.println("Error: no arguments entered");
        } else {
            /*
             * create a new String array and insert all the args provided into it.
             */
            String[] arrayS = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                arrayS[i] = args[i];
            }
            /*
             * create a int array and using the StringsToInts function enter all the args from the String array into it.
             */
            int[] arrayN = new int[args.length];
            arrayN = stringsToInts(arrayS);
            /*
             * prints using the appropriate functions the minimum, maximum, and average values of the numbers in arrayN.
             */
            System.out.println("min: " + min(arrayN));
            System.out.println("max: " + max(arrayN));
            System.out.println("avg: " + avg(arrayN));
        }
    }

    /**
     * The function , gets a (String) array of numbers,creates a new (int) array and insertes the (String) numbers into
     * it.
     *
     * @param numbers - a String array (numbers).
     * @return numbersArray - a converted (int) version of the String array.
     */
    public static int[] stringsToInts(String[] numbers) {
        int[] numbersArray = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numbersArray[i] = Integer.parseInt(numbers[i]);
        }
        return (numbersArray);
    }

    /**
     * The function , gets a array of numbers and returns the minimum number among them.
     *
     * @param numbers - a (int) array of numbers.
     * @return minimum - the minimum number in numbers array.
     */
    public static int min(int[] numbers) {
        int minimum = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (minimum > numbers[i]) {
                minimum = numbers[i];
            }
        }
        return minimum;
    }

    /**
     * The function , gets a array of numbers and returns the maximum number among them.
     *
     * @param numbers - a (int) array of numbers.
     * @return maximum - the maximum number in numbers array.
     */
    public static int max(int[] numbers) {
        int maximum = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (maximum < numbers[i]) {
                maximum = numbers[i];
            }
        }
        return maximum;
    }

    /**
     * The function , gets a array of numbers calculates and returns the average value of them all.
     *
     * @param numbers - a (int) array of numbers.
     * @return average - the average (float) value of the numbers array.
     */
    public static float avg(int[] numbers) {
        float average = 0;
        for (int i = 0; i < numbers.length; i++) {
            average += numbers[i];
        }
        average = (average / numbers.length);
        return average;
    }
}
