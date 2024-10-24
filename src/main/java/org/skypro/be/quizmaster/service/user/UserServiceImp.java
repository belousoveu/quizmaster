package org.skypro.be.quizmaster.service.user;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("root") == null) {
            User admin = new User();
            admin.setUsername("root");
            admin.setPassword(passwordEncoder.encode("root"));
            admin.setRoles(Collections.singleton("ROLE_ADMIN"));
            userRepository.save(admin);
            log.info("Admin user created: {}", admin);
        } else {
            log.info("Admin user already exists");
        }
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("ROLE_USER"));
        log.info("User created: {}", user);
        userRepository.save(user);
    }

    @Override
    public void changeUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRoles(Collections.singleton("ROLE_" + role));
        log.info("User role changed: {}", user);
        userRepository.save(user);
    }

    @Override
    public void authenticate(String username, String password, HttpServletRequest request) {
        try {
            request.login(username, password);
            log.info("User authenticated: {}", username);
        } catch (ServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.info("User with id {} not found", userId);
            return new UsernameNotFoundException("User not found");
        });
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
