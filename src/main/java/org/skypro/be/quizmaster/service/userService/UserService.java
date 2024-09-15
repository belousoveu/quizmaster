package org.skypro.be.quizmaster.service.userService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
            admin.setRoles(Collections.singleton("ROLE_ADMIN"));
            userRepository.save(admin);
            logger.info("Admin user created: {}", admin);
        } else {
            logger.info("Admin user already exists");
        }
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("ROLE_USER"));
        logger.info("User created: {}", user);
        userRepository.save(user);
    }

    public void changeUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRoles(Collections.singleton("ROLE_" + role));
        logger.info("User role changed: {}", user);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void authenticate(String username, String password, HttpServletRequest request) {
        try {
            request.login(username, password);
            logger.info("User authenticated: {}", username);
        } catch (ServletException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
