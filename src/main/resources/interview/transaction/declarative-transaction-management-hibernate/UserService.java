package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.dao.UserDao;
import x.y.model.User;

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
        // Example: generate invoice for user
        invoiceService.createPdf(userId);
        // could send email, etc.
    }
}