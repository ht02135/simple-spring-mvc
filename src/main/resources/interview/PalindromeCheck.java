
/*
https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you determine if a string is a palindrome? java example
///////////////////////////
Normalize case (toLowerCase()) so "Racecar" equals "racecar".
Reverse the string using StringBuilder.reverse().
Compare the original (normalized) string with the reversed string.
If they match → it’s a palindrome.
*/
public class PalindromeCheck {
    public static void main(String[] args) {
        String str = "Racecar";

        // Convert to lowercase for case-insensitive comparison
        String lowerStr = str.toLowerCase();

        // Reverse the string
        String reversed = new StringBuilder(lowerStr).reverse().toString();

        // Check if it's a palindrome
        if (lowerStr.equals(reversed)) {
            System.out.println(str + " is a palindrome.");
        } else {
            System.out.println(str + " is NOT a palindrome.");
        }
    }
}