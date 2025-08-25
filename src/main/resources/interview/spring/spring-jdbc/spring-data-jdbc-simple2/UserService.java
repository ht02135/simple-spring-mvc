// ============================================================================
// SERVICE LAYER USAGE (Single Repository)
// ============================================================================

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository; // Single dependency!
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // All user operations through single repository
    public User createUser(String name, String email, Long departmentId) {
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }
        
        User user = new User(name, email);
        user.setDepartmentId(departmentId);
        return userRepository.save(user);
    }
    
    public List<User> getActiveUsersByDepartment(String departmentName) {
        return userRepository.findByDepartmentNameAndActive(departmentName, true);
    }
    
    public List<User> searchUsers(String keyword) {
        return userRepository.findByNameContaining(keyword);
    }
    
    public List<User> getHighEarners(BigDecimal threshold) {
        return userRepository.findBySalaryGreaterThan(threshold);
    }
    
    public void deactivateOldUsers(LocalDateTime cutoffDate) {
        List<User> oldUsers = userRepository.findByCreatedDateBefore(cutoffDate);
        oldUsers.forEach(user -> user.setActive(false));
        userRepository.saveAll(oldUsers);
    }
    
    // All methods use the SAME repository - clean and simple!
}