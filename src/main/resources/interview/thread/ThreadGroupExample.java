
/*
ThreadGroupExample.java
////////////
What is a ThreadGroup?
A ThreadGroup is a mechanism in Java to group multiple threads into a single unit for 
easier management.
Introduced in Java 1.0, before higher-level concurrency utilities existed.
With a ThreadGroup, you can:
List all threads in a group
Set maximum priority for all threads
Interrupt all threads in the group at once
Manage thread hierarchies (a group can have subgroups)
//////////////
Why NOT to use ThreadGroup?
It sounds useful, but in practice, it’s considered deprecated in spirit 
(not officially deprecated yet, but discouraged).
Problems with ThreadGroup:
Limited Functionality
You can’t safely enumerate threads (may miss some if they die in the middle).
Group-wide exception handling isn’t reliable.
Doesn’t fit with modern Java concurrency
The java.util.concurrent package (ExecutorService, ThreadPoolExecutor, ForkJoinPool) 
provides better control, monitoring, and task management.
Security concerns
Some APIs (like stopping or suspending all threads in a group) are unsafe and removed 
in newer Java versions.
Poor Design
Mixing thread hierarchies with management responsibilities leads to confusion.
No real isolation: a thread in a group can still affect others.
*/

public class ThreadGroupExample {
    public static void main(String[] args) {
        // Create a ThreadGroup
        ThreadGroup group = new ThreadGroup("MyGroup");

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
            }
        };

        // Create threads in the group
        Thread t1 = new Thread(group, task, "Thread-1");
        Thread t2 = new Thread(group, task, "Thread-2");

        t1.start();
        t2.start();

        System.out.println("Active threads in group: " + group.activeCount());

        // Interrupt all threads in the group
        group.interrupt();
    }
}

// ////////////////

/*
Modern applications should use Executors or ForkJoinPool for thread management.
*/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " is running");
        };

        executor.submit(task);
        executor.submit(task);

        executor.shutdown(); // Gracefully shut down
    }
}
