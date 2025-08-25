/*
3. SPRING DATA JDBC:
   ✓ Minimal code required
   ✓ Repository pattern with automatic implementations
   ✓ Query derivation from method names
   ✓ Built-in CRUD operations
   ✓ Type-safe queries
   ✗ Less control over SQL optimization
   ✗ Learning curve for query derivation rules
   ✗ May generate suboptimal queries in complex scenarios
*/
// Spring Data JDBC Configuration
@Configuration
@EnableJdbcRepositories
public class SpringDataJdbcConfig extends AbstractJdbcConfiguration {
    
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        return dataSource;
    }
}