package org.skypro.be.quizmaster.repository;

import org.junit.jupiter.api.Test;
import org.skypro.be.quizmaster.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class ResultsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResultsRepository repository;

    @Test
    void findAllByUserId_testWhenUserIdExists() {
        Result record1 = new Result(1L, "Test1", LocalDateTime.now(), 10, 8);
        Result record2 = new Result(1L, "Test2", LocalDateTime.now(), 10, 9);
        Result record3 = new Result(2L, "Test3", LocalDateTime.now(), 5, 5);

        entityManager.persist(record1);
        entityManager.persist(record2);
        entityManager.persist(record3);
        entityManager.flush();

        List<Result> actual = repository.findAllByUserId(1L);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(17, actual.stream().mapToInt(Result::getCorrectAnswers).sum());

    }

    @Test
    void findAllByUserId_testWhenUserIdNotExists() {
        Result record1 = new Result(1L, "Test1", LocalDateTime.now(), 10, 8);
        Result record2 = new Result(1L, "Test2", LocalDateTime.now(), 10, 9);
        Result record3 = new Result(2L, "Test3", LocalDateTime.now(), 5, 5);

        entityManager.persist(record1);
        entityManager.persist(record2);
        entityManager.persist(record3);
        entityManager.flush();

        List<Result> actual = repository.findAllByUserId(3L);

        assertNotNull(actual);
        assertEquals(0, actual.size());
        assertEquals(0, actual.stream().mapToInt(Result::getCorrectAnswers).sum());

    }


}