package com.example.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Comprehensive example demonstrating SQL Exception handling and translation in Spring JDBC
 */
@Repository
public class ExceptionHandlingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private DataSource dataSource;

    // ===== 1. RAW SQL EXCEPTIONS - What happens without Spring's exception translation =====
    
    /**
     * Example showing raw SQLException - This is what you'd get without Spring
     * Spring automatically translates these into more specific DataAccessExceptions
     */
    public void demonstrateRawSQLException() {
        try {
            // This will cause a SQLException due to invalid SQL syntax
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INVALID SQL SYNTAX HERE");
            ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Raw SQLException:");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            System.out.println("Vendor-specific error code: " + e.getErrorCode());
            
            // Different databases have different error codes for the same logical error!
            // MySQL: Error code 1064, SQL State 42000
            // PostgreSQL: Different codes
            // Oracle: Different codes again
            // This is why Spring's exception translation is so valuable!
        }
    }

    // ===== 2. SPRING'S AUTOMATIC EXCEPTION TRANSLATION =====
    
    /**
     * Spring JdbcTemplate automatically translates SQLExceptions into DataAccessExceptions
     * This provides database-independent exception handling
     */
    public void demonstrateAutomaticExceptionTranslation() {
        try {
            // This will throw a BadSqlGrammarException (translated from SQLException)
            jdbcTemplate.queryForObject("SELECT * FROM non_existent_table", String.class);
        } catch (BadSqlGrammarException e) {
            System.out.println("Spring translated SQLException to BadSqlGrammarException:");
            System.out.println("Message: " + e.getMessage());
            System.out.println("SQL: " + e.getSql());
            System.out.println("Root cause: " + e.getRootCause().getClass().getSimpleName());
        } catch (DataAccessException e) {
            System.out.println("Caught DataAccessException: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
    }

    // ===== 3. DIFFERENT TYPES OF SPRING DATA ACCESS EXCEPTIONS =====
    
    /**
     * Demonstrates various Spring DataAccessExceptions that can occur
     */
    public void demonstrateVariousExceptions() {
        
        // 1. DuplicateKeyException - Constraint violation
        try {
            jdbcTemplate.update("INSERT INTO users (id, email) VALUES (1, 'duplicate@test.com')");
            jdbcTemplate.update("INSERT INTO users (id, email) VALUES (1, 'duplicate@test.com')"); // Duplicate
        } catch (DuplicateKeyException e) {
            System.out.println("DuplicateKeyException caught:");
            System.out.println("Message: " + e.getMessage());
            handleDuplicateKeyError(e);
        } catch (DataIntegrityViolationException e) {
            System.out.println("DataIntegrityViolationException caught:");
            System.out.println("Message: " + e.getMessage());
        }
        
        // 2. EmptyResultDataAccessException - No results when one expected
        try {
            String result = jdbcTemplate.queryForObject(
                "SELECT name FROM users WHERE id = ?", String.class, 99999);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("EmptyResultDataAccessException caught:");
            System.out.println("Expected 1 result, got 0");
            System.out.println("Message: " + e.getMessage());
        }
        
        // 3. IncorrectResultSizeDataAccessException - Wrong number of results
        try {
            // This might return multiple results when we expect one
            String result = jdbcTemplate.queryForObject(
                "SELECT name FROM users WHERE department = ?", String.class, "IT");
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println("IncorrectResultSizeDataAccessException caught:");
            System.out.println("Expected size: " + e.getExpectedSize());
            System.out.println("Actual size: " + e.getActualSize());
        }
        
        // 4. DataAccessResourceFailureException - Connection problems
        try {
            // Simulate connection failure (this might not actually throw in all cases)
            jdbcTemplate.execute("SELECT 1"); // This would fail if DB is down
        } catch (DataAccessResourceFailureException e) {
            System.out.println("DataAccessResourceFailureException caught:");
            System.out.println("Message: " + e.getMessage());
        }
    }

    // ===== 4. CUSTOM SQL EXCEPTION TRANSLATOR =====
    
    /**
     * Creating and using a custom SQLExceptionTranslator
     * This shows how you can customize exception translation behavior
     */
    public void demonstrateCustomExceptionTranslator() {
        // Create custom exception translator
        SQLExceptionTranslator customTranslator = new CustomSQLExceptionTranslator();
        
        // Set custom translator on JdbcTemplate
        jdbcTemplate.setExceptionTranslator(customTranslator);
        
        try {
            // This will use our custom translator
            jdbcTemplate.update("INSERT INTO users (id, email) VALUES (1, 'test@example.com')");
            jdbcTemplate.update("INSERT INTO users (id, email) VALUES (1, 'test@example.com')"); // Duplicate
        } catch (CustomBusinessException e) {
            System.out.println("Custom exception caught: " + e.getMessage());
        } catch (DataAccessException e) {
            System.out.println("Standard DataAccessException: " + e.getClass().getSimpleName());
        }
    }

    // ===== 5. SQL ERROR CODE EXCEPTION TRANSLATOR =====
    
    /**
     * Demonstrates SQLErrorCodeSQLExceptionTranslator - the default translator
     * This translator uses database-specific error codes to determine the appropriate exception
     */
    public void demonstrateSQLErrorCodeTranslator() {
        SQLErrorCodeSQLExceptionTranslator errorCodeTranslator = 
            new SQLErrorCodeSQLExceptionTranslator(dataSource);
        
        // Create a mock SQLException to translate
        SQLException sqlException = new SQLException("Duplicate entry", "23000", 1062); // MySQL duplicate key
        
        /*
        public DataAccessException translate(
        	String task,
 			@Nullable String sql,
 			SQLException ex)
 		------------------------------
		Parameters:
    		task - readable text describing the task being attempted
    		sql - the SQL query or update that caused the problem (if known)
    		ex - the offending SQLException
    	----------------------------
    	Returns:
    		the DataAccessException wrapping the SQLException, or null if no 
    		specific translation could be applied
        */
        DataAccessException translated = errorCodeTranslator.translate(
            "INSERT operation", "INSERT INTO users...", sqlException);
        
        System.out.println("SQLErrorCodeSQLExceptionTranslator results:");
        System.out.println("Original SQLException - Error Code: " + sqlException.getErrorCode());
        System.out.println("Original SQLException - SQL State: " + sqlException.getSQLState());
        System.out.println("Translated to: " + translated.getClass().getSimpleName());
        System.out.println("Message: " + translated.getMessage());
    }

    // ===== 6. SQL STATE EXCEPTION TRANSLATOR =====
    
    /**
     * Demonstrates SQLStateSQLExceptionTranslator - fallback translator
     * This translator uses standard SQL state codes instead of vendor-specific error codes
     */
    public void demonstrateSQLStateTranslator() {
        SQLStateSQLExceptionTranslator sqlStateTranslator = new SQLStateSQLExceptionTranslator();
        
        // Test different SQL states
        testSQLStateTranslation(sqlStateTranslator, "23000", 1062, "Integrity constraint violation");
        testSQLStateTranslation(sqlStateTranslator, "42000", 1064, "Syntax error");
        testSQLStateTranslation(sqlStateTranslator, "08000", 0, "Connection exception");
        testSQLStateTranslator(sqlStateTranslator, "40000", 0, "Transaction rollback");
    }
    
    private void testSQLStateTranslation(SQLStateSQLExceptionTranslator translator, 
                                       String sqlState, int errorCode, String message) {
        SQLException sqlException = new SQLException(message, sqlState, errorCode);
        DataAccessException translated = translator.translate("Test operation", "SELECT 1", sqlException);
        
        System.out.println("SQLStateSQLExceptionTranslator:");
        System.out.println("SQL State: " + sqlState + " -> " + 
                          (translated != null ? translated.getClass().getSimpleName() : "No translation"));
    }

    // ===== 7. EXCEPTION HANDLING PATTERNS =====
    
    /**
     * Best practices for handling different types of database exceptions
     */
    public User saveUserWithProperExceptionHandling(User user) {
        try {
            String sql = "INSERT INTO users (name, email, department) VALUES (:name, :email, :department)";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", user.getName());
            params.addValue("email", user.getEmail());
            params.addValue("department", user.getDepartment());
            
            namedParameterJdbcTemplate.update(sql, params);
            return user;
            
        } catch (DuplicateKeyException e) {
            // Handle duplicate email specifically
            System.out.println("User with email already exists: " + user.getEmail());
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists", e);
            
        } catch (DataIntegrityViolationException e) {
            // Handle other constraint violations
            System.out.println("Data integrity violation: " + e.getMessage());
            throw new InvalidUserDataException("Invalid user data provided", e);
            
        } catch (BadSqlGrammarException e) {
            // This indicates a programming error
            System.err.println("SQL syntax error - this should not happen in production!");
            System.err.println("SQL: " + e.getSql());
            throw new SystemException("Database query error", e);
            
        } catch (DataAccessResourceFailureException e) {
            // Database connection issues
            System.err.println("Database connection failed: " + e.getMessage());
            throw new DatabaseUnavailableException("Database is currently unavailable", e);
            
        } catch (DataAccessException e) {
            // Catch-all for other database exceptions
            System.err.println("Unexpected database error: " + e.getClass().getSimpleName());
            System.err.println("Message: " + e.getMessage());
            throw new SystemException("Unexpected database error", e);
        }
    }

    /**
     * Example of using PreparedStatementCallback with exception handling
     */
    public void demonstrateCallbackExceptionHandling() {
        try {
            jdbcTemplate.execute("SELECT * FROM users WHERE id = ?", 
                new PreparedStatementCallback<Void>() {
                    @Override
                    public Void doInPreparedStatement(PreparedStatement ps) throws SQLException {
                        ps.setInt(1, 1);
                        ResultSet rs = ps.executeQuery();
                        
                        // Simulate an SQLException within callback
                        if (!rs.next()) {
                            throw new SQLException("No user found", "02000", 100);
                        }
                        
                        return null;
                    }
                });
        } catch (DataAccessException e) {
            System.out.println("Exception in callback translated to: " + e.getClass().getSimpleName());
            System.out.println("Root cause: " + e.getRootCause().getClass().getSimpleName());
        }
    }

    // ===== 8. UTILITY METHODS =====
    
    private void handleDuplicateKeyError(DuplicateKeyException e) {
        System.out.println("Handling duplicate key error...");
        // Extract the root SQLException to get vendor-specific details
        if (e.getRootCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            System.out.println("Database error code: " + sqlEx.getErrorCode());
            System.out.println("SQL State: " + sqlEx.getSQLState());
            
            // Handle different database-specific error codes if needed
            switch (sqlEx.getErrorCode()) {
                case 1062: // MySQL duplicate key
                    System.out.println("MySQL duplicate key detected");
                    break;
                case 23505: // PostgreSQL unique violation
                    System.out.println("PostgreSQL unique constraint violation");
                    break;
                default:
                    System.out.println("Other duplicate key error: " + sqlEx.getErrorCode());
            }
        }
    }