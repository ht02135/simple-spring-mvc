/*
REPOSITORY INTERFACE IMPLEMENTATION FLOW:

1. You define interface extending CrudRepository
2. Spring Data JDBC creates PROXY implementation at RUNTIME
3. Proxy delegates to SimpleJdbcRepository (default implementation)
4. Query methods are parsed and converted to SQL by QueryCreator
5. Custom @Query methods bypass query creation and use provided SQL

KEY CLASSES INVOLVED:
├── org.springframework.data.repository.core.support.RepositoryFactorySupport
├── org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory
├── org.springframework.data.jdbc.repository.support.SimpleJdbcRepository
├── org.springframework.data.jdbc.core.convert.JdbcConverter
└── org.springframework.data.jdbc.core.JdbcAggregateTemplate
*/
// SPRING DATA JDBC - Method name or custom query
// ============================================================================
// REAL-WORLD APPROACH: SINGLE USER REPOSITORY
// ============================================================================

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Single comprehensive User Repository
 * Contains ALL user-related database operations
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    // ========== BASIC QUERY METHODS (Generated from method names) ==========
    
    // Simple property queries
    List<User> findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByActive(Boolean active);
    List<User> findByAge(Integer age);
    
    // Pattern matching queries
    List<User> findByNameContaining(String namePattern);
    List<User> findByEmailContaining(String emailPattern);
    List<User> findByNameStartingWith(String prefix);
    List<User> findByEmailEndingWith(String suffix);
    
    // Case insensitive queries
    List<User> findByNameIgnoreCase(String name);
    List<User> findByNameStartingWithIgnoreCase(String prefix);
    
    // Range queries
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);
    List<User> findBySalaryGreaterThan(BigDecimal minSalary);
    List<User> findBySalaryLessThan(BigDecimal maxSalary);
    List<User> findByCreatedDateAfter(LocalDateTime date);
    List<User> findByCreatedDateBefore(LocalDateTime date);
    
    // Compound queries (AND conditions)
    List<User> findByNameAndEmail(String name, String email);
    List<User> findByActiveAndAgeBetween(Boolean active, Integer minAge, Integer maxAge);
    List<User> findByDepartmentIdAndActive(Long departmentId, Boolean active);
    
    // Compound queries (OR conditions)
    List<User> findByNameOrEmail(String name, String email);
    List<User> findByAgeOrSalaryGreaterThan(Integer age, BigDecimal salary);
    
    // Null checks
    List<User> findByDepartmentIdIsNull();
    List<User> findByDepartmentIdIsNotNull();
    List<User> findBySalaryIsNull();
    
    // Boolean queries
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();
    
    // Collection queries
    List<User> findByAgeIn(List<Integer> ages);
    List<User> findByDepartmentIdIn(List<Long> departmentIds);
    List<User> findByNameNotIn(List<String> excludedNames);
    
    // Ordering
    List<User> findByActiveOrderByNameAsc(Boolean active);
    List<User> findByDepartmentIdOrderBySalaryDesc(Long departmentId);
    List<User> findAllByOrderByCreatedDateDesc();
    
    // Limiting results
    List<User> findFirst5ByActiveOrderByCreatedDateDesc(Boolean active);
    List<User> findTop10ByDepartmentIdOrderBySalaryDesc(Long departmentId);
    Optional<User> findFirstByEmailContainingOrderByCreatedDateDesc(String domain);
    
    // Existence checks
    boolean existsByEmail(String email);
    boolean existsByNameAndDepartmentId(String name, Long departmentId);
    
    // Count queries
    long countByActive(Boolean active);
    long countByDepartmentId(Long departmentId);
    long countByAgeBetween(Integer minAge, Integer maxAge);
    
    // Delete queries
    void deleteByActive(Boolean active);
    void deleteByCreatedDateBefore(LocalDateTime cutoffDate);
    long deleteByDepartmentIdAndActiveFalse(Long departmentId);
    
    // ========== CUSTOM SQL QUERIES (When method names get too complex) ==========
    
    @Query("SELECT * FROM users WHERE created_date > :date")
    List<User> findRecentUsers(@Param("date") LocalDateTime date);
    
    @Query("SELECT * FROM users WHERE salary BETWEEN :minSalary AND :maxSalary AND active = true")
    List<User> findActiveBySalaryRange(@Param("minSalary") BigDecimal minSalary, 
                                       @Param("maxSalary") BigDecimal maxSalary);
    
    @Query("""
        SELECT u.* FROM users u 
        JOIN departments d ON u.dept_id = d.id 
        WHERE d.name = :deptName AND u.active = :active
        ORDER BY u.salary DESC
        """)
    List<User> findByDepartmentNameAndActive(@Param("deptName") String departmentName, 
                                             @Param("active") Boolean active);
    
    @Query("""
        SELECT u.* FROM users u 
        JOIN departments d ON u.dept_id = d.id 
        WHERE d.budget > :budget AND u.salary < :maxSalary
        ORDER BY d.budget DESC, u.salary ASC
        """)
    List<User> findByDepartmentBudgetAndMaxSalary(@Param("budget") BigDecimal budget, 
                                                  @Param("maxSalary") BigDecimal maxSalary);
    
    @Query("""
        SELECT u.* FROM users u 
        WHERE u.age > (SELECT AVG(age) FROM users WHERE dept_id = u.dept_id)
        """)
    List<User> findUsersOlderThanDepartmentAverage();
    
    @Query("SELECT COUNT(*) FROM users WHERE dept_id = :deptId AND salary > :salary")
    int countByDepartmentAndSalaryThreshold(@Param("deptId") Long departmentId, 
                                           @Param("salary") BigDecimal salary);
    
    @Query("""
        SELECT DISTINCT u.* FROM users u 
        WHERE u.email LIKE %:domain 
        AND u.created_date >= :startDate 
        AND u.created_date <= :endDate
        ORDER BY u.created_date DESC
        """)
    List<User> findByEmailDomainInDateRange(@Param("domain") String domain,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
    
    // Update queries (Note: Spring Data JDBC supports limited update operations)
    @Query("UPDATE users SET active = :status WHERE dept_id = :deptId")
    void updateActiveStatusByDepartment(@Param("deptId") Long departmentId, 
                                       @Param("status") Boolean status);
}