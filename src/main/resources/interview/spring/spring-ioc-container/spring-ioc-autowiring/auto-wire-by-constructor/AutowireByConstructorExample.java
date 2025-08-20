/*
auto-wire-by-constructor
AutowireByConstructorExample.java
applicationContext.xml
*/
public class ServiceD {
    public void doWork() {
        System.out.println("ServiceD is working...");
    }
}

public class ClientD {
    private ServiceD service;

    public ClientD(ServiceD service) {  // constructor injection
        this.service = service;
    }

    public void execute() {
        service.doWork();
    }
}