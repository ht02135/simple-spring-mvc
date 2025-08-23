/*
2. Create a Data Access Object (DAO)

Next, create a DAO class that uses the JdbcTemplate to perform 
queries. We'll use a User class to represent our data.
*/
// ===== MODEL CLASSES =====

public static class User {
    private Long id;
    private String name;
    private String email;
    private String department;
    private Double salary;

    // Constructors
    public User() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + 
               "', department='" + department + "', salary=" + salary + "}";
    }
}