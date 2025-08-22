import org.springframework.stereotype.Component;

@Component("catService")
public class CatService implements AnimalService {
    @Override
    public String getAnimalType() {
        return "Cat";
    }
}