import java.util.List;
import java.util.Map;

public interface EmployeeService {
    
    // Basic CRUD operations
    void createEmployee(Employee employee);
    Employee getEmployeeById(int id);
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);
    
    // Extended operations
    void createEmployeeFull(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeByEmail(String email);
    List<Employee> getEmployeesByGenderAndName(String gender, String firstName, String lastName);
    List<Map<String, Object>> getEmployeeCountByGender();
    
    // Business logic methods
    List<Employee> getEmployeesBySalaryRange(int minSalary, int maxSalary);
    List<Employee> getEmployeesByLastName(String lastName);
    int getTotalEmployeeCount();
    Employee getHighestPaidEmployee();
    
    // Business validation methods
    boolean isEmailUnique(String email);
    boolean canDeleteEmployee(int id);
    void giveRaise(int employeeId, int raiseAmount);
}