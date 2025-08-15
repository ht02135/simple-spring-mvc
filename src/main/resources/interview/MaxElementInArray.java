
/*
https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you find the maximum element in an array? java example
////////////////////
Assume the first element is the maximum (max = numbers[0]).
Loop from the second element onward.
If the current element is greater than max, update max.
After the loop, max contains the largest value in the array.
*/

public class MaxElementInArray {
    public static void main(String[] args) {
        int[] numbers = {10, 45, 2, 99, 56, 78};

        int max = numbers[0]; // Start with the first element

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i]; // Update max if a larger value is found
            }
        }

        System.out.println("Maximum element is: " + max);
    }
}

/*
you want to sort the array in descending order and then pick the first element (which will be the maximum).
*/
public class MaxElementBySorting {
    public static void main(String[] args) {
        Integer[] numbers = {10, 45, 2, 99, 56, 78}; // Integer, not int, for Collections.reverseOrder()

        // Sort in descending order
        Arrays.sort(numbers, Collections.reverseOrder());

        // First element is the maximum
        int max = numbers[0];

        System.out.println("Sorted array (desc): " + Arrays.toString(numbers));
        System.out.println("Maximum element: " + max);
    }
}



