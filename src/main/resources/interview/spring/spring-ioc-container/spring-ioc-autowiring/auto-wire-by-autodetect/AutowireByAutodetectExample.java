/*
auto-wire-by-autodetect
AutowireByAutodetectExample.java
applicationContext.xml
*/
public class ServiceE {
    public void doWork() {
        System.out.println("ServiceE is working...");
    }
}

public class ClientE {
    private ServiceE service;

    // constructor present → Spring will try this first
    public ClientE(ServiceE service) {
        this.service = service;
    }

    // fallback setter (used if constructor not suitable)
    public void setService(ServiceE service) {
        this.service = service;
    }

    public void execute() {
        service.doWork();
    }
}