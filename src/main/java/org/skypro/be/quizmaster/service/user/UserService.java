package org.skypro.be.quizmaster.service.user;

import jakarta.servlet.http.HttpServletRequest;
import org.skypro.be.quizmaster.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void registerUser(User user);

    void changeUserRole(Long id, String role);

    void authenticate(String username, String password, HttpServletRequest request);

    User getUser(Long userId);

    List<User> getUsers();
}
