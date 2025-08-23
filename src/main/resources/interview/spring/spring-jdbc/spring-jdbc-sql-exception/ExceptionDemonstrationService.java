/**
 * Service to demonstrate exception handling scenarios
 */
@Service
public class ExceptionDemonstrationService {
    
    @Autowired
    private ExceptionHandlingRepository repository;
    
    public void runAllDemonstrations() {
        System.out.println("=== SQL Exception Handling Demonstrations ===\n");
        
        System.out.println("1. Raw SQLException (without Spring translation):");
        try {
            repository.demonstrateRawSQLException();
        } catch (Exception e) {
            System.out.println("Caught: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        System.out.println();
        
        System.out.println("2. Spring's Automatic Exception Translation:");
        repository.demonstrateAutomaticExceptionTranslation();
        System.out.println();
        
        System.out.println("3. Various Spring DataAccessExceptions:");
        repository.demonstrateVariousExceptions();
        System.out.println();
        
        System.out.println("4. SQLErrorCodeSQLExceptionTranslator:");
        repository.demonstrateSQLErrorCodeTranslator();
        System.out.println();
        
        System.out.println("5. SQLStateSQLExceptionTranslator:");
        repository.demonstrateSQLStateTranslator();
        System.out.println();
        
        System.out.println("6. Custom Exception Translator:");
        repository.demonstrateCustomExceptionTranslator();
        System.out.println();
        
        System.out.println("7. PreparedStatementCallback Exception Handling:");
        repository.demonstrateCallbackExceptionHandling();
        System.out.println();
        
        System.out.println("=== Exception Translation Summary ===");
        System.out.println("✅ SQLException -> DataAccessException (automatic)");
        System.out.println("✅ Database-independent exception handling");
        System.out.println("✅ Specific exceptions for different error types");
        System.out.println("✅ Custom translators for business-specific needs");
        System.out.println("✅ Consistent exception hierarchy across different databases");
    }
}