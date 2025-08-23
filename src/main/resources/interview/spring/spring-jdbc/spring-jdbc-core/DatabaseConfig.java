// DatabaseConfig.java - Alternative Java Configuration (instead of XML)
package com.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Value("${db.driver:org.h2.Driver}")
    private String driverClassName;
    
    @Value("${db.url:jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1}")
    private String url;
    
    @Value("${db.username:sa}")
    private String username;
    
    @Value("${db.password:}")
    private String password;
    
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
            .withTableName("employees")
            .usingGeneratedKeyColumns("id");
    }
    
    @Bean
    public SimpleJdbcCall simpleJdbcCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource)
            .withProcedureName("get_employee_info");
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}