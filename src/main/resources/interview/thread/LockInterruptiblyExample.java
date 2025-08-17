
/*
LockInterruptiblyExample.java
//////////////////
i dont understand why i ever will go with lock.lockInterruptibly() give me java 
examplle why i must use lock.lockInterruptibly() ??
/////////////////////
on the surface lockInterruptibly() looks redundant because lock() already blocks 
until success.
The only reason to use lockInterruptibly() is when you want your thread to be able 
to give up waiting for a lock if it’s been told to stop (interrupted). This is very 
useful in deadlock-prone code or when you have long-running tasks that should be 
cancelable.
*/

/*
✅ Fixed with lockInterruptibly()
Now both threads can be interrupted to break deadlocks:
*/
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSolved {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void task1() {
        try {
            lock1.lockInterruptibly(); // can be interrupted
            System.out.println("Task1 got lock1, waiting for lock2...");
            Thread.sleep(100);

            lock2.lockInterruptibly();
            try {
                System.out.println("Task1 got lock2");
            } finally {
                lock2.unlock();
            }

        } catch (InterruptedException e) {
            System.out.println("Task1 interrupted and exiting gracefully");
        } finally {
            if (lock1.isHeldByCurrentThread()) lock1.unlock();
        }
    }

    public void task2() {
    	/*
    	What happens here?
		If deadlock occurs, t2.interrupt() breaks T2 out of lockInterruptibly().
		T2 releases its lock → T1 can continue.
		Program recovers safely.
    	*/
        try {
            lock2.lockInterruptibly();
            System.out.println("Task2 got lock2, waiting for lock1...");
            Thread.sleep(100);

            lock1.lockInterruptibly();
            try {
                System.out.println("Task2 got lock1");
            } finally {
                lock1.unlock();
            }

        } catch (InterruptedException e) {
            System.out.println("Task2 interrupted and exiting gracefully");
        } finally {
            if (lock2.isHeldByCurrentThread()) lock2.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadlockSolved example = new DeadlockSolved();
        Thread t1 = new Thread(example::task1, "T1");
        Thread t2 = new Thread(example::task2, "T2");

        t1.start();
        t2.start();

        // Give them time to deadlock
        Thread.sleep(300);

        // Break the deadlock by interrupting one thread
        System.out.println("Main thread interrupts T2 to avoid deadlock!");
        t2.interrupt();
    }
}

// ////////////////////

/*
Example Without lockInterruptibly() (deadlock risk)
Two threads trying to acquire locks in different order:
////////////////////////
If t1 gets lock1 and t2 gets lock2, both will block forever.
Interrupting them won’t help, because lock() does not respond to interrupts.
*/
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void task1() {
        lock1.lock();
        try {
            System.out.println("Task1 got lock1, waiting for lock2...");
            Thread.sleep(100);
            lock2.lock(); // waits forever if Task2 holds lock2
            try {
                System.out.println("Task1 got lock2");
            } finally {
                lock2.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println("Task1 interrupted");
        } finally {
            lock1.unlock();
        }
    }

    public void task2() {
        lock2.lock();
        try {
            System.out.println("Task2 got lock2, waiting for lock1...");
            Thread.sleep(100);
            lock1.lock(); // waits forever if Task1 holds lock1
            try {
                System.out.println("Task2 got lock1");
            } finally {
                lock1.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println("Task2 interrupted");
        } finally {
            lock2.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadlockExample example = new DeadlockExample();
        Thread t1 = new Thread(example::task1, "T1");
        Thread t2 = new Thread(example::task2, "T2");

        t1.start();
        t2.start();

        // Both threads will deadlock here and never recover!
    }
}

