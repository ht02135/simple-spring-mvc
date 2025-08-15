
/*
ClassLockExample.java
/////////////////////
ReentrantLock ensures only one thread at a time can execute the code 
between lock.lock() and lock.unlock().
You must always release the lock in a finally block to avoid deadlocks.
Compared to synchronized, Lock lets you:
Try to acquire the lock without waiting (tryLock()).
Interrupt waiting threads.
Create fair locks (first-come-first-served).
*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private int counter = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // Acquire the lock
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " incremented counter to " + counter);
        } finally {
            lock.unlock(); // Always release in finally
        }
    }
}

public class ClassLockExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                resource.increment();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}