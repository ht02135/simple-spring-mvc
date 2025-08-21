public class RegularSingletonTest {
    public static void main(String[] args) {
        RegularSingleton s1 = RegularSingleton.getInstance();
        RegularSingleton s2 = RegularSingleton.getInstance();

        System.out.println(s1 == s2); // true
        s1.doWork();
    }
}
