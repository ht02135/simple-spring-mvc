/*
1. Regular Singleton Object (Factory Method)
/////////////////
Lifecycle managed inside the class itself.
Global instance for the entire JVM.
*/
// Regular singleton with static factory method
public class RegularSingleton {

    // private static instance
    private static final RegularSingleton instance = new RegularSingleton();

    // private constructor prevents instantiation
    private RegularSingleton() {
        System.out.println("RegularSingleton instance created");
    }

    // static factory method
    public static RegularSingleton getInstance() {
        return instance;
    }

    // example method
    public void doWork() {
        System.out.println("Doing work in RegularSingleton");
    }
}

