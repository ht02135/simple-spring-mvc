// 2. ENTITY CLASSES
// ============================================================================

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Table("users")
public class User {
    @Id
    private Long id;
    
    private String name;
    private String email;
    private Integer age;
    private BigDecimal salary;
    private Boolean active;
    
    @Column("dept_id")
    private Long departmentId;
    
    @Column("created_date")
    private LocalDateTime createdDate;
    
    // Constructors
    public User() {}
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.active = true;
        this.createdDate = LocalDateTime.now();
    }
    
    public User(String name, String email, Integer age, BigDecimal salary, Long departmentId) {
        this(name, email);
        this.age = age;
        this.salary = salary;
        this.departmentId = departmentId;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}