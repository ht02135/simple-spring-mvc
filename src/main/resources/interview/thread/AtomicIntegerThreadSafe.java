
/*
AtomicIntegerThreadSafe.java
////////////////////
Thread-Safe Version (using AtomicInteger)
Why This Is Now Thread-Safe
AtomicInteger guarantees atomicity for increments (incrementAndGet()).
The volatile boolean running flag still ensures visibility between threads.
No lost updates occur, so the final counter is as expected.
//////////////////////
Yes — AtomicInteger is both volatile-based and atomic.
/////////////////////////
1. Atomic Primitives
These wrap primitive types but make operations atomic and visible (via volatile internally):
AtomicInteger – atomic int
AtomicLong – atomic long
AtomicBoolean – atomic boolean
/////////////////////////////
2. Atomic Arrays
Atomic versions of arrays for safe concurrent element updates:
AtomicIntegerArray
AtomicLongArray
AtomicReferenceArray<E>
/////////////////////////////
3. Atomic References
Work with objects instead of primitives:
AtomicReference<V> – holds an object reference atomically.
AtomicStampedReference<V> – atomic reference plus a stamp (often used to avoid the ABA 
problem in lock-free algorithms).
AtomicMarkableReference<V> – atomic reference plus a boolean mark.
////////////////////////////
Rule of thumb:
For a simple counter with low contention → AtomicInteger / AtomicLong.
For a high-traffic counter → LongAdder.
For object references → AtomicReference (or its stamped/markable versions).
For arrays → use atomic array variants.
For updating object fields without replacing the object → use field updaters.
*/
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileCounterThreadSafe {
    private AtomicInteger counter = new AtomicInteger(0); // atomic & thread-safe
    private volatile boolean running = true; // still fine with volatile for flag

    public void startTask() {
        Thread worker1 = new Thread(() -> {
            while (running) {
            	//hung : i bet incrementAndGet is guarded with synchromized
            	//atomic is overrated, just extract operation to a synchromized method
                counter.incrementAndGet(); // atomic increment
            }
        });

        Thread worker2 = new Thread(() -> {
            while (running) {
            	//hung : i bet incrementAndGet is guarded with synchromized
            	//atomic is overrated, just extract operation to a synchromized method
                counter.incrementAndGet();
            }
        });

        worker1.start();
        worker2.start();

        try {
            Thread.sleep(2000); // let them increment for 2 seconds
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

        System.out.println("Final counter value: " + counter.get());
    }

    public static void main(String[] args) {
        new VolatileCounterThreadSafe().startTask();
    }
}