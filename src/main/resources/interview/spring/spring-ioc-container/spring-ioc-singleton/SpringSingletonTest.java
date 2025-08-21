import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringSingletonTest {
    public static void main(String[] args) {
        ApplicationContext context = 
            new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringSingleton s1 = (SpringSingleton) context.getBean("springSingleton");
        SpringSingleton s2 = (SpringSingleton) context.getBean("springSingleton");

        System.out.println(s1 == s2); // true
        s1.doWork();
    }
}
