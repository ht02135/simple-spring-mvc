// ============================================================================
// 3. SPRING DATA JDBC EXAMPLE
// ============================================================================
// Spring Data JDBC - Repository pattern with automatic query generation

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Entity class with Spring Data annotations
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    
    // Constructors, getters, setters
    public User() {}
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}