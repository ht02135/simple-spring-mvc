
/*
https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you find the non-matching characters in a string? java example
/////////////////////////
Count frequencies using a HashMap<Character, Integer>.
Loop again to check which characters have a count of 1.
Those are the non-matching (unique) characters.
*/

import java.util.HashMap;
import java.util.Map;

public class NonMatchingCharacters {
    public static void main(String[] args) {
        String input = "programming";

        // Step 1: Count frequency of each character
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : input.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        // Step 2: Print characters that occur only once
        System.out.print("Non-matching characters: ");
        for (char ch : input.toCharArray()) {
            if (freqMap.get(ch) == 1) {
                System.out.print(ch + " ");
            }
        }
    }
}