
/*
ThreadPoolWithOutExecutors.java
//////////////////////////////
Here we implement our own fixed-size thread pool using a blocking queue and worker threads.
We manually create worker threads.
A Queue stores pending tasks.
wait() and notify() coordinate worker threads and task submission.
No built-in task prioritization, rejection handling, or scaling — unlike Executors.
*/
import java.util.LinkedList;
import java.util.Queue;

class SimpleThreadPool {
    private final int poolSize;
    private final Worker[] workers;
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private boolean isStopped = false;

    public SimpleThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.workers = new Worker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Worker("Worker-" + i);
            workers[i].start();
        }
    }

    public synchronized void submit(Runnable task) {
        if (isStopped) throw new IllegalStateException("ThreadPool is stopped");
        taskQueue.add(task);
        notify(); // Wake up a waiting worker
    }

    public synchronized Runnable fetchTask() throws InterruptedException {
        while (taskQueue.isEmpty() && !isStopped) {
            wait();
        }
        return taskQueue.poll();
    }

    public synchronized void shutdown() {
        isStopped = true;
        notifyAll();
    }

    private class Worker extends Thread {
        public Worker(String name) { super(name); }

        public void run() {
            try {
                while (true) {
                    Runnable task = fetchTask();
                    if (task == null && isStopped) break;
                    if (task != null) task.run();
                }
            } catch (InterruptedException ignored) { }
        }
    }
}

public class ThreadPoolWithoutExecutors {
    public static void main(String[] args) {
        SimpleThreadPool pool = new SimpleThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println("Task " + taskId + " running on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed on " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();
    }
}