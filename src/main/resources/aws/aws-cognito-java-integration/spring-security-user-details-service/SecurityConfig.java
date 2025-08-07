@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyUserDetailsService myUserDetailsService;	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.antMatchers("/user/**").access("hasAnyRole('USER', 'ADMIN')")
		.and().formLogin()  //login configuration
                .loginPage("/customLogin.jsp")
                .loginProcessingUrl("/appLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/user")	
		.and().logout()  //logout configuration
		.logoutUrl("/appLogout") 
		.logoutSuccessUrl("/customLogin.jsp")
		.and().exceptionHandling() //exception handling configuration
		.accessDeniedPage("/error");
	} 	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
} 

