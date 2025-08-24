@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    public User registerUser(String name) {
        User user = new User(name);
        return userDao.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    public void invoice(Long userId) {
        invoiceService.createPdf(userId);
    }

    // ================= NEW DAO METHODS =================

    @Transactional
    public User insertUser(String name, String email, String gender) {
        return userDao.insertUser(name, email, gender);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByGenderAndName(String gender, String firstName, String lastName) {
        return userDao.findByGenderAndName(gender, firstName, lastName);
    }

    @Transactional(readOnly = true)
    public List<Object[]> countUsersByGender() {
        return userDao.countUsersByGender();
    }
}
