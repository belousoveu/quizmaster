package org.skypro.be.quizmaster.service.question.manual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.exception.QuestionNotFoundException;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.skypro.be.quizmaster.model.QuestionType.*;
import static org.skypro.be.quizmaster.model.Section.JAVA;
import static org.skypro.be.quizmaster.service.TestData.getMockQuestion;


@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    @Mock
    private QuestionRepository repository;

    @InjectMocks
    private JavaQuestionService out;

    List<Question> questions;


    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();
    }

    @Test
    void createQuestion_test() {
        Question expected = new Question(JAVA);
        Question actual = out.createQuestion();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void saveQuestion_test() {

        Question expected = getMockQuestion(JAVA, OPEN_QUESTION);
        when(repository.save(expected)).thenAnswer(i -> {
            Question savedQuestion = i.getArgument(0);
            savedQuestion.setId(1L);
            questions.add(savedQuestion);
            return savedQuestion;
        });

        out.saveQuestion(expected);

        assertFalse(questions.isEmpty());
        assertTrue(questions.contains(expected));
        assertEquals(expected.getId(), 1L);
    }

    @Test
    void getQuestions_test() {
        questions.add(getMockQuestion(JAVA, OPEN_QUESTION));
        questions.add(getMockQuestion(JAVA, SINGLE_CHOICE));
        questions.add(getMockQuestion(JAVA, MULTIPLE_CHOICE));
        when(repository.findAll()).thenReturn(questions);

        List<Question> actual = out.getQuestions();

        assertNotNull(actual);
        assertIterableEquals(questions, actual);
    }

    @Test
    void getQuestion_testWhenQuestionExists() {
        Question expected = getMockQuestion(JAVA, OPEN_QUESTION);
        expected.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(expected));

        Question actual = out.getQuestion(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getQuestion_testWhenQuestionNotExists() {
        when(repository.findById(1L)).thenThrow(QuestionNotFoundException.class);

        assertThrows(QuestionNotFoundException.class, () -> out.getQuestion(1L));
    }


    @Test
    void deleteQuestion_test() {
        long id = 1L;
        out.deleteQuestion(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void getRandomQuestion_test() {
        Question expected = getMockQuestion(JAVA, OPEN_QUESTION);
        expected.setId(1L);
        when(repository.findIdsBySectionAndType(JAVA, OPEN_QUESTION)).thenReturn(List.of(1L, 2L, 5L));
        when(repository.findById(anyLong())).thenReturn(Optional.of(expected));

        Question actual = out.getRandomQuestion(OPEN_QUESTION);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), 1L);

    }

    @Test
    void getQuestionTypesStatistics_test() {
        Map<String, String> expected = Map.of(
                OPEN_QUESTION.getName(), "2",
                SINGLE_CHOICE.getName(), "4",
                MULTIPLE_CHOICE.getName(), "5"
        );
        when(repository.countBySectionAndQuestionType(List.of(JAVA), List.of(OPEN_QUESTION))).thenReturn(2L);
        when(repository.countBySectionAndQuestionType(List.of(JAVA), List.of(SINGLE_CHOICE))).thenReturn(4L);
        when(repository.countBySectionAndQuestionType(List.of(JAVA), List.of(MULTIPLE_CHOICE))).thenReturn(5L);

        Map<String, String> actual = out.getQuestionTypesStatistics();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}