/*
Key Advantages of Named Parameters:
1. Self-Documenting Code
Positional (?): You have to count positions to understand what each 
parameter represents
Named (:param): The SQL clearly shows what each parameter is for 
2. Error Prevention
Positional: Easy to mix up parameter order, especially with many parameters
Named: Parameter order doesn't matter, reducing errors significantly
4. Complex Queries
The example shows a complex query with 8 parameters:
Positional: Nearly impossible to understand without looking at the parameter assignment
Named: Crystal clear what each parameter represents directly in the SQL
*/
package com.example.comparison;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryComparison {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // ===== APPROACH 1: Using PreparedStatementCreator with positional parameters (?) =====
    
    /**
     * Insert user using PreparedStatementCreator with positional parameters
     * Advantages: Fine-grained control over PreparedStatement creation
     * Disadvantages: Verbose, parameter order matters, hard to maintain
     */
    public int addUserWithPreparedStatementCreator(String name, String email) {
        final String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);  // Parameter order matters!
                ps.setString(2, email); // Parameter order matters!
                return ps;
            }
        }, keyHolder);
        
        return keyHolder.getKey().intValue();
    }

    /**
     * Complex query with PreparedStatementCreator - Shows the pain of positional parameters
     * When you have many parameters, it becomes error-prone and hard to maintain
     */
    public List<User> searchUsersWithPreparedStatementCreator(String namePattern, String emailDomain, 
                                                              Integer minAge, Integer maxAge, 
                                                              String department, Boolean isActive) {
        // Notice how hard it is to track which parameter is which!
        final String sql = "SELECT * FROM users WHERE " +
                          "name LIKE ? AND " +
                          "email LIKE ? AND " +
                          "age BETWEEN ? AND ? AND " +
                          "department = ? AND " +
                          "is_active = ? " +
                          "ORDER BY name";
        
        return jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + namePattern + "%");    // What if you mix up the order?
                ps.setString(2, "%" + emailDomain);          // Easy to make mistakes here
                ps.setInt(3, minAge);                        // Parameter 3 or 4?
                ps.setInt(4, maxAge);                        // Getting confusing...
                ps.setString(5, department);                 // Is this parameter 5 or 6?
                ps.setBoolean(6, isActive);                  // Very error-prone!
                return ps;
            }
        }, new UserRowMapper());
    }

    // ===== APPROACH 2: Using NamedParameterJdbcTemplate with MapSqlParameterSource =====
    
    /**
     * Insert user using NamedParameterJdbcTemplate with MapSqlParameterSource
     * Advantages: Self-documenting, parameter order doesn't matter, type-safe
     * Best practice for complex queries with multiple parameters
     */
    public int addUserWithMapSqlParameterSource(String name, String email) {
        final String sql = "INSERT INTO users (name, email) VALUES (:name, :email)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);      // Self-documenting!
        params.addValue("email", email);   // Order doesn't matter!
        
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Complex query with MapSqlParameterSource - Much more readable and maintainable
     * Notice how clear and self-documenting this becomes!
     */
    public List<User> searchUsersWithMapSqlParameterSource(String namePattern, String emailDomain, 
                                                           Integer minAge, Integer maxAge, 
                                                           String department, Boolean isActive) {
        final String sql = "SELECT * FROM users WHERE " +
                          "name LIKE :namePattern AND " +
                          "email LIKE :emailPattern AND " +
                          "age BETWEEN :minAge AND :maxAge AND " +
                          "department = :department AND " +
                          "is_active = :isActive " +
                          "ORDER BY name";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("namePattern", "%" + namePattern + "%");  // Crystal clear what this is!
        params.addValue("emailPattern", "%" + emailDomain);       // Self-documenting
        params.addValue("minAge", minAge);                        // No confusion about order
        params.addValue("maxAge", maxAge);                        // Easy to understand
        params.addValue("department", department);                // Readable and maintainable
        params.addValue("isActive", isActive);                    // Much better!
        
        return namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
    }

    /**
     * Batch update with MapSqlParameterSource - Shows type safety advantages
     */
    public int[] batchUpdateUsersWithMapSqlParameterSource(List<User> users) {
        final String sql = "UPDATE users SET name = :name, email = :email, age = :age " +
                          "WHERE id = :id";
        
        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            batchParams[i] = new MapSqlParameterSource();
            batchParams[i].addValue("id", user.getId());
            batchParams[i].addValue("name", user.getName());
            batchParams[i].addValue("email", user.getEmail());
            batchParams[i].addValue("age", user.getAge());
        }
        
        return namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
    }

    // ===== APPROACH 3: Using NamedParameterJdbcTemplate with Map =====
    
    /**
     * Insert user using NamedParameterJdbcTemplate with plain Map
     * Advantages: Simple, concise, parameter order doesn't matter
     * Disadvantages: Less type-safe than MapSqlParameterSource, no method chaining
     */
    public int addUserWithMap(String name, String email) {
        final String sql = "INSERT INTO users (name, email) VALUES (:name, :email)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);       // Simple and clear
        params.put("email", email);     // Easy to understand
        
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Complex query with Map - Still much better than positional parameters
     */
    public List<User> searchUsersWithMap(String namePattern, String emailDomain, 
                                        Integer minAge, Integer maxAge, 
                                        String department, Boolean isActive) {
        final String sql = "SELECT * FROM users WHERE " +
                          "name LIKE :namePattern AND " +
                          "email LIKE :emailPattern AND " +
                          "age BETWEEN :minAge AND :maxAge AND " +
                          "department = :department AND " +
                          "is_active = :isActive " +
                          "ORDER BY name";
        
        Map<String, Object> params = new HashMap<>();
        params.put("namePattern", "%" + namePattern + "%");
        params.put("emailPattern", "%" + emailDomain);
        params.put("minAge", minAge);
        params.put("maxAge", maxAge);
        params.put("department", department);
        params.put("isActive", isActive);
        
        return namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
    }

    /**
     * Dynamic query building with Map - Shows flexibility advantage
     */
    public List<User> dynamicSearchWithMap(String name, String email, String department) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name LIKE :name");
            params.put("name", "%" + name + "%");
        }
        
        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE :email");
            params.put("email", "%" + email + "%");
        }
        
        if (department != null && !department.isEmpty()) {
            sql.append(" AND department = :department");
            params.put("department", department);
        }
        
        sql.append(" ORDER BY name");
        
        return namedParameterJdbcTemplate.query(sql.toString(), params, new UserRowMapper());
    }

    // ===== COMPARISON METHODS - Shows the problems with positional parameters =====
    
    /**
     * This method shows what happens when you have a complex query with positional parameters
     * Try to figure out what each ? represents without looking at the parameter setting!
     */
    public List<User> complexQueryWithPositionalParams(String name, String email, String dept, 
                                                       Integer age, Boolean active, String city, 
                                                       String country, Double salary) {
        // Can you tell what each ? represents just by looking at the SQL?
        String sql = "SELECT * FROM users u JOIN addresses a ON u.id = a.user_id " +
                    "WHERE u.name LIKE ? AND u.email LIKE ? AND u.department = ? " +
                    "AND u.age > ? AND u.is_active = ? AND a.city = ? " +
                    "AND a.country = ? AND u.salary > ? ORDER BY u.name, a.city";
        
        return jdbcTemplate.query(sql, new UserRowMapper(),
                "%" + name + "%",    // What if you accidentally swap these?
                "%" + email + "%",   // Very error-prone!
                dept,                // Easy to mix up order
                age,                 // Which parameter is this again?
                active,              // Getting lost in the parameters...
                city,                // Is this city or country?
                country,             // Confusing!
                salary);             // Hard to maintain
    }

    /**
     * Same complex query with named parameters - Much clearer!
     */
    public List<User> complexQueryWithNamedParams(String name, String email, String dept, 
                                                  Integer age, Boolean active, String city, 
                                                  String country, Double salary) {
        // Look how self-documenting this SQL is!
        String sql = "SELECT * FROM users u JOIN addresses a ON u.id = a.user_id " +
                    "WHERE u.name LIKE :namePattern AND u.email LIKE :emailPattern " +
                    "AND u.department = :department AND u.age > :minAge " +
                    "AND u.is_active = :isActive AND a.city = :city " +
                    "AND a.country = :country AND u.salary > :minSalary " +
                    "ORDER BY u.name, a.city";
        
        Map<String, Object> params = new HashMap<>();
        params.put("namePattern", "%" + name + "%");     // Crystal clear!
        params.put("emailPattern", "%" + email + "%");   // Self-documenting!
        params.put("department", dept);                  // No confusion!
        params.put("minAge", age);                       // Obvious what this is!
        params.put("isActive", active);                  // Easy to understand!
        params.put("city", city);                        // Clear distinction!
        params.put("country", country);                  // No mix-ups possible!
        params.put("minSalary", salary);                 // Maintainable!
        
        return namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
    }

    // ===== DEMONSTRATION OF ERROR-PRONE SCENARIOS =====
    
    /**
     * Shows how easy it is to make mistakes with positional parameters
     * What if someone changes the parameter order in the method signature?
     */
    public void updateUserPositionalParams(Long userId, String name, String email, String department) {
        String sql = "UPDATE users SET name = ?, email = ?, department = ? WHERE id = ?";
        // Oops! What if someone changes method parameter order but forgets to update this?
        jdbcTemplate.update(sql, name, email, department, userId);
    }

    /**
     * With named parameters, parameter order in method signature doesn't matter for SQL!
     */
    public void updateUserNamedParams(Long userId, String name, String email, String department) {
        String sql = "UPDATE users SET name = :name, email = :email, department = :dept WHERE id = :userId";
        
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);        // Order doesn't matter!
        params.put("name", name);           // Much safer!
        params.put("email", email);         // Self-documenting!
        params.put("dept", department);     // Error-resistant!
        
        namedParameterJdbcTemplate.update(sql, params);
    }