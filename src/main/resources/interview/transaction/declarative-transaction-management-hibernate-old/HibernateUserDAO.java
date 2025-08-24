// --- Hibernate Implementation ---
@Repository("userDAO")
public class HibernateUserDAO extends HibernateDaoSupport implements UserDAO {

    private static Logger log = Logger.getLogger(HibernateUserDAO.class);

    @Autowired
    public HibernateUserDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    @Override
    public void save(User user) {
        log.info("Saving user: " + user);
        getHibernateTemplate().save(user);
    }

    @Override
    public User findById(Long id) {
        return (User) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("from User u where u.id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    @Override
    public List<User> findAll() {
        return (List<User>) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("from User u");
            return query.list();
        });
    }

    @Override
    public User findByEmail(String email) {
        return (User) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("from User u where u.email = :email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
    }

    @Override
    public List<User> findByGenderAndName(String gender, String firstName, String lastName) {
        return (List<User>) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery(
                "from User u where u.gender = :gender and u.firstName = :firstName and u.lastName = :lastName"
            );
            query.setParameter("gender", gender);
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            return query.list();
        });
    }
}
