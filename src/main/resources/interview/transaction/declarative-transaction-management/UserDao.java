/*
UserDao.java (DAO Stub)
*/
package x.y.dao;

import x.y.model.User;

public class UserDao {

    public Long insertUser(User user) {
        // Simulate SQL insert
        System.out.println("Inserting user into DB: " + user.getName());
        return 1L; // pretend generated ID
    }

    public User findUserById(Long id) {
        // Simulate SQL select
        System.out.println("Fetching user from DB with id=" + id);
        return new User(id, "Test User");
    }
}