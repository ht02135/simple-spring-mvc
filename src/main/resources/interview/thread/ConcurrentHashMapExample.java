
/*
ConcurrentHashMapExample.java
///////////////////
1. Synchronization
Hashtable:
Entire map is synchronized.
Every method (get, put, etc.) is synchronized on the whole object.
This leads to a lot of contention in multi-threaded applications.

ConcurrentHashMap:
Uses fine-grained locking (in Java 7, segmented locks; in Java 8+, bucket-level 
CAS + synchronized blocks).
Multiple threads can read/write different parts of the map at the same time.
Much faster under concurrency.
///////////////////
3. Iterators
Hashtable: Iterator is fail-fast (will throw ConcurrentModificationException 
if the map is modified while iterating).
ConcurrentHashMap: Iterator is weakly consistent (doesn’t throw exceptions, 
reflects some but not all modifications).
///////////////////
4. Performance
Hashtable: Slower under high concurrency because of full synchronization.
ConcurrentHashMap: Much faster because of fine-grained locking.
/////////////////
When to Use
Hashtable: Almost never in modern applications (kept for backward compatibility).
ConcurrentHashMap: Use when you need a thread-safe, high-performance map.
*/
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class MapExample {
    public static void main(String[] args) {
        
        // Hashtable Example (Legacy)
        Hashtable<Integer, String> hashtable = new Hashtable<>();
        hashtable.put(1, "Apple");
        hashtable.put(2, "Banana");
        hashtable.put(3, "Cherry");

        System.out.println("Hashtable: " + hashtable);

        // ConcurrentHashMap Example (Preferred in multithreading)
        ConcurrentHashMap<Integer, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put(1, "Dog");
        concurrentMap.put(2, "Elephant");
        concurrentMap.put(3, "Fox");

        System.out.println("ConcurrentHashMap: " + concurrentMap);

        // Multi-threaded Example
        Runnable task = () -> {
            for (int i = 4; i <= 6; i++) {
                concurrentMap.put(i, "Value" + i);
                System.out.println(Thread.currentThread().getName() + " added: " + i);
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}