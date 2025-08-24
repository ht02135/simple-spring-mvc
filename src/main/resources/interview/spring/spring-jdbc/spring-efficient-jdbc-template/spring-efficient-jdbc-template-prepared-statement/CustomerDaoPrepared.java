/*
JdbcTemplate also supports PreparedStatement-style usage where you 
can pass parameters more explicitly. There are two common ways:
1>Using ? placeholders with jdbcTemplate.update(...) / query(...) 
(we already did this in the previous DAO).
2>Using PreparedStatementCreator or PreparedStatementSetter 
for more control.
////////////////////////
Key Differences
1>PreparedStatementCreator: Used when you want full control of how 
the statement is created (Connection → PreparedStatement).
2>PreparedStatementSetter: Used when SQL is fixed, but you just 
want to set parameters.
*/
package com.example.dao;

import com.example.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoPrepared {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDaoPrepared(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // -------- RowMapper (reusable) --------
    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        Customer c = new Customer();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        c.setGender(rs.getString("gender"));
        return c;
    };

    // -------- Insert with PreparedStatementCreator --------
    public void save(Customer customer) {
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO customers (name, email, gender) VALUES (?, ?, ?)"
            );
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getGender());
            return ps;
        });
    }

    // -------- Update with PreparedStatementSetter --------
    public int updateEmail(Long id, String newEmail) {
        return jdbcTemplate.update(
            "UPDATE customers SET email = ? WHERE id = ?",
            (PreparedStatementSetter) ps -> {
                ps.setString(1, newEmail);
                ps.setLong(2, id);
            }
        );
    }

    // -------- query() with PreparedStatementSetter --------
    public List<Customer> findCustomersByGender(String gender) {
        return jdbcTemplate.query(
            "SELECT * FROM customers WHERE gender = ?",
            (PreparedStatementSetter) ps -> ps.setString(1, gender),
            rowMapper
        );
    }

    // -------- queryForObject with PreparedStatementCreator --------
    public Customer findById(Long id) {
        return jdbcTemplate.queryForObject(
            (Connection con) -> {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE id = ?");
                ps.setLong(1, id);
                return ps;
            },
            rowMapper
        );
    }

    // -------- queryForObject (scalar) --------
    public String findEmailById(Long id) {
        return jdbcTemplate.queryForObject(
            (Connection con) -> {
                PreparedStatement ps = con.prepareStatement("SELECT email FROM customers WHERE id = ?");
                ps.setLong(1, id);
                return ps;
            },
            (rs, rowNum) -> rs.getString("email")  // inline RowMapper
        );
    }
}
