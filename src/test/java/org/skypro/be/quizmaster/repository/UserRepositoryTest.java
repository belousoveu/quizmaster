package org.skypro.be.quizmaster.repository;

import org.junit.jupiter.api.Test;
import org.skypro.be.quizmaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    void findByUsername_testWhenUsernameExists() {

        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("password1");
        user1.setRoles(Collections.singleton("ROLE_USER"));

        User admin = new User();
        admin.setUsername("root");
        admin.setPassword("root");
        admin.setRoles(Collections.singleton("ROLE_ADMIN"));

        entityManager.persist(user1);
        entityManager.persist(admin);
        entityManager.flush();

        User actual = repository.findByUsername("root");

        assertNotNull(actual);
        assertEquals(admin, actual);

    }

    @Test
    void findByUsername_testWhenUsernameDoesNotExist() {
        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("password1");
        user1.setRoles(Collections.singleton("ROLE_USER"));

        User admin = new User();
        admin.setUsername("root");
        admin.setPassword("root");
        admin.setRoles(Collections.singleton("ROLE_ADMIN"));

        entityManager.persist(user1);
        entityManager.persist(admin);
        entityManager.flush();

        User actual = repository.findByUsername("user3");

        assertNull(actual);

    }
}