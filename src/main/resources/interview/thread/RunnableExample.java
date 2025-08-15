
/*
RunnableExample.java
/////////////////////
Runnable
Package: java.lang
Purpose: Represents a task that can be run.
Return value: No return value (the method is void run()).
Checked exceptions: Cannot throw checked exceptions directly from run().
Usage: Often used with Thread or ExecutorService when you don't need a result.
///////////////////
Runnable Interface
Available since Java 1.0 (in java.lang package).
Used to execute code on a concurrent thread.
Does not return any result and cannot throw checked exceptions.
Method signature:
void run();
*/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableExample {
    public static void main(String[] args) {
    	/*
    	you are overriding the run() method of the Runnable interface, just in a shorter, 
    	lambda expression form.
    	*/
        Runnable task = () -> {
            System.out.println("Runnable task is running on thread: " + Thread.currentThread().getName());
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task); // No return value
        executor.shutdown();
    }
}
