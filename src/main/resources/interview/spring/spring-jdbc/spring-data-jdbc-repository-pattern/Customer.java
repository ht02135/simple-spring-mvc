import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
public class Customer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String street;
    private String city;
    private String state;

    // getters and setters
}
