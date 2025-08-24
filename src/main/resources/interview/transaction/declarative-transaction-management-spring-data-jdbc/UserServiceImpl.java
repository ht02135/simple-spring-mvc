import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findByIdExplicit(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllExplicit();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> findByGenderAndName(String gender, String firstName, String lastName) {
        return userRepository.findByGenderAndName(gender, firstName, lastName);
    }
}
