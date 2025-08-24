import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
    @Id
    private Long id;
    private String email;
    private String gender;
    private String firstName;
    private String lastName;
    private String name;

    // getters and setters
}
