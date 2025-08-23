// ===== DEMONSTRATION CLASS =====

package com.example.demo;

import com.example.comparison.UserRepositoryComparison;
import com.example.comparison.UserRepositoryComparison.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ParameterComparisonDemo {
    
    @Autowired
    private UserRepositoryComparison userRepository;
    
    public void demonstrateDifferences() {
        System.out.println("=== JDBC Parameter Comparison Demo ===\n");
        
        // 1. Simple insert comparison
        System.out.println("1. SIMPLE INSERT OPERATIONS:");
        
        System.out.println("Using PreparedStatementCreator (verbose):");
        int id1 = userRepository.addUserWithPreparedStatementCreator("John Doe", "john@example.com");
        System.out.println("Inserted user with ID: " + id1);
        
        System.out.println("Using MapSqlParameterSource (recommended):");
        int id2 = userRepository.addUserWithMapSqlParameterSource("Jane Smith", "jane@example.com");
        System.out.println("Inserted user with ID: " + id2);
        
        System.out.println("Using Map (simple and clear):");
        int id3 = userRepository.addUserWithMap("Bob Wilson", "bob@example.com");
        System.out.println("Inserted user with ID: " + id3);
        
        // 2. Complex query comparison
        System.out.println("\n2. COMPLEX QUERY OPERATIONS:");
        
        System.out.println("Positional parameters - hard to read and error-prone:");
        List<User> users1 = userRepository.complexQueryWithPositionalParams(
            "John", "example.com", "IT", 25, true, "New York", "USA", 50000.0);
        
        System.out.println("Named parameters - self-documenting and maintainable:");
        List<User> users2 = userRepository.complexQueryWithNamedParams(
            "John", "example.com", "IT", 25, true, "New York", "USA", 50000.0);
        
        // 3. Dynamic query demonstration
        System.out.println("\n3. DYNAMIC QUERY BUILDING:");
        List<User> dynamicResults = userRepository.dynamicSearchWithMap("John", null, "IT");
        System.out.println("Dynamic search results: " + dynamicResults.size() + " users found");
        
        System.out.println("\n=== ADVANTAGES SUMMARY ===");
        System.out.println("POSITIONAL PARAMETERS (?):");
        System.out.println("❌ Parameter order matters - error-prone");
        System.out.println("❌ Hard to read and maintain with many parameters");
        System.out.println("❌ Easy to mix up parameter positions");
        System.out.println("❌ Not self-documenting");
        System.out.println("✅ Slightly more compact syntax");
        
        System.out.println("\nNAMED PARAMETERS (:param):");
        System.out.println("✅ Self-documenting SQL");
        System.out.println("✅ Parameter order doesn't matter");
        System.out.println("✅ Much easier to maintain");
        System.out.println("✅ Reduces errors in complex queries");
        System.out.println("✅ Better for dynamic query building");
        System.out.println("✅ MapSqlParameterSource provides type safety");
        System.out.println("❌ Slightly more verbose");
        
        System.out.println("\nRECOMMENDATION:");
        System.out.println("🎯 Use MapSqlParameterSource for complex queries with many parameters");
        System.out.println("🎯 Use Map<String, Object> for simple to medium complexity queries");
        System.out.println("🎯 Avoid positional parameters for queries with more than 2-3 parameters");
    }
}