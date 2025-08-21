/*
    4. ✅ Answering Directly:
	1>Do we need @ComponentScan (Java config)?
	Yes, if you want to detect @Component, @Service, etc.
	Not needed if you define beans only via @Bean methods.

	2>Do we need <context:component-scan/> (XML)?
	Yes, same reason — for detecting annotated beans.
	Not needed if you define beans only via <bean> tags.

	3>Do we need <context:annotation-config/>?
	Yes in XML config → it registers RequiredAnnotationBeanPostProcessor, 
	AutowiredAnnotationBeanPostProcessor, etc.
	No in Java config → AnnotationConfigApplicationContext automatically registers these.

	4>Do we need RequiredAnnotationBeanPostProcessor explicitly?
	No → it’s included when you use <context:annotation-config/> or @ComponentScan.
	Only declare explicitly if you don’t want the other processors but still want 
	@Required checked. 
	/////////////////////////
	hung : translate into what a pain in ass, just do following
	1>xml
	<context:annotation-config/>
	<context:component-scan base-package="com.example"/>
	2>config java
	@Configuration
	@ComponentScan(basePackages = "com.example") // enable scanning
	public class AppConfig
*/
package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example") // enable scanning
public class AppConfig {
    // No need to declare @Bean for Student since it's annotated with @Component
}
