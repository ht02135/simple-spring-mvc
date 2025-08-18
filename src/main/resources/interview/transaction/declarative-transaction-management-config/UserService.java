
/*
Annotated UserService.java
*/
package x.y.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.dao.UserDao;
import x.y.model.User;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Transactional // default: rollback on RuntimeException
    public Long registerUser(User user) {
        return userDao.insertUser(user);
    }

    @Transactional(readOnly = true) // read-only transaction
    public User getUser(Long id) {
        return userDao.findUserById(id);
    }
}