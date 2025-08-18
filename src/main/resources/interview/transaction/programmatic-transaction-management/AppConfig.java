/*
To make this example work, you need to configure the TransactionTemplate 
and the PlatformTransactionManager. This is typically done in a Spring 
configuration class.
////////////////////
Why Use TransactionTemplate?
1>Granular Control: You have explicit control over where the transaction 
boundaries are, which is helpful for complex, non-declarative logic.
2>Simplicity: It abstracts away the boilerplate code for try...catch...
finally blocks needed to manage transactions manually. The TransactionTemplate 
ensures that the transaction is correctly committed or rolled back, even 
if an exception is thrown.
3>Hybrid Scenarios: It's a great option for services that primarily use 
declarative transactions but have a few specific methods that require a 
programmatic approach.
////////////////////////
You don't need AppConfig if you're using the MainApp with an XML configuration. 
You would use one or the other, not both.

The AppConfig class, with its @Configuration and @Bean annotations, is the 
Java-based approach to configuring a Spring application context. It serves 
the same purpose as the applicationContext.xml file
1>AppConfig (Java-based configuration): You would use this approach if you prefer 
to define your beans and their dependencies programmatically in Java. It is often 
considered more type-safe and easier to refactor than XML.
2>applicationContext.xml (XML-based configuration): This is the traditional way to 
configure a Spring application. The MainApp in the example loads this file to 
bootstrap the application and get the configured beans.
Both methods achieve the same result: they define the DataSource, TransactionManager, 
TransactionTemplate, and ProductService beans and wire them together. You choose the 
one that aligns with your project's chosen configuration style
*/
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        // Configure and return your DataSource here (e.g., using HikariCP, etc.)
        // This is a placeholder; you would use a real data source.
        return new org.springframework.jdbc.datasource.DriverManagerDataSource(
            "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", ""
        );
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}