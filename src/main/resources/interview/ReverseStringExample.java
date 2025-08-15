
/*
 * https://pigmancareers.uky.edu/blog/2024/08/04/45-common-coding-interview-questions/
How do you reverse a string java example
*/

//1. Using StringBuilder.reverse()
public class ReverseStringExample {
    public static void main(String[] args) {
        String original = "Hello World";
        
        // StringBuilder has a built-in reverse method
        String reversed = new StringBuilder(original).reverse().toString();
        
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);
    }
}

//2. Using a Loop
public class ReverseStringLoop {
    public static void main(String[] args) {
        String original = "Hello World";
        String reversed = "";

        for (int i = original.length() - 1; i >= 0; i--) {
            reversed += original.charAt(i); // Not the most efficient
        }

        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);
    }
}

//3. Using a Char Array
public class ReverseStringArray {
    public static void main(String[] args) {
        String original = "Hello World";
        
        char[] chars = original.toCharArray();
        int left = 0, right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        String reversed = new String(chars);
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);
    }
}



