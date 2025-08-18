/*
convert applicationContext.xml and MainApp.java into AppConfig.java 
////////////////////////
To convert the XML configuration (applicationContext.xml) and the main 
application class (MainApp.java) into a single Java-based configuration 
file (AppConfig.java), you will use Spring's Java-based configuration. 
This approach consolidates the bean definitions and the main method 
into one coherent file.
/////////////////////////
AppConfig.java

This single file will define all the beans that were in applicationContext.xml 
and include the main method to bootstrap the Spring context and run the application.
///////////////////////////
Key Differences
1>Annotations: The @Configuration annotation marks the class as a source of 
bean definitions. @Bean is used on methods to declare that they return a Spring bean.
2>Method-based Injection: Instead of using <constructor-arg> in XML, the JdbcTemplate, 
TransactionTemplate, and ProductService beans are created by methods that take 
their dependencies as method parameters. Spring's dependency injection container 
automatically resolves and provides these dependencies.
3>AnnotationConfigApplicationContext: The main method uses AnnotationConfigApplicationContext 
to load the configuration from the AppConfig class, replacing the ClassPathXmlApplicationContext 
used for XML. This is the entry point for running a Spring application with Java-based 
configuration.
*/
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class AppConfig {

    // 1. Define the DataSource bean
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    // 2. Define the PlatformTransactionManager bean
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 3. Define the TransactionTemplate bean
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    // 4. Define the JdbcTemplate bean
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // 5. Define the ProductService bean and inject dependencies
    @Bean
    public ProductService productService(TransactionTemplate transactionTemplate, JdbcTemplate jdbcTemplate) {
        return new ProductService(transactionTemplate, jdbcTemplate);
    }

    public static void main(String[] args) {
        // Bootstrap the application using the Java-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // Retrieve the ProductService bean
        ProductService productService = context.getBean(ProductService.class);
        
        // Execute the transactional method
        productService.createProductAndLog("Laptop");
    }
}