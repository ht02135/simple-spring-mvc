/*
Core Query Methods:
1. queryForObject() - Single Results
Single Object: queryForObject(sql, rowMapper, params)
Primitive Values: queryForObject(sql, String.class, params)
Throws exceptions if 0 or >1 results found

2. query() - Multiple Results
With RowMapper: query(sql, rowMapper, params)
With RowCallbackHandler: query(sql, callbackHandler, params)
Lambda RowMapper: query(sql, (rs, rowNum) -> mapObject(rs), params)

3. queryForList() - Lists
List of Maps: queryForList(sql, params) → List<Map<String,Object>>
List of Primitives: queryForList(sql, String.class, params) → List<String>

4. queryForMap() - Single Row as Map
Single Map: queryForMap(sql, params) → Map<String,Object>
Useful for aggregate queries and when you don't need objects
*/
package com.example.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Comprehensive example demonstrating JdbcTemplate query methods for fetching records
 */
@Repository
public class UserQueryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ===== 1. queryForObject() - Single Object Results =====

    /**
     * queryForObject() - Fetch a single object, expects exactly one result
     * Throws EmptyResultDataAccessException if no results
     * Throws IncorrectResultSizeDataAccessException if more than one result
     */
    public User findUserById(Long id) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    /**
     * queryForObject() with BeanPropertyRowMapper - Automatic mapping by property names
     */
    public User findUserByIdUsingBeanMapper(Long id) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    /**
     * queryForObject() for single primitive values
     */
    public String getUserNameById(Long id) {
        String sql = "SELECT name FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Integer getUserCount() {
        String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Double getAverageSalary() {
        String sql = "SELECT AVG(salary) FROM users";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    /**
     * queryForObject() with multiple parameters
     */
    public User findUserByEmailAndDepartment(String email, String department) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE email = ? AND department = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email, department);
    }

    // ===== 2. query() - Multiple Object Results =====

    /**
     * query() - Fetch multiple objects using custom RowMapper
     */
    public List<User> findAllUsers() {
        String sql = "SELECT id, name, email, department, salary FROM users ORDER BY name";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    /**
     * query() with parameters
     */
    public List<User> findUsersByDepartment(String department) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE department = ? ORDER BY name";
        return jdbcTemplate.query(sql, new UserRowMapper(), department);
    }

    /**
     * query() with multiple parameters and conditions
     */
    public List<User> findUsersBySalaryRange(Double minSalary, Double maxSalary) {
        String sql = "SELECT id, name, email, department, salary FROM users " +
                    "WHERE salary BETWEEN ? AND ? ORDER BY salary DESC";
        return jdbcTemplate.query(sql, new UserRowMapper(), minSalary, maxSalary);
    }

    /**
     * query() using BeanPropertyRowMapper
     */
    public List<User> findUsersByDepartmentUsingBeanMapper(String department) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE department = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), department);
    }

    /**
     * query() with inline RowMapper (Lambda expression)
     */
    public List<User> findActiveUsersWithLambda() {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE is_active = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setDepartment(rs.getString("department"));
            user.setSalary(rs.getDouble("salary"));
            return user;
        });
    }

    // ===== 3. queryForList() - List of Maps or Primitive Values =====

    /**
     * queryForList() returning List<Map<String, Object>>
     * Useful when you don't want to create a specific class
     */
    public List<Map<String, Object>> findAllUsersAsMapList() {
        String sql = "SELECT id, name, email, department, salary FROM users";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * queryForList() with parameters returning List<Map<String, Object>>
     */
    public List<Map<String, Object>> findUsersByDepartmentAsMapList(String department) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE department = ?";
        return jdbcTemplate.queryForList(sql, department);
    }

    /**
     * queryForList() returning List of primitive values
     */
    public List<String> getAllUserNames() {
        String sql = "SELECT name FROM users ORDER BY name";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> getUserEmailsByDepartment(String department) {
        String sql = "SELECT email FROM users WHERE department = ? ORDER BY email";
        return jdbcTemplate.queryForList(sql, String.class, department);
    }

    public List<Double> getAllSalaries() {
        String sql = "SELECT salary FROM users ORDER BY salary DESC";
        return jdbcTemplate.queryForList(sql, Double.class);
    }

    // ===== 4. queryForMap() - Single Row as Map =====

    /**
     * queryForMap() - Returns a single row as Map<String, Object>
     * Throws EmptyResultDataAccessException if no results
     * Throws IncorrectResultSizeDataAccessException if more than one result
     */
    public Map<String, Object> findUserAsMapById(Long id) {
        String sql = "SELECT id, name, email, department, salary FROM users WHERE id = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public Map<String, Object> getUserStatsByDepartment(String department) {
        String sql = "SELECT department, COUNT(*) as user_count, AVG(salary) as avg_salary, " +
                    "MIN(salary) as min_salary, MAX(salary) as max_salary " +
                    "FROM users WHERE department = ? GROUP BY department";
        return jdbcTemplate.queryForMap(sql, department);
    }

    // ===== 5. query() with RowCallbackHandler - Processing Large Result Sets =====

    /**
     * query() with RowCallbackHandler - For processing large result sets without loading all into memory
     * Good for reports, data export, etc.
     */
    public void processAllUsersWithCallback(UserProcessor processor) {
        String sql = "SELECT id, name, email, department, salary FROM users";
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setDepartment(rs.getString("department"));
                user.setSalary(rs.getDouble("salary"));
                processor.process(user);
            }
        });
    }

    /**
     * Using RowCallbackHandler with parameters
     */
    public void generateSalaryReportByDepartment(String department, ReportGenerator reportGenerator) {
        String sql = "SELECT name, salary FROM users WHERE department = ? ORDER BY salary DESC";
        jdbcTemplate.query(sql, rs -> {
            String name = rs.getString("name");
            Double salary = rs.getDouble("salary");
            reportGenerator.addEntry(name, salary);
        }, department);
    }

    // ===== 6. Complex Queries with Joins =====

    /**
     * Complex query with JOIN using custom RowMapper
     */
    public List<UserWithAddress> findUsersWithAddresses() {
        String sql = "SELECT u.id, u.name, u.email, u.department, u.salary, " +
                    "a.street, a.city, a.state, a.zip_code " +
                    "FROM users u " +
                    "LEFT JOIN addresses a ON u.id = a.user_id " +
                    "ORDER BY u.name";
        return jdbcTemplate.query(sql, new UserWithAddressRowMapper());
    }

    public List<UserWithAddress> findUsersWithAddressesByCity(String city) {
        String sql = "SELECT u.id, u.name, u.email, u.department, u.salary, " +
                    "a.street, a.city, a.state, a.zip_code " +
                    "FROM users u " +
                    "INNER JOIN addresses a ON u.id = a.user_id " +
                    "WHERE a.city = ? " +
                    "ORDER BY u.name";
        return jdbcTemplate.query(sql, new UserWithAddressRowMapper(), city);
    }

    // ===== 7. Aggregate Queries =====

    /**
     * Various aggregate queries returning different data types
     */
    public Map<String, Object> getDepartmentStatistics() {
        String sql = "SELECT COUNT(*) as total_users, " +
                    "COUNT(DISTINCT department) as total_departments, " +
                    "AVG(salary) as avg_salary, " +
                    "MIN(salary) as min_salary, " +
                    "MAX(salary) as max_salary " +
                    "FROM users";
        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> getSalaryStatsByDepartment() {
        String sql = "SELECT department, " +
                    "COUNT(*) as user_count, " +
                    "AVG(salary) as avg_salary, " +
                    "MIN(salary) as min_salary, " +
                    "MAX(salary) as max_salary " +
                    "FROM users " +
                    "GROUP BY department " +
                    "ORDER BY avg_salary DESC";
        return jdbcTemplate.queryForList(sql);
    }

    // ===== 8. Conditional Queries =====

    /**
     * Query with optional parameters
     */
    public List<User> searchUsers(String namePattern, String department, Double minSalary) {
        StringBuilder sql = new StringBuilder("SELECT id, name, email, department, salary FROM users WHERE 1=1");
        
        if (namePattern != null && !namePattern.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (department != null && !department.isEmpty()) {
            sql.append(" AND department = ?");
        }
        if (minSalary != null) {
            sql.append(" AND salary >= ?");
        }
        sql.append(" ORDER BY name");

        // Build parameters array dynamically
        Object[] params = buildParametersArray(namePattern, department, minSalary);
        
        return jdbcTemplate.query(sql.toString(), new UserRowMapper(), params);
    }

    private Object[] buildParametersArray(String namePattern, String department, Double minSalary) {
        java.util.List<Object> paramList = new java.util.ArrayList<>();
        
        if (namePattern != null && !namePattern.isEmpty()) {
            paramList.add("%" + namePattern + "%");
        }
        if (department != null && !department.isEmpty()) {
            paramList.add(department);
        }
        if (minSalary != null) {
            paramList.add(minSalary);
        }
        
        return paramList.toArray();
    }