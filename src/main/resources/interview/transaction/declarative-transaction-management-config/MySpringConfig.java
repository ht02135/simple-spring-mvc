
/*
You want to see the same example (UserService + CourseService), but now using 
annotation-driven transaction management with @Transactional instead of XML 
<tx:advice> + <aop:advisor>.
////////////////////////
///Key differences from XML approach:
No <tx:advice> / <aop:advisor> in XML.
Use @EnableTransactionManagement in @Configuration.
Put @Transactional annotations directly on service methods (or at class level).
@Service and @Repository make beans autodetectable.

MySpringConfig.java (Java Config)
*/
package x.y.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement   // enables @Transactional support
public class MySpringConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/testdb");
        ds.setUsername("root");
        ds.setPassword("password");
        return ds;
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}

