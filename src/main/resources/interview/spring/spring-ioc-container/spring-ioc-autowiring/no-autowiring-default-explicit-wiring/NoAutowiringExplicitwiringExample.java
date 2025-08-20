/*
(1) No Autowiring (default) Explicit wiring:
no-autowiring-default-explicit-wiring
NoAutowiringExplicitwiringExample.java
applicationContext.xml
*/
public class ServiceA {
    public void doWork() {
        System.out.println("ServiceA is working...");
    }
}

public class ClientA {
    private ServiceA serviceA;

    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public void execute() {
        serviceA.doWork();
    }
}