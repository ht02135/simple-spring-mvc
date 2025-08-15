
/*
https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you calculate the number of numerical digits in a string? java example
/////////////////////////
Loop through each character in the string.
Use Character.isDigit(char) to check if the character is 0-9.
Increment the counter when it is a digit.
Return the count.
*/

public class DigitCounter {
    public static void main(String[] args) {
        String text = "Hello123World456";
        int digitCount = countDigits(text);

        System.out.println("Number of numerical digits: " + digitCount);
    }

    public static int countDigits(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}