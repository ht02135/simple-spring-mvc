package com.example.config;

import com.example.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
When using @Configuration + @Bean, Spring automatically registers 
the required BeanPostProcessors, so you don’t need 
@EnableAnnotationConfig explicitly (it’s implied).
However, if you’re mixing with component scanning (@ComponentScan), 
then the processors come in automatically.
////////////////////
4. Do we need to enable anything manually?
1>XML config: Yes → you need <context:annotation-config/> (or <context:component-scan/>).
2>Java config: No → AnnotationConfigApplicationContext already registers 
the post-processors, so RequiredAnnotationBeanPostProcessor is available automatically.
3>RequiredAnnotationBeanPostProcessor: You can declare it manually (rare), 
but normally <context:annotation-config/> or @ComponentScan registers it.
/////////////////////
Summary
1>@Required works only if RequiredAnnotationBeanPostProcessor is active.
2>In XML, enable it with <context:annotation-config/>.
3>In Java Config, it’s already enabled.
4>If you forget, Spring won’t enforce the property requirement and 
no exception will be thrown.
*/

@Configuration
public class AppConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("Jane Doe"); // must be set, or @Required will fail
        return student;
    }
}
