
/*
Changes from previous
Separate Producer and Consumer classes → More reusable and testable.
BlockingQueue still ensures thread safety.
Still uses Executors.newFixedThreadPool() for thread management.
*/
import java.util.concurrent.*;

// Producer Class
class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int producerId;

    public Producer(BlockingQueue<Integer> queue, int producerId) {
        this.queue = queue;
        this.producerId = producerId;
    }

    @Override
    public void run() {
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
    }
}

// Consumer Class
class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int consumerId;

    public Consumer(BlockingQueue<Integer> queue, int consumerId) {
        this.queue = queue;
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        try {
            while (true) { // Will stop when interrupted
                Integer item = queue.take(); // blocks if empty
                System.out.println("Consumer " + consumerId +
                        " consumed: " + item +
                        " (Queue size: " + queue.size() + ")");
                Thread.sleep(800); // Simulate processing
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerWithExecutor {
    public static void main(String[] args) {
    	/*
    	Yes — in the previous example, the BlockingQueue<Integer> queue is the shared 
    	synchronized resource between producers and consumers.
		Here’s why:
		Shared →
		All Producer and Consumer instances receive the same queue reference in their 
		constructor. They all operate on this same object in memory.

		Synchronized (Thread-safe) →
		ArrayBlockingQueue (and other BlockingQueue implementations) internally use 
		locks to make put() and take() thread-safe.

		When a producer calls put(), it will block if the queue is full until a consumer 
		removes an item.

		When a consumer calls take(), it will block if the queue is empty until a producer 
		adds an item.

		No manual synchronized keyword needed →
		Because BlockingQueue already manages synchronization and waiting internally, we 
		don’t have to manually implement wait()/notify() or locks.
    	*/
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Start producers
        executor.submit(new Producer(queue, 1));
        executor.submit(new Producer(queue, 2));

        // Start consumers
        executor.submit(new Consumer(queue, 1));
        executor.submit(new Consumer(queue, 2));

        // Allow time for work, then shutdown
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Main thread exiting");
    }
}