
/*
DaemonVsUserThread.java
User Thread (Non-Daemon Thread): In Java, user threads have a specific 
life cycle and its life is independent of any other thread. JVM (Java 
Virtual Machine) waits for any of the user threads to complete its tasks 
before terminating it. When user threads are finished, JVM terminates the 
whole program along with associated daemon threads. 

Daemon Thread: In Java, daemon threads are basically referred to as a 
service provider that provides services and support to user threads. 
There are basically two methods available in thread class for daemon 
thread: setDaemon() and isDaemon(). 
/////////////////////////////
Daemon Thread vs user thread java example
User threads are regular threads — the JVM will keep running until all user threads finish.
Daemon threads are background service threads — the JVM will exit as soon as only daemon threads remain
//////////////////////////////
The daemon thread keeps running in the background, but
Once the user thread finishes, JVM stops, even if daemon thread is still looping.
If you remove daemonThread.setDaemon(true);, both threads become user threads, and JVM will wait for both to finish.
*/

public class DaemonVsUserThread {
    public static void main(String[] args) {
        // User thread
        Thread userThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("User thread running... " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("User thread finished.");
        });

        // Daemon thread
        Thread daemonThread = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread running in background...");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Mark as daemon
        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();

        System.out.println("Main method ends.");
    }
}