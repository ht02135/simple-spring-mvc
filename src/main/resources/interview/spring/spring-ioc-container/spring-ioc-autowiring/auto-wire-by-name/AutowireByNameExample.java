/*
auto-wire-by-name
AutowireByNameExample.java
applicationContext.xml
*/
public class ServiceB {
    public void doWork() {
        System.out.println("ServiceB is working...");
    }
}

public class ClientB {
    private ServiceB serviceB; // property name must match bean id "serviceB"

    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

    public void execute() {
        serviceB.doWork();
    }
}