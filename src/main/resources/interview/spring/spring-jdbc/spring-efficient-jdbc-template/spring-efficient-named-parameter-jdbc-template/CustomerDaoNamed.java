/*
you spotted the main ergonomic advantage of NamedParameterJdbcTemplate.
///////////////////////////////
1. Classic JdbcTemplate
With plain JdbcTemplate, you must supply parameters either:
1a>Inline as varargs:
jdbcTemplate.queryForObject(
    "SELECT * FROM customers WHERE id = ?",
    rowMapper,
    id
);
1b>Or via PreparedStatementSetter / PreparedStatementCreator:
jdbcTemplate.query(
    con -> {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE id = ?");
        ps.setLong(1, id);
        return ps;
    },
    rowMapper
);
Both cases explicitly use PreparedStatement under the hood.
/////////////////////////////
2. NamedParameterJdbcTemplate
With NamedParameterJdbcTemplate, you don’t deal with PreparedStatement directly.
Instead, you pass a map (or MapSqlParameterSource) of parameter names → 
values, and Spring internally binds them to a PreparedStatement.

String sql = "SELECT * FROM customers WHERE id = :id";
MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("id", id);
Customer customer = namedJdbcTemplate.queryForObject(sql, params, rowMapper);

You never see PreparedStatement, but Spring still creates one behind the scenes.
It replaces :id with ? internally, binds your values in order, and executes safely.
///////////////////////////////
4. How Spring Does It
Spring’s NamedParameterJdbcTemplate:
Parses the SQL with :param placeholders.
Converts it into ? placeholders for JDBC.
Creates a PreparedStatement.
Uses a PreparedStatementSetter internally to bind the values from your map.
So under the hood:
NamedParameterJdbcTemplate → JdbcTemplate → PreparedStatement.
*/
package com.example.dao;

import com.example.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDaoNamed {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CustomerDaoNamed(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
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

    // ---------- Basic Insert ----------
    public void save(Customer customer) {
        String sql = "INSERT INTO customers (name, email, gender) VALUES (:name, :email, :gender)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("gender", customer.getGender());
        namedJdbcTemplate.update(sql, params);
    }

    // ---------- query() ----------
    public List<Customer> findAllCustomers() {
    	// rowMapper returns RowMapper<Customer>
        return namedJdbcTemplate.query("SELECT * FROM customers", rowMapper);
    }

    // ---------- queryForObject() ----------
    public Customer findCustomerById(Long id) {
        String sql = "SELECT * FROM customers WHERE id = :id";
        // rowMapper returns RowMapper<Customer>
        return namedJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                rowMapper);
    }

    public Customer findCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = :email";
        // rowMapper returns RowMapper<Customer>
        return namedJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("email", email),
                rowMapper);
    }

    // ---------- queryForList() ----------
    public List<Map<String, Object>> findAllCustomersAsMapList() {
        return namedJdbcTemplate.queryForList("SELECT * FROM customers",
                new MapSqlParameterSource());
    }

    public List<Map<String, Object>> findCustomersByEmailAsMapList(String email) {
        String sql = "SELECT * FROM customers WHERE email = :email";
        return namedJdbcTemplate.queryForList(sql,
                new MapSqlParameterSource("email", email));
    }

    // ---------- queryForMap() ----------
    public Map<String, Object> findCustomerAsMapById(Long id) {
        String sql = "SELECT * FROM customers WHERE id = :id";
        return namedJdbcTemplate.queryForMap(sql,
                new MapSqlParameterSource("id", id));
    }
    
    public Map<String, Object> findCustomerAsMapById2(Long id) {
        String sql = "SELECT * FROM customers WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return namedJdbcTemplate.queryForMap(sql, params);
    }

    // ---------- queryForList with single column ----------
    public List<String> getAllCustomerNames() {
        String sql = "SELECT name FROM customers";
        // you still need to new MapSqlParameterSource even if you not
        // passing any param
        MapSqlParameterSource params = new MapSqlParameterSource();
        // (rs, rowNum) -> rs.getString("name") is kind of mapping returns customer name
        return namedJdbcTemplate.query(sql,
        		params,
                (rs, rowNum) -> rs.getString("name"));
    }

    public List<String> getCustomerEmailsByGender(String gender) {
        String sql = "SELECT email FROM customers WHERE gender = :gender";
        return namedJdbcTemplate.query(sql,
                new MapSqlParameterSource("gender", gender),
                // (rs, rowNum) -> rs.getString("email")) is kind of mapping returns customer email
                (rs, rowNum) -> rs.getString("email"));
    }
    
    //queryForList() with single column and parameter → getCustomerEmailsByGender
    public List<String> getCustomerEmailsByGender2(String gender) {
        String sql = "SELECT email FROM customers WHERE gender = :gender";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("gender", gender);
        return namedJdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getString("email")
        );
    }
}
