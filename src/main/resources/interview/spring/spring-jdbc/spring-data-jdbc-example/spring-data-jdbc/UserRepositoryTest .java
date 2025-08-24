// ============================================================================
// TESTING (Single Repository)
// ============================================================================

@DataJdbcTest
@Sql({"/schema.sql", "/test-data.sql"})
class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository; // Single repository to test!
    
    @Test
    void shouldTestAllQueryMethods() {
        // Test basic queries
        assertThat(userRepository.findByName("John Doe")).hasSize(1);
        assertThat(userRepository.findByActive(true)).hasSizeGreaterThan(0);
        
        // Test pattern matching
        assertThat(userRepository.findByEmailContaining("@example.com")).isNotEmpty();
        
        // Test range queries  
        assertThat(userRepository.findByAgeBetween(25, 35)).isNotEmpty();
        
        // Test custom queries
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        assertThat(userRepository.findRecentUsers(cutoff)).isNotEmpty();
        
        // All in one test class - comprehensive coverage!
    }
}