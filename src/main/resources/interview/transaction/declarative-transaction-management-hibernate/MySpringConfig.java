/*
Spring Configuration with Hibernate
///////////////////////
///We’ll build a complete example that uses:
Spring annotation-driven transaction management (@EnableTransactionManagement, @Transactional)
Hibernate JPA as the persistence provider
Entities (User, Course, Invoice)
Repositories/DAOs with Hibernate EntityManager
Services (UserService, CourseService, InvoiceService)
Controllers (UserController, CourseController)
Config class MySpringConfig that wires Hibernate + Spring transactions
This is a skeleton you can run either with Spring Boot or classic Spring (non-boot).
*/
package x.y.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = "x.y")
public class MySpringConfig {

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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
    	/*
    	Yes, LocalContainerEntityManagerFactoryBean is used in Spring to integrate with 
    	JPA providers like Hibernate.
    	/////////////////////////
		LocalContainerEntityManagerFactoryBean and HibernateJpaVendorAdapter are key 
		components in configuring Spring applications to use JPA (Java Persistence API) 
		with Hibernate as the persistence provider.
    	*/
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("x.y.model");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.setProperty("hibernate.show_sql", "true");

        emf.setJpaProperties(props);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}