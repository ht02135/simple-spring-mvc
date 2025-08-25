// Service class using Spring Data JDBC Repository
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User createUser(String name, String email) {
        User user = new User(name, email);
        return userRepository.save(user);  // Auto-generated implementation
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Auto-generated implementation
    }
    
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();  // Auto-generated
    }
    
    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);  // Generated from method name
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);  // Auto-generated implementation
    }
}