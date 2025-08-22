/*
//////////////////////////
clear Java/Spring example comparing 
@Resource (JSR-250) vs @Autowired + @Qualifier (Spring-specific), 
showing how they differ in terms of injection by name vs injection 
by type with filtering.
1>@Resource: This is the primary JSR-250 annotation for dependency injection. 
It is used to inject a resource, typically by name. If a name is not specified, 
it defaults to injecting a resource with the same name as the annotated field 
or setter method
2>The @Qualifier annotation is a Spring-specific annotation used in conjunction 
with @Autowired to resolve ambiguity when multiple beans of the same type are 
present. When you have two or more beans of the same type, Spring's @Autowired 
annotation doesn't know which one to inject, leading to a NoUniqueBeanDefinitionException.
//////////////////////////
Key Takeaways
1>@Resource (JSR-250, portable):
Injects by name first (field/property name or name attribute).
Falls back to by type if no name match.
2>@Autowired + @Qualifier (Spring-specific):
Injects by type first.
If multiple candidates exist, @Qualifier is used as a filter to pick the right bean.
//////////////////////////
When mainly using the Spring Framework, @Qualifier is generally preferred 
over @Resource for dependency injection
--------------------------
Reasons for preferring @Qualifier in Spring:
1>Spring-native and richer support:
@Qualifier is a Spring-specific annotation and integrates seamlessly with 
Spring's dependency injection mechanisms. It offers more flexibility and 
features within the Spring ecosystem compared to @Resource
2>Resolving ambiguity with @Autowired:
@Qualifier is designed to be used in conjunction with Spring's @Autowired 
annotation to precisely specify which bean to inject when multiple beans 
of the same type are present in the application context. This prevents 
NoUniqueBeanDefinitionException.
3>Constructor and multi-argument method injection:
@Qualifier supports injection into constructors and multi-argument methods, 
which is not possible with @Resource.
////////////////////////
*/
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class InjectionDemoApplication implements CommandLineRunner {

    private final ZooWithResource zooWithResource;
    private final ZooWithQualifier zooWithQualifier;

    public InjectionDemoApplication(ZooWithResource zooWithResource, ZooWithQualifier zooWithQualifier) {
        this.zooWithResource = zooWithResource;
        this.zooWithQualifier = zooWithQualifier;
    }

    public static void main(String[] args) {
        SpringApplication.run(InjectionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        zooWithResource.printAnimal();   // prints: ZooWithResource: Cat
        zooWithQualifier.printAnimal();  // prints: ZooWithQualifier: Dog
    }
}