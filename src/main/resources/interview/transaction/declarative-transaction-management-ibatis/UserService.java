package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.mapper.UserMapper;
import x.y.model.User;

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
        // send email, etc.
    }
}