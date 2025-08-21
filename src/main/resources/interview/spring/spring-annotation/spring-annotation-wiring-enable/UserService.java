package com.example.service;

import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    @Qualifier("specialRepository") // Chooses between multiple implementations
    private UserRepository userRepository;

    public String fetchUser() {
        return userRepository.getUserName();
    }
}
