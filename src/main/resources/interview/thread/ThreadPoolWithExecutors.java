
/*
ThreadPoolWithExecutors.java
/////////////////
You use Executors.newFixedThreadPool() or similar factory methods to create a thread pool.
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolWithExecutors {
    public static void main(String[] args) {
        // Create a thread pool with 3 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            /*
            https://medium.com/@AlexanderObregon/javas-executorservice-submit-method-explained-63562cc07edc
            The submit() method is one of the key functions of the ExecutorService interface. 
            It allows you to submit a task (either Runnable or Callable) for execution and 
            returns a Future object. The Future object represents the result of an asynchronous 
            computation, providing methods to check if the computation is complete, wait for 
            its completion, and retrieve the result once it's ready.
            */
            executor.submit(() -> {
                System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed on " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("All tasks finished");
    }
}
