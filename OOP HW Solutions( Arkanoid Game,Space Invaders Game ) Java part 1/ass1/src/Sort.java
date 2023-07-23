
public class Sort {
    /**
     * The main function , gets a list of numbers and a desired sorting order in the commandline, sorts the numbers and
     * prints the result according to the choosen sorting order.
     *
     * @param args - cmd line args (a list of numbers and desired sorting order).
     */
    public static void main(String[] args) {
        /* checks that arguments where entered. */
        if (args.length <= 1) {
            System.out.println("Error: no valid number of arguments entered");
        } else {
            /* a check for the first argument being the desired sorting order. */
            if ((!"asc".equals(args[0])) && (!"desc".equals(args[0]))) {
                System.out.println(
                        "the first argument needs to be the desired order" + "(asc -> ascending or desc -> desending ");
            } else {
                /*
                 * create a new String array and insert all the args provided(except the first one -> the sorting order)
                 * into it.
                 */
                String[] arrayS = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    arrayS[i - 1] = args[i];
                }
                /*
                 * create a int array and using the StringsToInts function and enter all the args from the String array
                 * into it.
                 */
                int[] arrayN = new int[args.length - 1];
                arrayN = stringsToInts(arrayS);
                /* send the array to sorting */
                arraySort(arrayN, args[0]);
                /* printing the array */
                arrayPrint(arrayN);
            }
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
     * The function , gets a (int) array of numbers and sorts it based of the choosen sorting order(ascending ||
     * descending).
     *
     * @param numbers - a int array of numbers.
     * @param order - the order to sort by (ascending || descending).
     * @return numbers - a sorted (int) version of the array.
     */
    public static int[] arraySort(int[] numbers, String order) {
        int temp = 0;
        /* sorting the array using bubble sort algoritem based on the desired sorting order (ascending || descending */
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 1; j < (numbers.length - i); j++) {
                if ("asc".equals(order)) {
                    /* sort in ascending order */
                    if (numbers[j - 1] > numbers[j]) {
                        temp = numbers[j - 1];
                        numbers[j - 1] = numbers[j];
                        numbers[j] = temp;
                    }
                } else {
                    /* sort in descending order */
                    if (numbers[j] > numbers[j - 1]) {
                        temp = numbers[j];
                        numbers[j] = numbers[j - 1];
                        numbers[j - 1] = temp;
                    }
                }
            }
        }
        return (numbers);
    }

    /**
     * The function , gets a (int) array of numbers, then prints the array numbers in one line.
     *
     * @param numbers - a int array of numbers.
     */
    public static void arrayPrint(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println("");
    }
}