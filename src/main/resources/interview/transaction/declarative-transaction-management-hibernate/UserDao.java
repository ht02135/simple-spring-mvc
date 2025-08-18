/*
DAOs (using JPA’s EntityManager)
*/
package x.y.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import x.y.model.User;

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
}