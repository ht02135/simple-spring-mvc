public class Employee {
    private int id;
    private String first_name;
    private String last_name;
    private int salary;
    private String email;
    private String gender;
    
    // Default constructor
    public Employee() {}
    
    // Constructor with basic fields
    public Employee(String fname, String lname, int salary) {
        this.first_name = fname;
        this.last_name = lname;
        this.salary = salary;
    }
    
    // Constructor with all fields
    public Employee(String fname, String lname, int salary, String email, String gender) {
        this.first_name = fname;
        this.last_name = lname;
        this.salary = salary;
        this.email = email;
        this.gender = gender;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return first_name;
    }
    
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
    
    public String getLastName() {
        return last_name;
    }
    
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    
    public int getSalary() {
        return salary;
    }
    
    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}