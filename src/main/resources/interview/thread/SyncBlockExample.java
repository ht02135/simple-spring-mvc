
/*
SyncBlockExample.java
/////////////////////
Synchronized Method: In this method, the thread acquires a lock on the object when 
they enter the synchronized method and releases the lock either normally or by 
throwing an exception when they leave the method.  No other thread can use the 
whole method unless and until the current thread finishes its execution and 
release the lock. It can be used when one wants to lock on the entire functionality 
of a particular method. 

Synchronized Block: In this method, the thread acquires a lock on the object 
between parentheses after the synchronized keyword, and releases the lock when 
they leave the block. No other thread can acquire a lock on the locked object 
unless and until the synchronized block exists. It can be used when one wants 
to keep other parts of the programs accessible to other threads.
 
Synchronized blocks should be preferred more as it boosts the performance of a 
particular program. It only locks a certain part of the program (critical section) 
rather than the entire method and therefore leads to less contention
*/
class CounterBlock {
    private int count = 0;

    public void increment() {
        /*
        synchronized Block
		Here, you synchronize only a specific section of code, reducing contention 
		and improving performance.
    	*/
        // Only this block is synchronized
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        synchronized (this) {
            return count;
        }
    }
}

public class SyncBlockExample {
    public static void main(String[] args) throws InterruptedException {
        CounterBlock counter = new CounterBlock();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final Count (Block): " + counter.getCount());
    }
}

// ////////////////////////

class Counter {
    private int count = 0;

    /*
    synchronized Method
	When you mark a whole method as synchronized, the entire method body is locked 
	on the object’s intrinsic lock (for instance methods) or the class lock (for static methods).
    */
    // Whole method is synchronized
    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

public class SyncMethodExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final Count (Method): " + counter.getCount());
    }
}
