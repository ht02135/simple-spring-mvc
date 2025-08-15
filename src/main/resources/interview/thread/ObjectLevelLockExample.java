
/*
ObjectLevelLockExample
//////////////////////
synchronized(this) means the lock is on the current object (obj in this case).
Since both t1 and t2 share the same instance (obj), only one can run the printNumbers() method at a time.
If each thread had its own object, there would be no mutual exclusion.
////////////////////////
object level lock is only for instance method (non-static) and not class method (static)
*/

class ObjectLevelLockExample implements Runnable {
    
	//object level lock is only for instance method (non-static) and not class method (static)
    public void printNumbers() {
        synchronized (this) { // Object-level lock
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                try {
                    Thread.sleep(500); // simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Object-level lock: synchronized non-static method
    //object level lock is only for instance method (non-static) and not class method (static)
    public synchronized void display() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            try {
                Thread.sleep(500); // simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void run() {
        printNumbers();
    }

    public static void main(String[] args) {
        ObjectLevelLockExample obj = new ObjectLevelLockExample();
        
        Thread t1 = new Thread(obj, "Thread-1");
        Thread t2 = new Thread(obj, "Thread-2");
        
        t1.start();
        t2.start();
    }
}