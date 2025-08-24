/*
2. SPRING JDBC:
   ✓ Eliminates boilerplate code
   ✓ Automatic resource management
   ✓ Exception translation to Spring's DataAccessException
   ✓ Template pattern reduces complexity
   ✗ Still need to write SQL manually
   ✗ Manual object mapping (RowMapper)
*/
// Spring JDBC Configuration
@Configuration
@EnableTransactionManagement
public class SpringJdbcConfig {
    
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}