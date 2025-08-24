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
import java.util.Map;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ---------- RowMapper ----------
    private final RowMapper<Customer> rowMapper = new RowMapper<>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer c = new Customer();
            c.setId(rs.getLong("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setGender(rs.getString("gender")); // assuming gender column exists
            return c;
        }
    };

    // ---------- Basic Insert ----------
    public void save(Customer customer) {
        jdbcTemplate.update(
                "INSERT INTO customers (name, email, gender) VALUES (?, ?, ?)",
                customer.getName(), customer.getEmail(), customer.getGender()
        );
    }

    // ---------- query() ----------
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query("SELECT * FROM customers", rowMapper);
    }

    // ---------- queryForObject() ----------
    public Customer findCustomerById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE id = ?",
                rowMapper,
                id
        );
    }

    public Customer findCustomerByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE email = ?",
                rowMapper,
                email
        );
    }

    // ---------- queryForList() ----------
    public List<Map<String, Object>> findAllCustomersAsMapList() {
        return jdbcTemplate.queryForList("SELECT * FROM customers");
    }

    public List<Map<String, Object>> findCustomersByEmailAsMapList(String email) {
        return jdbcTemplate.queryForList(
                "SELECT * FROM customers WHERE email = ?",
                email
        );
    }

    // ---------- queryForMap() ----------
    // (only works if query returns exactly ONE row)
    public Map<String, Object> findCustomerAsMapById(Long id) {
        return jdbcTemplate.queryForMap("SELECT * FROM customers WHERE id = ?", id);
    }

    // ---------- queryForList with single column ----------
    public List<String> getAllCustomerNames() {
        return jdbcTemplate.queryForList(
                "SELECT name FROM customers",
                String.class
        );
    }

    public List<String> getCustomerEmailsByGender(String gender) {
        return jdbcTemplate.queryForList(
                "SELECT email FROM customers WHERE gender = ?",
                String.class,
                gender
        );
    }
}
