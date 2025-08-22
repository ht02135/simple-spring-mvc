/*
Using @Resource (JSR-250, by name first, then by type)
////////////////////////
Here, Spring will inject the CatService because the name matches.
If name wasn’t specified, Spring would try to inject by field name 
(animalService), and if no match, then by type.
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
-----------------------------------------
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
///////////////////////////////////
If you're exclusively using the Spring Framework, you might wonder 
why you'd ever need @Resource when @Autowired and @Qualifier handle 
dependency injection so well. While it's true that @Autowired and 
@Qualifier are the standard Spring-centric approach, @Resource 
still has a valuable use case, primarily due to its portability 
and different lookup behavior.
----------------------
Lookup Semantics: Name vs. Type
3>The behavior of @Resource is sometimes preferred when you know the exact 
name of the bean you want to inject and want to make that intention clear. 
It can be seen as a more direct and explicit form of dependency injection 
by name, without needing an extra annotation like @Qualifier.
*/
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ZooWithResource {

    // Injection by NAME: will look for a bean named "catService"
    // If not found, falls back to by-type injection.
    @Resource(name = "catService")
    private AnimalService animalService;

    public void printAnimal() {
        System.out.println("ZooWithResource: " + animalService.getAnimalType());
    }
}