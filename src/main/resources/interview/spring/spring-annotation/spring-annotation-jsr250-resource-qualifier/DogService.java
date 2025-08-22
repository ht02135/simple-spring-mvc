import org.springframework.stereotype.Component;

@Component("dogService")
public class DogService implements AnimalService {
    @Override
    public String getAnimalType() {
        return "Dog";
    }
}