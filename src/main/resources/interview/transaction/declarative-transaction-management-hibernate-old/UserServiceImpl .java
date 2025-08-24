@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User user) {
        userDAO.save(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> findByGenderAndName(String gender, String firstName, String lastName) {
        return userDAO.findByGenderAndName(gender, firstName, lastName);
    }

    // setter injection (optional)
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
