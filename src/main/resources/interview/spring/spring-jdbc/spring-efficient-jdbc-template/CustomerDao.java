/*
JdbcTemplate DAO
//////////////////////
///This gives you a flexible setup:
Use JdbcTemplate (DAO) OR Spring Data JDBC (Repository).
Switch between HikariCP, Tomcat JDBC, or Commons DBCP by 
changing the dataSource bean.
Transaction management is enabled with @Transactional.
*/
package com.example.dao;

import com.example.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDao {
    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> rowMapper = new RowMapper<>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer c = new Customer();
            c.setId(rs.getLong("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            return c;
        }
    };

    public void save(Customer customer) {
        jdbcTemplate.update("INSERT INTO customers(name,email) VALUES(?,?)",
                customer.getName(), customer.getEmail());
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", rowMapper);
    }
}
