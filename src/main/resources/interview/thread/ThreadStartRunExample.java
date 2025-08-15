
/*
ThreadStartRunExample.java
///////////////////
1. run() method
Purpose: Contains the code that will execute in the new thread.
Nature: Just a normal method—if you call it directly, it will run in the current thread, not a new one.
Defined in: java.lang.Runnable interface (which Thread implements).
Override: You usually override run() to define the task for the thread.
/////////////////////
start() method
Purpose: Creates a new OS-level thread and then calls the run() method in that new thread.
Nature: Native + JVM-managed method; you shouldn’t override it.
Behavior: Can be called only once per Thread object—calling it twice causes IllegalThreadStateException.
*/

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running in thread: " + Thread.currentThread().getName());
    }
}

public class ThreadStartRunExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();

        // This will start a new thread
        t1.start();  
        
        // This will NOT start a new thread — it just calls run() like a normal method
        t1.run();  

        System.out.println("Main thread: " + Thread.currentThread().getName());
    }
}