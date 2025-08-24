// ============================================================================
// 2. SPRING JDBC EXAMPLE
// ============================================================================
// Spring JDBC - Simplified with JdbcTemplate, automatic resource management

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SpringJdbcUserDao {
    private final JdbcTemplate jdbcTemplate;
    
    public SpringJdbcUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public User findById(Long id) {
        String sql = "SELECT id, name, email FROM users WHERE id = ?";
        
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }
    
    public List<User> findAll() {
        String sql = "SELECT id, name, email FROM users";
        
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
    
    public void save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, 
                new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);
        
        user.setId(keyHolder.getKey().longValue());
    }
    
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getId());
    }
    
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        jdbcTemplate.update(sql, id);
    }
    
    // RowMapper for mapping ResultSet to User object
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}