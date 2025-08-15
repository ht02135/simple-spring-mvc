
/*
CallableExample.java
/////////////////////
Callable
Package: java.util.concurrent
Purpose: Represents a task that can be run and returns a result.
Return value: Has a return type (V call() returns a value).
Checked exceptions: Can throw checked exceptions from call().
Usage: Used when you need a result or want to handle exceptions in a thread.
//////////////////////////
Callable Interface
Introduced in Java 5 (in java.util.concurrent package) as part of the Concurrency API.
Addresses limitations of Runnable by:
Allowing a return value (via generics).
Allowing checked exceptions to be thrown.
Uses generics to define the return type of the result object.
Method signature:
V call() throws Exception;
*/

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) throws Exception {
    	/*
    	you are overriding the call() method of the Callable<String> interface
    	*/
        Callable<String> task = () -> {
            Thread.sleep(1000); // Simulate some work
            return "Callable result from thread: " + Thread.currentThread().getName();
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(task); // Returns a Future

        System.out.println("Result: " + future.get()); // Blocks until result is ready
        executor.shutdown();
    }
}