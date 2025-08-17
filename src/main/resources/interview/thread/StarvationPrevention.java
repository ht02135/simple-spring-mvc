
/*
StarvationPrevention.java

*/
import java.util.concurrent.locks.ReentrantLock;

public class StarvationPrevention {
	/*
	How to Prevent Thread Starvation
	Use Fair Locks (ReentrantLock(true))
	Java’s ReentrantLock has a fairness policy.
	With new ReentrantLock(true), the longest-waiting thread gets the lock first (FIFO).
	//////////////////////////
	Avoid Priority Abuse
	Don’t set extreme thread priorities (MIN_PRIORITY vs MAX_PRIORITY) unless absolutely necessary.
	Use Higher-Level Concurrency APIs
	Instead of manually handling locks, use thread pools (ExecutorService), semaphores, 
	or concurrent collections, which are designed to minimize starvation.
	*/
    private static final ReentrantLock lock = new ReentrantLock(true); // fair lock

    public static void main(String[] args) {
        Runnable task = () -> {
            while (true) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " got the lock");
                    try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                } finally {
                    lock.unlock();
                }
            }
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
        new Thread(task, "Thread-3").start();
    }
}

// /////////////////////

import java.util.concurrent.locks.ReentrantLock;

public class StarvationExample {
    private static final ReentrantLock lock = new ReentrantLock();

    /*
    The high-priority thread almost always acquires the lock first.
	The low-priority thread is starved and may rarely (or never) get CPU time.
    */
    public static void main(String[] args) {
        // High priority thread (keeps hogging the lock)
        Thread highPriority = new Thread(() -> {
            while (true) {
                if (lock.tryLock()) {
                    try {
                        System.out.println("High priority thread working...");
                        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
        highPriority.setPriority(Thread.MAX_PRIORITY);

        // Low priority thread (starved because high-priority keeps grabbing the lock)
        Thread lowPriority = new Thread(() -> {
            while (true) {
                if (lock.tryLock()) {
                    try {
                        System.out.println("Low priority thread finally working...");
                        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        highPriority.start();
        lowPriority.start();
    }
}

