
/*
DAOs (no transactions here, just called by services)
*/
package x.y.dao;

import org.springframework.stereotype.Repository;
import x.y.model.User;

@Repository
public class UserDao {
    public Long insertUser(User user) {
        System.out.println("Inserting user into DB: " + user.getName());
        return 1L;
    }
    public User findUserById(Long id) {
        System.out.println("Fetching user with id=" + id);
        return new User(id, "Test User");
    }
}