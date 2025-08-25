import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    
    // Basic CRUD operations
    void insert(Employee employee);
    Employee findById(int id);
    void update(Employee employee);
    void delete(int id);
    
    // Additional methods based on your reference
    void insertFull(Employee employee);
    List<Employee> findAll();
    Employee findByEmail(String email);
    List<Employee> findByGenderAndName(String gender, String firstName, String lastName);
    List<Map<String, Object>> countEmployeesByGender();
    
    // Additional useful methods
    List<Employee> findBySalaryRange(int minSalary, int maxSalary);
    List<Employee> findByLastName(String lastName);
    int getTotalEmployeeCount();
    Employee findHighestPaidEmployee();
}