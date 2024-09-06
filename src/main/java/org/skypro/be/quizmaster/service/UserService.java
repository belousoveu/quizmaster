package org.skypro.be.quizmaster.service;

import jakarta.annotation.PostConstruct;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("root") == null) {
            User admin = new User();
            admin.setUsername("root");
            admin.setPassword(passwordEncoder.encode("root"));
            admin.setRoles(Collections.singleton("ADMIN"));
            userRepository.save(admin);
        }
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("USER"));
        userRepository.save(user);
    }
}
