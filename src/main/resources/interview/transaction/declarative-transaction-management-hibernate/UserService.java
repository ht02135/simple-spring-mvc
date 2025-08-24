/*
1. Why Hibernate InvoiceService has:
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.READ_COMMITTED
)
-----------------
1>When using Hibernate/JPA (EntityManager), Spring manages transactions 
through a JpaTransactionManager.
2>By default, it uses:
2a>Propagation = REQUIRED (join existing transaction or create a new 
one if none exists).
2b>Isolation = default of DB (often READ_COMMITTED in MySQL/Postgres).
2c>In your Hibernate service, someone explicitly wrote propagation 
and isolation — but it’s actually redundant, because those are already defaults.
3>i.e. Even if you wrote just @Transactional, Spring would treat it 
the same way.
/////////////////////
3. Why UserService (both iBatis & Hibernate) only uses @Transactional
-----------------
1>UserService methods don’t need special isolation — they’re mostly 
just CRUD inserts/selects.
2>The default Spring settings (REQUIRED + DB default isolation) are enough.
3>That’s why you only see @Transactional.
/////////////////
4. Key takeaway
---------------
@Transactional without attributes =
propagation = REQUIRED, isolation = DEFAULT (→ DB’s isolation, e.g. READ_COMMITTED).
1>Hibernate example just made these explicit.
2>iBatis example left them implicit.
3>Both behave the same in practice unless you deliberately change them.
*/
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
