import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Override
    public void createEmployee(Employee employee) {
        if (employee.getEmail() != null && !isEmailUnique(employee.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        }
        employeeMapper.insert(employee);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
        return employeeMapper.findById(id);
    }
    
    @Override
    public void updateEmployee(Employee employee) {
        if (employee.getId() <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
        Employee existing = employeeMapper.findById(employee.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employee.getId());
        }
        employeeMapper.update(employee);
    }
    
    @Override
    public void deleteEmployee(int id) {
        if (!canDeleteEmployee(id)) {
            throw new IllegalStateException("Cannot delete employee with ID: " + id);
        }
        employeeMapper.delete(id);
    }
    
    @Override
    public void createEmployeeFull(Employee employee) {
        if (employee.getEmail() != null && !isEmailUnique(employee.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        }
        employeeMapper.insertFull(employee);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeMapper.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return employeeMapper.findByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByGenderAndName(String gender, String firstName, String lastName) {
        return employeeMapper.findByGenderAndName(gender, firstName, lastName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getEmployeeCountByGender() {
        return employeeMapper.countEmployeesByGender();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalaryRange(int minSalary, int maxSalary) {
        if (minSalary < 0 || maxSalary < 0 || minSalary > maxSalary) {
            throw new IllegalArgumentException("Invalid salary range");
        }
        return employeeMapper.findBySalaryRange(minSalary, maxSalary);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        return employeeMapper.findByLastName(lastName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getTotalEmployeeCount() {
        return employeeMapper.getTotalEmployeeCount();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Employee getHighestPaidEmployee() {
        return employeeMapper.findHighestPaidEmployee();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isEmailUnique(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return employeeMapper.findByEmail(email) == null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean canDeleteEmployee(int id) {
        Employee employee = employeeMapper.findById(id);
        return employee != null;
    }
    
    @Override
    public void giveRaise(int employeeId, int raiseAmount) {
        if (raiseAmount <= 0) {
            throw new IllegalArgumentException("Raise amount must be positive");
        }
        Employee employee = employeeMapper.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
        employee.setSalary(employee.getSalary() + raiseAmount);
        employeeMapper.update(employee);
    }
}