
/*
1>BlockingQueue
Automatically handles synchronization.
put() blocks when full.
take() blocks when empty.

2>Thread Pool
Using Executors.newFixedThreadPool(4) means we reuse threads efficiently.
Two threads act as producers, two as consumers.

3>Graceful Shutdown
We let producers finish their tasks.
Consumers here run indefinitely until the executor is forced to stop — in a real app, you’d send a poison pill or stop signal to exit cleanly.
*/

import java.util.concurrent.*;

public class ProducerConsumerWithExecutor {
    public static void main(String[] args) {
        // Shared queue with capacity limit
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Thread pool with 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit producers
        for (int i = 1; i <= 2; i++) {
            int producerId = i;
            executor.submit(() -> {
                try {
                    for (int j = 1; j <= 5; j++) {
                        int item = (producerId * 100) + j; // Unique item
                        queue.put(item); // blocks if full
                        System.out.println("Producer " + producerId +
                                " produced: " + item +
                                " (Queue size: " + queue.size() + ")");
                        Thread.sleep(500); // Simulate work
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Submit consumers
        for (int i = 1; i <= 2; i++) {
            int consumerId = i;
            executor.submit(() -> {
                try {
                    while (true) { // We'll exit later when interrupted
                        Integer item = queue.take(); // blocks if empty
                        System.out.println("Consumer " + consumerId +
                                " consumed: " + item +
                                " (Queue size: " + queue.size() + ")");
                        Thread.sleep(800); // Simulate processing
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Stop after a while
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // force stop
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Main thread exiting");
    }
}