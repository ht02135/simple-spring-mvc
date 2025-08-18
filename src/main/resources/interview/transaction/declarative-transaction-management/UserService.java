
/*
Here’s a complete example showing how to configure declarative 
transaction management with AOP advice in Spring for a UserService.
We’ll include the Java service class, DAO stub, and the Spring 
XML configuration.
*/
package x.y.service;

import x.y.dao.UserDao;
import x.y.model.User;

public class UserService {

    private UserDao userDao;

    // Spring will inject this dependency
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Transactional method: Inserts a new user.
     */
    public Long registerUser(User user) {
        // delegate to DAO (insert into DB)
        Long id = userDao.insertUser(user);
        return id;
    }

    /**
     * Read-only transactional method (matched by "get*")
     */
    public User getUser(Long id) {
        return userDao.findUserById(id);
    }
}