@Configuration
@EnableJpaRepositories("com.concretepage.repository")
public class DBConfig {
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
	    LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
	    lcemfb.setJpaVendorAdapter(getJpaVendorAdapter());
	    lcemfb.setDataSource(dataSource());
	    lcemfb.setPackagesToScan("com.concretepage.entity");
	    return lcemfb;
	}
	
	@Bean
	public JpaVendorAdapter getJpaVendorAdapter() {
	    JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
	    return adapter;
	}	

	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
	    dataSource.setUrl("jdbc:hsqldb:hsql://localhost:9001");
	    dataSource.setUsername("sa");
	    dataSource.setPassword("");
	    return dataSource;
	}
	
	@Bean(name="transactionManager")
	public PlatformTransactionManager txManager(){
	    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
			getEntityManagerFactoryBean().getObject());
	    return jpaTransactionManager;
	}	
} 