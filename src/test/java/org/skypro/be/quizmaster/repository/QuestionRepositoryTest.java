package org.skypro.be.quizmaster.repository;

import org.junit.jupiter.api.Test;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository repository;

    @Test
    void countBySectionAndQuestionType_testWhenExistTargetQuestions() {
        Question question1 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question1);

        Question question2 = TestData.getMockQuestion(Section.JAVA, QuestionType.SINGLE_CHOICE);
        entityManager.persist(question2);

        Question question3 = TestData.getMockQuestion(Section.SPRING, QuestionType.MULTIPLE_CHOICE);
        entityManager.persist(question3);
        entityManager.flush();

        long actual = repository.countBySectionAndQuestionType(List.of(Section.JAVA),
                List.of(QuestionType.OPEN_QUESTION, QuestionType.SINGLE_CHOICE, QuestionType.MULTIPLE_CHOICE));

        assertEquals(2, actual);

    }

    @Test
    void countBySectionAndQuestionType_testWhenNotExistTargetQuestions() {
        Question question1 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question1);

        Question question2 = TestData.getMockQuestion(Section.JAVA, QuestionType.SINGLE_CHOICE);
        entityManager.persist(question2);

        Question question3 = TestData.getMockQuestion(Section.SPRING, QuestionType.MULTIPLE_CHOICE);
        entityManager.persist(question3);
        entityManager.flush();

        long actual = repository.countBySectionAndQuestionType(List.of(Section.SPRING),
                List.of(QuestionType.OPEN_QUESTION, QuestionType.SINGLE_CHOICE));

        assertEquals(0, actual);
    }

    @Test
    void countBySectionAndQuestionType_testWhenEmptyQueryParams() {
        Question question1 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question1);

        Question question2 = TestData.getMockQuestion(Section.JAVA, QuestionType.SINGLE_CHOICE);
        entityManager.persist(question2);

        Question question3 = TestData.getMockQuestion(Section.SPRING, QuestionType.MULTIPLE_CHOICE);
        entityManager.persist(question3);
        entityManager.flush();

        long actual = repository.countBySectionAndQuestionType(List.of(), List.of());

        assertEquals(0, actual);
    }


    @Test
    void findIdsBySectionAndType_testWhenExistTargetQuestions() {

        Question question1 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question1);

        Question question2 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question2);

        Question question3 = TestData.getMockQuestion(Section.SPRING, QuestionType.MULTIPLE_CHOICE);
        entityManager.persist(question3);
        entityManager.flush();

        List<Long> expected = List.of(1L, 2L);
        List<Long> actual = repository.findIdsBySectionAndType(Section.JAVA, QuestionType.OPEN_QUESTION);

        assertEquals(2, actual.size());
        assertIterableEquals(expected, actual);
    }

    @Test
    void findIdsBySectionAndType_testWhenNotExistTargetQuestions() {

        Question question1 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question1);

        Question question2 = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
        entityManager.persist(question2);

        Question question3 = TestData.getMockQuestion(Section.SPRING, QuestionType.MULTIPLE_CHOICE);
        entityManager.persist(question3);
        entityManager.flush();

        List<Long> expected = List.of();
        List<Long> actual = repository.findIdsBySectionAndType(Section.JAVA, QuestionType.SINGLE_CHOICE);

        assertEquals(0, actual.size());
        assertIterableEquals(expected, actual);
    }
}