
/*
ThreadJoinExample.java
///////////////////////
What Thread.join() Does
join() is a method on Thread that causes the calling thread to wait until the 
target thread finishes execution.
////////////////////////
For example:

If main thread calls workerThread.join(), the main thread will stop and wait 
until workerThread is done running.

*/

public class ThreadJoinExample {
    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            System.out.println("Worker thread starting...");
            try {
                Thread.sleep(2000); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker thread finished!");
        });

        System.out.println("Starting worker thread...");
        worker.start();

        try {
            System.out.println("Main thread waiting for worker to finish...");
            worker.join(); // Wait for worker to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread resumes after worker is done.");
    }
}