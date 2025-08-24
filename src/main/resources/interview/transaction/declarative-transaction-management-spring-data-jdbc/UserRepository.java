import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE id = :id")
    User findByIdExplicit(@Param("id") Long id);

    @Query("SELECT * FROM users")
    List<User> findAllExplicit();

    @Query("SELECT * FROM users WHERE email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT * FROM users WHERE gender = :gender AND first_name = :firstName AND last_name = :lastName")
    List<User> findByGenderAndName(@Param("gender") String gender,
                                   @Param("firstName") String firstName,
                                   @Param("lastName") String lastName);
}
