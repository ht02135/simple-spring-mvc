/*
Explanation
1>findUserById(): This method uses queryForObject(). It takes a SQL 
string with a positional parameter (?) and the parameter value (id). 
The UserRowMapper is crucial here; it tells JdbcTemplate how to create 
a User object from the ResultSet returned by the query.
2>findAllUsers(): This method uses query(), which is designed to return 
a List. It also uses the UserRowMapper to transform each row into a 
User object.
3>UserRowMapper: This inner class implements the RowMapper interface. 
Its mapRow() method is called for each row in the ResultSet, and it's 
responsible for extracting data from the ResultSet and populating a User object.
*/
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to fetch a single user by ID
    public User findUserById(int id) {
        String sql = "SELECT id, name, email FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    // Method to fetch a list of all users
    public List<User> findAllUsers() {
        String sql = "SELECT id, name, email FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // A RowMapper implementation to map a ResultSet row to a User object
    private static final class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}