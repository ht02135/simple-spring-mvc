
/*
ClassLevelLockExample .java
public class ClassLevelLockExample  
{    
  public void classLevelLockMethod()  
 {       
     synchronized (ClassLevelLockExample.class)  
       {         
            //DO your stuff here       
       }    
 } 
} 
/////////////////////////////////
monitor lock of the Class object is used instead of an instance lock.
synchronized (ClassName.class) locks on the class object, so:
All instances of that class share the same lock.
This is different from synchronized (this), which locks only the current instance.
Both static and instance methods can use synchronized (ClassName.class) to coordinate access.
This is useful when you want to protect class-wide shared resources rather than instance-specific ones.
*/

class ClassLevelLockExample {

    public void instanceMethod() {
        synchronized (ClassLevelLockExample.class) {
            System.out.println(Thread.currentThread().getName() + " acquired class-level lock in instance method");
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void staticMethod() {
        synchronized (ClassLevelLockExample.class) {
            System.out.println(Thread.currentThread().getName() + " acquired class-level lock in static method");
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TestClassLevelLock {
    public static void main(String[] args) {
        ClassLevelLockExample obj1 = new ClassLevelLockExample();
        ClassLevelLockExample obj2 = new ClassLevelLockExample();

        // Runnable calling instance method
        Runnable task1 = () -> obj1.instanceMethod();

        // Runnable calling static method
        Runnable task2 = () -> ClassLevelLockExample.staticMethod();

        Thread t1 = new Thread(task1, "Thread-1");
        Thread t2 = new Thread(task2, "Thread-2");

        t1.start();
        t2.start();
    }
}