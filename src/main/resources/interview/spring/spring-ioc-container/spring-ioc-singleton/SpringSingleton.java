/*
2. Spring Singleton Bean (Managed by Container)
*/
public class SpringSingleton {

    public SpringSingleton() {
        System.out.println("SpringSingleton bean created by Spring container");
    }

    public void doWork() {
        System.out.println("Doing work in SpringSingleton");
    }
}
