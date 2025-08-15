
/*
VolatileCounterExample.java
////////////////
Not Thread-Safe Example (with compound operation)
You want to keep volatile int counter but still make it thread-safe.

That’s possible — but you’ll have to add synchronization around the 
compound operation so it becomes atomic.
volatile will give you visibility, 
and synchronized will give you atomicity.
*/
public class VolatileCounterSafe {
    private volatile int counter = 0; // volatile for visibility
    private volatile boolean running = true;

    public void startTask() {
        Thread worker1 = new Thread(() -> {
            while (running) {
                increment(); // synchronized to ensure atomicity
            }
        });

        Thread worker2 = new Thread(() -> {
            while (running) {
                increment();
            }
        });

        worker1.start();
        worker2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        running = false;

        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final counter value: " + counter);
    }

    // synchronized method to make counter++ atomic
    private synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) {
        new VolatileCounterSafe().startTask();
    }
}