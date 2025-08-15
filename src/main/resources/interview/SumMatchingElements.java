
/*
https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you total all of the matching integer elements in an array? java eample
/////////////////
We loop through the array.
For each number, we add it to its running total in the HashMap.
If it doesn’t exist yet, we start from 0.
At the end, each key in the map has the sum of all its matching values from the array.
*/

import java.util.HashMap;
import java.util.Map;

public class SumMatchingElements {
    public static void main(String[] args) {
        int[] numbers = {2, 3, 2, 5, 3, 2, 5};

        // Map to store number -> sum of occurrences
        Map<Integer, Integer> sumMap = new HashMap<>();

        for (int num : numbers) {
            sumMap.put(num, sumMap.getOrDefault(num, 0) + num);
        }

        // Display results
        for (Map.Entry<Integer, Integer> entry : sumMap.entrySet()) {
            System.out.println("Number: " + entry.getKey() + " | Total Sum: " + entry.getValue());
        }
    }
}