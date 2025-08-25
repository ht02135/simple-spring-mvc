// ============================================================================
// 1. PLAIN JDBC EXAMPLE
// ============================================================================
// Raw JDBC - Manual connection management, SQL writing, exception handling

/*
1. PLAIN JDBC:
   ✓ Full control over SQL and connections
   ✓ Maximum performance (no abstraction overhead)
   ✗ Lots of boilerplate code
   ✗ Manual resource management
   ✗ Error-prone (connection leaks, SQL injection risks)
   ✗ Manual exception handling
*/

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlainJdbcUserDao {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String username = "user";
    private final String password = "password";
    
    public User findById(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement("SELECT id, name, email FROM users WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        } finally {
            // Manual cleanup
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public void save(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement(
                "INSERT INTO users (name, email) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}