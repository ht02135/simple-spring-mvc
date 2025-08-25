/*
2. Why MyBatis (UserMapper, InvoiceMapper) services only have:
@Transactional
-----------------
1>With MyBatis/iBatis, Spring usually wires a DataSourceTransactionManager 
instead of a JpaTransactionManager.
2>Defaults of @Transactional are Propagation.REQUIRED and 
Isolation.DEFAULT (which means “use database’s default isolation level” → MySQL = READ_COMMITTED).
3>So writing only @Transactional is equivalent to what you see in 
the Hibernate example, unless you need to override.
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
    private UserMapper userMapper;

    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    public User registerUser(String name) {
        User user = new User();
        user.setName(name);
        userMapper.insert(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userMapper.findById(id);
    }

    @Transactional
    public void invoice(Long userId) {
        invoiceService.createPdf(userId);
    }

    // ================= NEW METHODS =================

    @Transactional
    public User insertFullUser(String name, String email, String gender, String firstName, String lastName) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setGender(gender);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userMapper.insertFull(user);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByGenderAndName(String gender, String firstName, String lastName) {
        return userMapper.findByGenderAndName(gender, firstName, lastName);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> countUsersByGender() {
        return userMapper.countUsersByGender();
    }
}
