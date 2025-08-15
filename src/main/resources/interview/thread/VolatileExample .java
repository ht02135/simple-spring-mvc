
/*
VolatileExample .java
//////////////////
In Java, volatile makes a variable's value visible to all threads immediately after 
a write, but it does not make compound operations (like count++) atomic.
That means:
It’s thread-safe for visibility (ensures no stale values).
It’s not thread-safe for atomicity (you still need synchronization or AtomicXXX 
for increments, etc.).
////////////////////
How it works
1>Without volatile, the worker thread might keep reading a cached copy of running 
and never see false set by the main thread.
2>With volatile, the update to running in the main thread is flushed to main memory, 
and the worker reads the fresh value.
///////////////////
Yes — the previous example is thread-safe for the purpose it serves, and you do not 
need additional code to make running thread-safe in that case.

The only requirement here is that when the main thread sets running = false, the 
worker thread should see that change immediately.
volatile guarantees visibility — any write to running by one thread is immediately 
visible to all others.
We’re not doing compound operations (running++, running = !running, etc.) that 
require atomicity — just a simple read in the loop and a single write in the main thread.

*/

public class VolatileExample {
    private volatile boolean running = true; // visible to all threads

    public void startTask() {
        Thread worker = new Thread(() -> {
            System.out.println("Worker started...");
            while (running) {
                // do some work
            }
            System.out.println("Worker stopped.");
        });

        worker.start();

        try {
            Thread.sleep(2000); // let the worker run for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main thread stopping worker...");
        running = false; // immediately visible to worker thread
    }

    public static void main(String[] args) {
        new VolatileExample().startTask();
    }
}