/*
for some reason i like this better
/////////////////////////////
1>i could more or less care about spring data jdbc creating derived query
2>but ability to add @Query with simple/complex sql with named param with 
couple lines is huge plus
///////////////////////////////
Notes
1>Aggregation (COUNT, SUM) returns Map<String,Object> for flexible mapping.
2>Window functions / partitioning (ROW_NUMBER() OVER (PARTITION BY ...)) 
require mapping to Map<String,Object> or a DTO.
3>ORDER BY works as usual inside queries.
4>Can mix derived methods, custom SQL, and complex SQL in one repository.
////////////////////////////////
You now have a full repository + service pair that supports:
1>Simple derived queries
2>Custom SQL queries
3>Aggregations, grouping, ordering, window functions
*/
package com.example.repository;

import com.example.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CustomerRepository2 extends CrudRepository<Customer, Long> {

    // ================= Derived Queries overridden with @Query =================

    @Query("SELECT * FROM customer WHERE last_name = :lastName")
    List<Customer> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT * FROM customer WHERE gender = :gender")
    List<Customer> findByGender(@Param("gender") String gender);

    @Query("SELECT * FROM customer WHERE gender = :gender AND first_name = :firstName AND last_name = :lastName")
    List<Customer> findByGenderAndFirstNameAndLastName(@Param("gender") String gender,
                                                       @Param("firstName") String firstName,
                                                       @Param("lastName") String lastName);

    @Query("SELECT * FROM customer WHERE street = :street AND city = :city AND state = :state")
    List<Customer> findByStreetAndCityAndState(@Param("street") String street,
                                               @Param("city") String city,
                                               @Param("state") String state);

    // ================= Custom Queries =================

    @Query("SELECT * FROM customer")
    List<Customer> findAll();

    @Query("SELECT * FROM customer")
    List<Customer> findAllBy();

    @Query("SELECT * FROM customer WHERE created_date > :date")
    List<Customer> findRecentCustomers(@Param("date") LocalDateTime date);

    @Query("SELECT * FROM customer WHERE salary BETWEEN :minSalary AND :maxSalary AND active = true")
    List<Customer> findActiveBySalaryRange(@Param("minSalary") BigDecimal minSalary,
                                           @Param("maxSalary") BigDecimal maxSalary);

    @Query("""
        SELECT c.* FROM customer c
        JOIN department d ON c.dept_id = d.id
        WHERE d.name = :deptName AND c.active = :active
        ORDER BY c.salary DESC
        """)
    List<Customer> findByDepartmentNameAndActive(@Param("deptName") String departmentName,
                                                 @Param("active") Boolean active);

    @Query("UPDATE customer SET active = :status WHERE dept_id = :deptId")
    void updateActiveStatusByDepartment(@Param("deptId") Long departmentId,
                                        @Param("status") Boolean status);

    @Query("SELECT COUNT(*) FROM customer WHERE dept_id = :deptId AND salary > :salary")
    int countByDepartmentAndSalaryThreshold(@Param("deptId") Long departmentId,
                                            @Param("salary") BigDecimal salary);

    // ================= Complex SQL Examples =================

    // Count customers per gender
    @Query("SELECT gender, COUNT(*) AS cnt FROM customer GROUP BY gender ORDER BY cnt DESC")
    List<Map<String, Object>> countCustomersByGender();

    // Sum of salary per department
    @Query("SELECT dept_id, SUM(salary) AS totalSalary FROM customer GROUP BY dept_id ORDER BY totalSalary DESC")
    List<Map<String, Object>> sumSalaryByDepartment();

    // Partition by department: rank employees by salary
    @Query("""
        SELECT id, first_name, last_name, dept_id, salary,
               ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rank
        FROM customer
        """)
    List<Map<String, Object>> rankCustomersByDepartment();

    // Top 3 salaries per department
    @Query("""
        SELECT id, first_name, last_name, dept_id, salary
        FROM (
            SELECT *, ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rn
            FROM customer
        ) t
        WHERE t.rn <= 3
        """)
    List<Map<String, Object>> top3SalariesPerDepartment();
}
