
/*
LockInterfaceExample.java
/////////////////
Advantages of using Lock interface over Synchronization block: 
Methods of Lock interface i.e., Lock() and Unlock() can be called in different 
methods. It is the main advantage of a lock interface over a synchronized block 
because the synchronized block is fully contained in a single method.  
Lock interface is more flexible and makes sure that the longest waiting thread 
gets a fair chance for execution, unlike the synchronization block.
////////////////////
In Java, both synchronized blocks/methods and the Lock interface 
(from java.util.concurrent.locks) are used for thread-safety, but Lock is 
more flexible and powerful.
/////////////////////
1. lock.lock()
Behavior:
Acquires the lock, blocking indefinitely until it’s available.
The thread cannot be interrupted while waiting.
Use case:
When you’re sure the lock will eventually be available and interruption isn’t important.

lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
/////////////////////////
2. lock.tryLock()
Behavior:
Tries to acquire the lock without waiting.
Immediately returns:
true → lock acquired.
false → lock not available.
Use case:
When you don’t want to block forever (e.g., in non-blocking algorithms, or when you want 
to do something else if the lock is busy).

if (lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
} else {
    System.out.println("Couldn't get the lock, doing something else...");
}
////////////////////////
3. lock.lockInterruptibly()
Behavior:
Attempts to acquire the lock, but can be interrupted while waiting.
If interrupted, it throws InterruptedException and stops waiting.
Use case:
When you want to avoid deadlock scenarios or support cancellation while waiting

try {
    lock.lockInterruptibly();  // waits but responds to interrupts
    try {
        // critical section
    } finally {
        lock.unlock();
    }
} catch (InterruptedException e) {
    System.out.println("Thread was interrupted while waiting for the lock");
}
*/
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResourceLock {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    /*
    More powerful:
	Can use tryLock() to avoid blocking.
	Can use lockInterruptibly() to respond to interrupts.
	Can implement fair locks with new ReentrantLock(true).
	❌ Slightly more complex, must release lock manually (risk of deadlock if you forget).
    */
    public void increment() {
        lock.lock();   // acquire lock
        try {
            count++;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        } finally {
            lock.unlock(); // always release in finally!
        }
    }
}

public class LockExample {
    public static void main(String[] args) {
        SharedResourceLock resource = new SharedResourceLock();

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                resource.increment();
            }
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");

        t1.start();
        t2.start();
    }
}

// //////////////////
/*
an example in Java that demonstrates:
tryLock()
lockInterruptibly()
ReentrantLock(true) (fair lock)
//////////////////
ReentrantLock(true) → ensures fairness (longest waiting thread gets the lock first).
tryLock(timeout, unit) → waits up to the given time, returns false if it can’t acquire.
lockInterruptibly() → allows the thread to be interrupted while waiting for the lock, unlike lock().
👉 This example will show different behaviors:
T1 acquires normally.
T2 may fail if it can’t get the lock within 500ms.
T3 will be interrupted before it gets the lock.
*/
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class LockExample {
    private final ReentrantLock lock = new ReentrantLock(true); // fair lock
    private int count = 0;

    // Regular increment using lock()
    public void increment() {
        lock.lock();
        try {
            count++;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        } finally {
            lock.unlock();
        }
    }

    // Try to increment using tryLock with timeout
    public void tryIncrement() {
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) { // waits at most 500ms
                try {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " tryIncrement count: " + count);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could NOT acquire lock (tryLock)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted during tryLock");
        }
    }

    // Increment using lockInterruptibly
    public void interruptibleIncrement() {
        try {
            lock.lockInterruptibly(); // can respond to interruption
            try {
                count++;
                System.out.println(Thread.currentThread().getName() + " interruptibleIncrement count: " + count);
                Thread.sleep(200); // simulate work
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted while waiting for lock");
        }
    }

    public static void main(String[] args) {
        LockExample example = new LockExample();

        // Thread using normal lock()
        Thread t1 = new Thread(example::increment, "T1");

        // Thread using tryLock()
        Thread t2 = new Thread(example::tryIncrement, "T2");

        // Thread using lockInterruptibly()
        Thread t3 = new Thread(example::interruptibleIncrement, "T3");

        t1.start();
        t2.start();
        t3.start();

        // Interrupt t3 after 100ms (to demonstrate lockInterruptibly)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.interrupt();
    }
}

// //////////////////

class SharedResource {
    private int count = 0;

    /*
    ✅ Simpler, automatic lock/unlock.
    ❌ Can’t try acquiring lock, can’t interrupt waiting threads, no fairness policy.
    */
    public void increment() {
        synchronized (this) {  // lock on 'this' object
            count++;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        }
    }
}

public class SyncBlockExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                resource.increment();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
