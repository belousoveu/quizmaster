package org.skypro.be.quizmaster.repository;

import org.skypro.be.quizmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}