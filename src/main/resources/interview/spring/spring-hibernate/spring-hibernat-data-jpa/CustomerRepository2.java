/*
Spring Data JPA Benefits:

Simplified development with automatic repository implementations
Less boilerplate code - you only need to define interfaces
Built-in support for pagination, sorting, and auditing
Query derivation from method names (e.g., findByLastName)
Type safety with generic repository interfaces
///////////////////////
spring data jpa VS spring data jdbv
-----------------------
The Similarities You NoticedYes, both frameworks share:
Repository interfaces extending CrudRepository, PagingAndSortingRepository, etc.
Method name derivation (e.g., findByLastName)
@Query annotations for custom queries
@Modifying annotations for updates
Same Spring Data abstractions
--------------------------
The Real Differences Are Under the Hood
1. What Happens at Runtime
JPA: Generates JPQL → SQL, manages entity lifecycle, caching, lazy loading
JDBC: Direct SQL execution, simple object mapping, no lifecycle management
2. Entity Behavior
JPA: Entities are "managed" - changes are automatically detected and persisted
JDBC: Objects are just POJOs - you must explicitly save changes
///////////////////////////
Spring Data JPA vs Spring Data JDBC
--------------------------------
1>Spring Data JPA
ORM-based: Uses Object-Relational Mapping through JPA providers (like Hibernate)
Entity management: Full entity lifecycle management with persistence contexts
Lazy loading: Supports lazy loading of relationships
Caching: Built-in first and second-level caching
Complex relationships: Handles complex entity relationships (@OneToMany, @ManyToMany, etc.)
Query abstraction: High-level abstraction over SQL
Performance overhead: More overhead due to ORM features
-----------------------------
2>Spring Data JDBC
Direct JDBC: Works directly with JDBC, no ORM layer
Simple mapping: Simple object-to-table mapping without complex entity management
No lazy loading: All data is loaded eagerly
No caching: No built-in caching mechanisms
Aggregate roots: Follows Domain-Driven Design aggregate patterns
Explicit SQL control: More direct control over SQL execution
Better performance: Lower overhead, more predictable performance
//////////////////////////
our CustomerRepository is missing the deleteById method and the 
findById method, which are both used in the CustomerService. 
Although JpaRepository already provides these, they were unintentionally 
removed or not included in your provided code, so they must be added 
back to make the service layer methods functional.
//////////////////////////
he CustomerService file is already correct and requires no changes. 
It's using methods like save, findAll, findById, and deleteById, which 
are all part of the Spring Data JPA JpaRepository interface. As long 
as CustomerRepository extends JpaRepository<Customer, Long>, these 
methods are automatically provided at runtime. The only missing 
methods that needed to be added to the repository interface were 
findById and deleteById to make it clear which methods were required 
for the service layer to function.
///////////////////////////
*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // ================= Derived Queries overridden with @Query =================

    @Query("SELECT c FROM Customer c WHERE c.lastName = :lastName")
    List<Customer> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT c FROM Customer c WHERE c.gender = :gender")
    List<Customer> findByGender(@Param("gender") String gender);

    @Query("SELECT c FROM Customer c WHERE c.gender = :gender AND c.firstName = :firstName AND c.lastName = :lastName")
    List<Customer> findByGenderAndFirstNameAndLastName(@Param("gender") String gender,
                                                       @Param("firstName") String firstName,
                                                       @Param("lastName") String lastName);

    @Query("SELECT c FROM Customer c WHERE c.street = :street AND c.city = :city AND c.state = :state")
    List<Customer> findByStreetAndCityAndState(@Param("street") String street,
                                               @Param("city") String city,
                                               @Param("state") String state);

    // ================= Custom Queries =================

    // The following two methods were already present in JpaRepository.
    // They are added here for clarity, although not strictly necessary since
    // JpaRepository provides them by default.
    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    List<Customer> findAllBy();

    @Query("SELECT c FROM Customer c WHERE c.createdDate > :date")
    List<Customer> findRecentCustomers(@Param("date") LocalDateTime date);

    @Query("SELECT c FROM Customer c WHERE c.salary BETWEEN :minSalary AND :maxSalary AND c.active = true")
    List<Customer> findActiveBySalaryRange(@Param("minSalary") BigDecimal minSalary,
                                           @Param("maxSalary") BigDecimal maxSalary);

    @Query("""
            SELECT c FROM Customer c
            JOIN Department d ON c.deptId = d.id
            WHERE d.name = :deptName AND c.active = :active
            ORDER BY c.salary DESC
            """)
    List<Customer> findByDepartmentNameAndActive(@Param("deptName") String departmentName,
                                                 @Param("active") Boolean active);

    @Modifying
    @Query("UPDATE Customer c SET c.active = :status WHERE c.deptId = :deptId")
    void updateActiveStatusByDepartment(@Param("deptId") Long departmentId,
                                        @Param("status") Boolean status);

    int countByDepartmentAndSalaryThreshold(@Param("deptId") Long departmentId,
                                            @Param("salary") BigDecimal salary);

    void deleteById(Long id);


    // ================= Complex Native SQL Examples =================

    // Count customers per gender
    @Query(value = "SELECT gender, COUNT(*) AS cnt FROM customer GROUP BY gender ORDER BY cnt DESC",
            nativeQuery = true)
    List<Map<String, Object>> countCustomersByGender();

    // Sum of salary per department
    @Query(value = "SELECT dept_id, SUM(salary) AS totalSalary FROM customer GROUP BY dept_id ORDER BY totalSalary DESC",
            nativeQuery = true)
    List<Map<String, Object>> sumSalaryByDepartment();

    // Partition by department: rank employees by salary
    @Query(value = """
            SELECT id, first_name, last_name, dept_id, salary,
                    ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rank_num
            FROM customer
            """, nativeQuery = true)
    List<Map<String, Object>> rankCustomersByDepartment();

    // Top 3 salaries per department
    @Query(value = """
            SELECT id, first_name, last_name, dept_id, salary
            FROM (
                SELECT *, ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rn
                FROM customer
            ) t
            WHERE t.rn <= 3
            """, nativeQuery = true)
    List<Map<String, Object>> top3SalariesPerDepartment();
}