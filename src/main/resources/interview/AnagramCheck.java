
/*
https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you find out if the two given strings are anagrams? java example
/////////////////////////////
Remove whitespace and convert both strings to the same case (e.g., lowercase).
Convert the strings to character arrays.
Sort both arrays.
Compare the sorted arrays for equality.
*/

import java.util.Arrays;

public class AnagramCheck {
    public static boolean areAnagrams(String str1, String str2) {
        // Remove spaces and make lowercase
    	// In Java, \\s is a predefined character class that matches a single whitespace character
        str1 = str1.replaceAll("\\s", "").toLowerCase();
        str2 = str2.replaceAll("\\s", "").toLowerCase();

        // If lengths differ, they can't be anagrams
        if (str1.length() != str2.length()) {
            return false;
        }

        // Convert to char arrays and sort
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        // Compare sorted arrays
        return Arrays.equals(arr1, arr2);
    }

    public static void main(String[] args) {
        String s1 = "Listen";
        String s2 = "Silent";

        if (areAnagrams(s1, s2)) {
            System.out.println(s1 + " and " + s2 + " are anagrams.");
        } else {
            System.out.println(s1 + " and " + s2 + " are NOT anagrams.");
        }
    }
}