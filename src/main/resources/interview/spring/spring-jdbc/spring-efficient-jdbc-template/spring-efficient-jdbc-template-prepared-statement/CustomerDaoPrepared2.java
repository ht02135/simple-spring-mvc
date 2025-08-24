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
////////////////////////
Differences from your original DAO
1>save(...), findCustomerById(...), findCustomerByEmail(...) now 
explicitly use PreparedStatementCreator.
2>getCustomerEmailsByGender(...) uses PreparedStatementSetter.
3>findAllCustomers(...) shows a query without parameters, still 
via PreparedStatementCreator.
//////////////////////
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
import java.util.Map;

@Repository
public class CustomerDaoPrepared {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDaoPrepared(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ---------- RowMapper ----------
    private final RowMapper<Customer> rowMapper = (ResultSet rs, int rowNum) -> {
        Customer c = new Customer();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        c.setGender(rs.getString("gender"));
        return c;
    };

    // ---------- Basic Insert with PreparedStatementCreator ----------
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

    // ---------- query() ----------
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query(
                (Connection con) -> con.prepareStatement("SELECT * FROM customers"),
                rowMapper
        );
    }

    // ---------- queryForObject() ----------
    public Customer findCustomerById(Long id) {
        return jdbcTemplate.queryForObject(
                (Connection con) -> {
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE id = ?");
                    ps.setLong(1, id);
                    return ps;
                },
                rowMapper
        );
    }

    /*
    public Customer findCustomerByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE email = ?",
                rowMapper,
                email
        );
    }
    //////////////////////////////
    the reason to use below is you can set email to param at position 1
    */
    public Customer findCustomerByEmail(String email) {
        return jdbcTemplate.queryForObject(
                (Connection con) -> {
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE email = ?");
                    ps.setString(1, email);
                    return ps;
                },
                rowMapper
        );
    }

    // ---------- queryForList() ----------
    /*
    Why you don’t need to pass a PreparedStatementCreator manually here
	Because methods like queryForList(...), queryForMap(...), are convenience wrappers.
	Spring is actually doing this internally:
	Create a PreparedStatement from the SQL (with ? placeholders).
	Bind your parameter(s) (email in this case) to the PreparedStatement.
	Execute the statement and map results into List<Map<String,Object>>.
	So although you don’t see the PreparedStatement explicitly, Spring is 
	still using it.
    */
    public List<Map<String, Object>> findAllCustomersAsMapList() {
        return jdbcTemplate.queryForList("SELECT * FROM customers");
    }

    // Version without PreparedStatementCreator is better
    public List<Map<String, Object>> findCustomersByEmailAsMapList(String email) {
        return jdbcTemplate.queryForList(
                "SELECT * FROM customers WHERE email = ?",
                email
        );
    }
    
    // Version with PreparedStatementCreator is pain in the ass
    public List<Map<String, Object>> findCustomersByEmailAsMapList2(String email) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE email = ?");
                ps.setString(1, email);   // explicit parameter binding
                return ps;
            },
            (rs, rowNum) -> {
                // convert each row into a Map<String, Object>
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                Map<String, Object> rowMap = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowMap.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                return rowMap;
            }
        );
    }


    // ---------- queryForMap() ----------
    public Map<String, Object> findCustomerAsMapById(Long id) {
        return jdbcTemplate.queryForMap(
                "SELECT * FROM customers WHERE id = ?",
                id
        );
    }

    // ---------- queryForList with single column ----------
    public List<String> getAllCustomerNames() {
        return jdbcTemplate.query(
                (Connection con) -> con.prepareStatement("SELECT name FROM customers"),
                (rs, rowNum) -> rs.getString("name")
        );
    }

    public List<String> getCustomerEmailsByGender(String gender) {
        return jdbcTemplate.query(
                "SELECT email FROM customers WHERE gender = ?",
                (PreparedStatementSetter) ps -> ps.setString(1, gender),
                (rs, rowNum) -> rs.getString("email")
        );
    }
}
