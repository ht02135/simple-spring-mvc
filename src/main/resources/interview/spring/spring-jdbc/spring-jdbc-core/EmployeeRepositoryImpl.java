/*
Class	Primary Use
JdbcTemplate	Executing general SQL queries and updates.
NamedParameterJdbcTemplate	Executing SQL queries with named parameters.
-----------------------------------
SimpleJdbcInsert	Simplifying INSERT statements.
SimpleJdbcCall	Calling stored procedures and functions.
SimpleJdbcTemplate	Deprecated; use JdbcTemplate instead.
*/
// EmployeeRepositoryImpl.java - Implementation using all JDBC Templates
package com.example.repository.impl;

import com.example.mapper.EmployeeRowMapper;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final SimpleJdbcCall simpleJdbcCall;
    
    @Autowired
    public EmployeeRepositoryImpl(DataSource dataSource,
                                  JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        
        // Configure SimpleJdbcInsert
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("employees")
            .usingGeneratedKeyColumns("id");
        
        // Configure SimpleJdbcCall (example for stored procedure)
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource)
            .withProcedureName("get_employee_info");
    }
    
    // Using SimpleJdbcInsert for INSERT operations
    @Override
    public Employee save(Employee employee) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", employee.getName());
        parameters.put("email", employee.getEmail());
        parameters.put("department", employee.getDepartment());
        parameters.put("salary", employee.getSalary());
        
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        employee.setId(id.longValue());
        return employee;
    }
    
    // Using JdbcTemplate for simple SELECT with parameters
    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try {
            Employee employee = jdbcTemplate.queryForObject(sql, 
                new EmployeeRowMapper(), id);
            return Optional.ofNullable(employee);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    // Using JdbcTemplate for SELECT ALL
    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees ORDER BY id";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }
    
    // Using NamedParameterJdbcTemplate for queries with named parameters
    @Override
    public List<Employee> findByDepartment(String department) {
        String sql = "SELECT * FROM employees WHERE department = :dept ORDER BY name";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dept", department);
        
        return namedParameterJdbcTemplate.query(sql, params, new EmployeeRowMapper());
    }
    
    // Using NamedParameterJdbcTemplate for UPDATE
    @Override
    public void update(Employee employee) {
        String sql = "UPDATE employees SET name = :name, email = :email, " +
                    "department = :department, salary = :salary WHERE id = :id";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", employee.getName());
        params.addValue("email", employee.getEmail());
        params.addValue("department", employee.getDepartment());
        params.addValue("salary", employee.getSalary());
        params.addValue("id", employee.getId());
        
        namedParameterJdbcTemplate.update(sql, params);
    }
    
    // Using JdbcTemplate for DELETE
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    // Using JdbcTemplate for aggregate queries
    @Override
    public int getEmployeeCount() {
        String sql = "SELECT COUNT(*) FROM employees";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    // Using NamedParameterJdbcTemplate for aggregate with parameters
    @Override
    public Double getAverageSalaryByDepartment(String department) {
        String sql = "SELECT AVG(salary) FROM employees WHERE department = :dept";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dept", department);
        
        return namedParameterJdbcTemplate.queryForObject(sql, params, Double.class);
    }
    
    // Using NamedParameterJdbcTemplate for batch update
    @Override
    public void updateSalaryByPercentage(String department, double percentage) {
        String sql = "UPDATE employees SET salary = salary * (1 + :percentage / 100) " +
                    "WHERE department = :dept";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("percentage", percentage);
        params.addValue("dept", department);
        
        namedParameterJdbcTemplate.update(sql, params);
    }
    
    // Using SimpleJdbcCall for stored procedures
    @Override
    public String callStoredProcedure(Long employeeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("emp_id", employeeId);
        
        Map<String, Object> result = simpleJdbcCall.execute(params);
        return result.get("employee_info").toString();
    }
}