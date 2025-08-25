import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeMapperImpl implements EmployeeMapper {
    
    private SqlMapClientTemplate sqlMapClientTemplate;
    
    // Setter injection for SqlMapClientTemplate
    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }
    
    @Override
    public void insert(Employee employee) {
        sqlMapClientTemplate.insert("Employee.insert", employee);
    }
    
    @Override
    public Employee findById(int id) {
        return (Employee) sqlMapClientTemplate.queryForObject("Employee.findById", id);
    }
    
    @Override
    public void update(Employee employee) {
        sqlMapClientTemplate.update("Employee.update", employee);
    }
    
    @Override
    public void delete(int id) {
        sqlMapClientTemplate.delete("Employee.delete", id);
    }
    
    @Override
    public void insertFull(Employee employee) {
        sqlMapClientTemplate.insert("Employee.insertFull", employee);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.findAll");
    }
    
    @Override
    public Employee findByEmail(String email) {
        return (Employee) sqlMapClientTemplate.queryForObject("Employee.findByEmail", email);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findByGenderAndName(String gender, String firstName, String lastName) {
        Map<String, Object> params = new HashMap<>();
        params.put("gender", gender);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.findByGenderAndName", params);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> countEmployeesByGender() {
        return (List<Map<String, Object>>) sqlMapClientTemplate.queryForList("Employee.countEmployeesByGender");
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findBySalaryRange(int minSalary, int maxSalary) {
        Map<String, Object> params = new HashMap<>();
        params.put("minSalary", minSalary);
        params.put("maxSalary", maxSalary);
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.findBySalaryRange", params);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findByLastName(String lastName) {
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.findByLastName", lastName);
    }
    
    @Override
    public int getTotalEmployeeCount() {
        Integer count = (Integer) sqlMapClientTemplate.queryForObject("Employee.getTotalEmployeeCount");
        return count != null ? count : 0;
    }
    
    @Override
    public Employee findHighestPaidEmployee() {
        return (Employee) sqlMapClientTemplate.queryForObject("Employee.findHighestPaidEmployee");
    }
    
    // Additional methods for more complex scenarios
    
    @SuppressWarnings("unchecked")
    public List<Employee> findEmployeesWithConditions(Map<String, Object> conditions) {
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.findEmployeesWithConditions", conditions);
    }
    
    public void updateSelective(Employee employee) {
        sqlMapClientTemplate.update("Employee.updateSelective", employee);
    }
    
    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesByName(String searchTerm) {
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.searchEmployeesByName", searchTerm);
    }
    
    @SuppressWarnings("unchecked")
    public List<Employee> findEmployeesAboveAverageSalary() {
        return (List<Employee>) sqlMapClientTemplate.queryForList("Employee.findEmployeesAboveAverageSalary");
    }
    
    public void batchInsert(List<Employee> employees) {
        sqlMapClientTemplate.insert("Employee.batchInsert", employees);
    }
}