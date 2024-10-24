package org.skypro.be.quizmaster.service.question.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.be.quizmaster.exception.GetListDynamicQuestionsException;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.service.question.dynamic.math.MathMultipleChoiceStrategy;
import org.skypro.be.quizmaster.service.question.dynamic.math.MathOpenQuestionStrategy;
import org.skypro.be.quizmaster.service.question.dynamic.math.MathSingleChoiceStrategy;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skypro.be.quizmaster.model.QuestionType.*;
import static org.skypro.be.quizmaster.model.Section.MATH;
import static org.skypro.be.quizmaster.service.TestData.MATH_QUESTION;
import static org.skypro.be.quizmaster.service.TestData.getMockQuestion;

class MathQuestionServiceTest {

    private final MathOpenQuestionStrategy openQuestionStrategy = mock(MathOpenQuestionStrategy.class);
    private final MathSingleChoiceStrategy singleChoiceStrategy = mock(MathSingleChoiceStrategy.class);
    private final MathMultipleChoiceStrategy multipleChoiceStrategy = mock(MathMultipleChoiceStrategy.class);

    private final List<QuestionCreationStrategy> strategies = List.of(
            openQuestionStrategy,
            singleChoiceStrategy,
            multipleChoiceStrategy
    );

    private final MathQuestionService out = new MathQuestionService();

    @BeforeEach
    void setUp() {
        out.setStrategies(strategies);

    }

    @Test
    void getRandomQuestion_test() {
        when(openQuestionStrategy.getSection()).thenReturn(MATH);
        when(singleChoiceStrategy.getSection()).thenReturn(MATH);
        when(multipleChoiceStrategy.getSection()).thenReturn(MATH);
        when(openQuestionStrategy.getType()).thenReturn(OPEN_QUESTION);
        when(singleChoiceStrategy.getType()).thenReturn(SINGLE_CHOICE);
        when(multipleChoiceStrategy.getType()).thenReturn(MULTIPLE_CHOICE);
        when(openQuestionStrategy.createQuestion()).thenReturn(getMockQuestion(MATH, OPEN_QUESTION));
        when(singleChoiceStrategy.createQuestion()).thenReturn(getMockQuestion(MATH, SINGLE_CHOICE));
        when(multipleChoiceStrategy.createQuestion()).thenReturn(getMockQuestion(MATH, MULTIPLE_CHOICE));

        Question actual1 = out.getRandomQuestion(OPEN_QUESTION);
        assertNotNull(actual1);
        assertEquals(OPEN_QUESTION, actual1.getQuestionType());
        assertEquals(MATH, actual1.getSection());

        Question actual2 = out.getRandomQuestion(SINGLE_CHOICE);
        assertNotNull(actual2);
        assertEquals(SINGLE_CHOICE, actual2.getQuestionType());
        assertEquals(MATH, actual2.getSection());

        Question actual3 = out.getRandomQuestion(MULTIPLE_CHOICE);
        assertNotNull(actual3);
        assertEquals(MULTIPLE_CHOICE, actual3.getQuestionType());
        assertEquals(MATH, actual3.getSection());

    }

    @Test
    void getQuestionTypesStatistics_test() {
        Map<String, String> expected = Map.of(
                OPEN_QUESTION.getName(), "&infin;",
                SINGLE_CHOICE.getName(), "&infin;",
                MULTIPLE_CHOICE.getName(), "&infin;"
        );

        Map<String, String> actual = out.getQuestionTypesStatistics();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getQuestions_test() {
        assertThrows(GetListDynamicQuestionsException.class, out::getQuestions);
    }

    @Test
    void createQuestion_test() {
        assertThrows(UnsupportedOperationException.class, out::createQuestion);
    }

    @Test
    void saveQuestion_test() {
        assertThrows(UnsupportedOperationException.class, () -> out.saveQuestion(MATH_QUESTION));
    }

    @Test
    void getQuestion_test() {
        assertThrows(UnsupportedOperationException.class, () -> out.getQuestion(1L));
    }

    @Test
    void deleteQuestion_test() {
        assertThrows(UnsupportedOperationException.class, () -> out.deleteQuestion(1L));
    }
}