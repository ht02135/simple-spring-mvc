@Configuration
@ComponentScan("com.concretepage")
@EnableWebMvc
public class AppConfig {
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/secure/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
} 