/*
auto-wire-by-type
AutowireByTypeExample.java
applicationContext.xml
*/
public class ServiceC {
    public void doWork() {
        System.out.println("ServiceC is working...");
    }
}

public class ClientC {
    private ServiceC service;  // name doesn't matter, only type does

    public void setService(ServiceC service) {
        this.service = service;
    }

    public void execute() {
        service.doWork();
    }
}