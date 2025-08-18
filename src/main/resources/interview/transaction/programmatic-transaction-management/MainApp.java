
/*
To use this XML configuration, you would load the Spring context in your main application:
*/
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ProductService productService = context.getBean("productService", ProductService.class);

        productService.createProductAndLog("Laptop");
    }
}
