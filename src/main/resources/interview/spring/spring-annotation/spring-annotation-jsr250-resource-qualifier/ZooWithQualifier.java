/*
Using @Autowired + @Qualifier (Spring-specific, by type first, 
then refine with qualifier)
////////////////////
Here, Spring sees two beans of type AnimalService (dogService 
and catService). Without @Qualifier, it throws an ambiguity error.
Adding @Qualifier("dogService") tells Spring exactly which 
bean to inject.
/////////////////////
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ZooWithQualifier {

    // Injection by TYPE first: finds multiple AnimalService beans
    // @Qualifier tells Spring which one to use
    @Autowired
    @Qualifier("dogService")
    private AnimalService animalService;

    public void printAnimal() {
        System.out.println("ZooWithQualifier: " + animalService.getAnimalType());
    }
}