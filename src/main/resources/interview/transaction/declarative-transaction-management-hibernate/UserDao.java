/*
DAOs (using JPA’s EntityManager)
*/
@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    // ================= NEW METHODS =================

    // 1. Insert user (via persist, JPA handles it)
    public User insertUser(String name, String email, String gender) {
        User user = new User();
        user.setName(name);
        // assuming User entity has email, gender fields
        user.setEmail(email);
        user.setGender(gender);
        em.persist(user);
        return user;
    }

    // 2. Get all users
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                 .getResultList();
    }

    // 3. Find by email
    public User findByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    // 4. Find by gender + name
    public List<User> findByGenderAndName(String gender, String firstName, String lastName) {
        return em.createQuery(
                 "SELECT u FROM User u WHERE u.gender = :gender AND u.firstName = :firstName AND u.lastName = :lastName",
                 User.class)
                 .setParameter("gender", gender)
                 .setParameter("firstName", firstName)
                 .setParameter("lastName", lastName)
                 .getResultList();
    }

    // 5. Count customers per gender
    public List<Object[]> countUsersByGender() {
        return em.createQuery("SELECT u.gender, COUNT(u) FROM User u GROUP BY u.gender ORDER BY COUNT(u) DESC")
                 .getResultList();
    }
}
