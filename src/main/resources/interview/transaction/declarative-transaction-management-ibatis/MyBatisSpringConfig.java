/*
5. Spring Configuration for iBATIS (Java Config Version)
////////////////////////
Perfect! Let’s convert your Hibernate + Spring transactional example 
into an iBATIS (MyBatis) + Spring setup. We’ll keep everything else 
the same: UserService, CourseService, InvoiceService, controllers, 
transactions, and so on.

We’ll focus on:
Using iBATIS/ MyBatis mappers instead of Hibernate DAOs
Wiring Spring transaction management with @Transactional
Controllers stay the same (UserController, CourseController)
////////////////////////
///Key Points for Transactions
@Transactional works the same way as Hibernate, because Spring uses 
DataSourceTransactionManager.
UserService.invoice() and InvoiceService.createPdf() share the same 
physical DB transaction by default (Propagation REQUIRED).
Isolation level can be set if needed, e.g.:
@Transactional(propagation = Propagation.REQUIRED, 
isolation = Isolation.READ_COMMITTED)
////////////////////////////
In the iBATIS/MyBatis setup, the mapper interfaces (like UserMapper, 
CourseMapper, InvoiceMapper) are injected directly into the services 
(UserService, CourseService, InvoiceService) via Spring.
In Java config we used @MapperScan("x.y.mapper") to automatically 
create Spring beans for all mappers. That’s why you don’t see 
explicit bean declarations for them.
*/
package x.y.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "x.y")
@MapperScan("x.y.mapper")
public class MyBatisSpringConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/testdb?useSSL=false");
        ds.setUsername("root");
        ds.setPassword("password");
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        return sessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}