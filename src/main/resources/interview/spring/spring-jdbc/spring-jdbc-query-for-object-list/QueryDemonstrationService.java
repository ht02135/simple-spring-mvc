// ===== DEMO SERVICE =====

package com.example.service;

import com.example.query.UserQueryRepository;
import com.example.query.UserQueryRepository.User;
import com.example.query.UserQueryRepository.UserWithAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryDemonstrationService {

    @Autowired
    private UserQueryRepository userRepository;

    public void demonstrateAllQueryMethods() {
        System.out.println("=== JdbcTemplate Query Methods Demonstration ===\n");

        // 1. queryForObject() examples
        System.out.println("1. queryForObject() - Single Object Results:");
        try {
            User user = userRepository.findUserById(1L);
            System.out.println("Found user: " + user);
            
            String userName = userRepository.getUserNameById(1L);
            System.out.println("User name: " + userName);
            
            Integer userCount = userRepository.getUserCount();
            System.out.println("Total users: " + userCount);
            
            Double avgSalary = userRepository.getAverageSalary();
            System.out.println("Average salary: $" + String.format("%.2f", avgSalary));
            
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No user found with ID 1");
        }

        // 2. query() examples
        System.out.println("\n2. query() - Multiple Object Results:");
        List<User> allUsers = userRepository.findAllUsers();
        System.out.println("All users (" + allUsers.size() + "):");
        allUsers.forEach(System.out::println);

        List<User> itUsers = userRepository.findUsersByDepartment("IT");
        System.out.println("\nIT Department users (" + itUsers.size() + "):");
        itUsers.forEach(System.out::println);

        List<User> highEarners = userRepository.findUsersBySalaryRange(70000.0, 100000.0);
        System.out.println("\nHigh earners ($70k-$100k) (" + highEarners.size() + "):");
        highEarners.forEach(System.out::println);

        // 3. queryForList() examples
        System.out.println("\n3. queryForList() - Lists of Maps and Primitives:");
        List<Map<String, Object>> usersAsMap = userRepository.findAllUsersAsMapList();
        System.out.println("Users as Map (first 2):");
        usersAsMap.stream().limit(2).forEach(System.out::println);

        List<String> userNames = userRepository.getAllUserNames();
        System.out.println("\nAll user names: " + userNames);

        List<String> itEmails = userRepository.getUserEmailsByDepartment("IT");
        System.out.println("IT department emails: " + itEmails);

        // 4. queryForMap() examples
        System.out.println("\n4. queryForMap() - Single Row as Map:");
        try {
            Map<String, Object> userMap = userRepository.findUserAsMapById(1L);
            System.out.println("User as Map: " + userMap);

            Map<String, Object> itStats = userRepository.getUserStatsByDepartment("IT");
            System.out.println("IT Department stats: " + itStats);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No data found");
        }

        // 5. RowCallbackHandler example
        System.out.println("\n5. RowCallbackHandler - Processing Large Result Sets:");
        System.out.println("Processing users with callback:");
        userRepository.processAllUsersWithCallback(user -> 
            System.out.println("Processed: " + user.getName() + " - $" + user.getSalary()));

        // 6. Complex queries with joins
        System.out.println("\n6. Complex Queries with Joins:");
        List<UserWithAddress> usersWithAddresses = userRepository.findUsersWithAddresses();
        System.out.println("Users with addresses (" + usersWithAddresses.size() + "):");
        usersWithAddresses.forEach(System.out::println);

        // 7. Aggregate queries
        System.out.println("\n7. Aggregate Queries:");
        Map<String, Object> stats = userRepository.getDepartmentStatistics();
        System.out.println("Overall statistics: " + stats);

        List<Map<String, Object>> deptStats = userRepository.getSalaryStatsByDepartment();
        System.out.println("Department statistics:");
        deptStats.forEach(System.out::println);

        // 8. Dynamic search
        System.out.println("\n8. Dynamic Search:");
        List<User> searchResults = userRepository.searchUsers("John", "IT", 60000.0);
        System.out.println("Search results: " + searchResults);

        System.out.println("\n=== Query Methods Summary ===");
        System.out.println("✅ queryForObject() - Single object or primitive value");
        System.out.println("✅ query() - Multiple objects with RowMapper");
        System.out.println("✅ queryForList() - List of Maps or primitive values");
        System.out.println("✅ queryForMap() - Single row as Map");
        System.out.println("✅ query() with RowCallbackHandler - Large result set processing");
        System.out.println("✅ Complex joins and aggregate queries supported");
    }
}