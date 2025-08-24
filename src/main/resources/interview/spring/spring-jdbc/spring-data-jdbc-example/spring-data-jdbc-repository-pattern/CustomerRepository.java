package com.example.repository;

import com.example.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	/*
	Yes, technically you can override any derived query method with @Query in Spring Data JDBC.
	1>Derived queries (like findByLastName) are automatically implemented by 
	Spring Data based on the method name.
	2>If you want custom SQL—for example, adding joins, filtering, or performance 
	tweaks—you can annotate the same method signature with @Query.
	3>Spring Data will ignore the method name derivation and use your SQL instead.
	*/

    // ================= Derived Query Methods =================

    List<Customer> findByLastName(String lastName);

    List<Customer> findByGender(String gender);

    List<Customer> findByGenderAndFirstNameAndLastName(String gender, String firstName, String lastName);

    List<Customer> findByStreetAndCityAndState(String street, String city, String state);

    // ================= Custom SQL Queries =================

    // Find recent customers created after a certain date
    @Query("SELECT * FROM customer WHERE created_date > :date")
    List<Customer> findRecentCustomers(@Param("date") LocalDateTime date);

    // Find active customers by salary range
    @Query("SELECT * FROM customer WHERE salary BETWEEN :minSalary AND :maxSalary AND active = true")
    List<Customer> findActiveBySalaryRange(@Param("minSalary") BigDecimal minSalary,
                                           @Param("maxSalary") BigDecimal maxSalary);

    // Join example: customers in a department
    @Query("""
        SELECT c.* FROM customer c
        JOIN department d ON c.dept_id = d.id
        WHERE d.name = :deptName AND c.active = :active
        ORDER BY c.salary DESC
        """)
    List<Customer> findByDepartmentNameAndActive(@Param("deptName") String departmentName,
                                                 @Param("active") Boolean active);

    // Update query example
    @Query("UPDATE customer SET active = :status WHERE dept_id = :deptId")
    void updateActiveStatusByDepartment(@Param("deptId") Long departmentId,
                                        @Param("status") Boolean status);

    // Count customers in a department with salary threshold
    @Query("SELECT COUNT(*) FROM customer WHERE dept_id = :deptId AND salary > :salary")
    int countByDepartmentAndSalaryThreshold(@Param("deptId") Long departmentId,
                                            @Param("salary") BigDecimal salary);
}
