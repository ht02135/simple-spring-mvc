/*
2️⃣ Why you may not see it in some examples
Many examples are Spring Boot examples. Boot automatically scans 
repositories in the main package or subpackages of @SpringBootApplication. 
So people skip <jdbc:repositories>.
//////////////////
In this case, you don’t need the XML tag, because @EnableJdbcRepositories replaces it.
*/
@Configuration
@EnableJdbcRepositories(basePackageClasses = CustomerRepository.class)
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}
