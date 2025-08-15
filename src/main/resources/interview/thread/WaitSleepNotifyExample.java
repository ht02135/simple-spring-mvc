
/*
WaitSleepNotifyExample.java
wait(): As the name suggests, it is a non-static method that causes the current 
thread to wait and go to sleep until some other threads call the notify () or 
notifyAll() method for the object’s monitor (lock). It simply releases the lock 
and is mostly used for inter-thread communication. It is defined in the object 
class, and should only be called from a synchronized context. 
//////////////////////
you want a Java example showing how wait(), sleep(), notify(), and notifyAll() work together.
Here’s a clear, minimal example that shows the differences, the required synchronization rules, and how they coordinate threads.
////////////////////////
wait():
Must be called from inside a synchronized block/method.
Releases the lock and suspends the thread until notify() or notifyAll() is called.

notify():
Wakes one waiting thread (chosen arbitrarily).

notifyAll():
Wakes all waiting threads (they compete for the lock).

sleep():
Pauses the thread for a given time without releasing the lock.
////////////////////////
1>Where did the lock come from?
consume() is declared synchronized.
That means when a thread enters this method, it automatically acquires the monitor
lock of the object instance (this), in this case, the SharedResource object.

2>What happens at wait()?
wait() is also called on this (implicitly — because there’s no qualifier like 
someObject.wait()).

This means:
The thread must currently hold this’s monitor lock.
Calling wait() makes the thread:
Release the this monitor lock immediately.
Go into the waiting state, linked to that monitor.
Stay there until it’s awakened by notify()/notifyAll() (or interrupted).

3>Why release the lock?
Without releasing the lock, no other thread could enter produce() to change available 
and call notify().
Releasing it allows other threads to enter synchronized sections on the same object.

4>After being notified:
The thread doesn’t instantly resume — it first has to reacquire the same monitor 
lock (this).
Only then does it continue execution after the wait() line.
*/

class SharedResource {
    private boolean available = false;

    public synchronized void produce() {
        System.out.println(Thread.currentThread().getName() + " producing...");
        available = true;
        // Wake up ONE waiting thread
        notify(); 
        // Or use notifyAll() if multiple threads should be awakened
    }

    public synchronized void consume() {
        while (!available) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting...");
                /*
                the lock being released is the intrinsic monitor lock (sometimes called 
                the "object lock") of the object you called wait() on.
                */
                wait(); // releases lock and waits
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " consuming...");
        available = false;
    }
}

public class WaitNotifyExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Consumer thread
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                resource.consume();
                try {
                	//hung: this comment misleading. what lock? we are not in synchronized block
                    Thread.sleep(500); // doesn't release lock 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer");

        // Producer thread
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000); // simulating work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                resource.produce();
            }
        }, "Producer");

        consumer.start();
        producer.start();
    }
}