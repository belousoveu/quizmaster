package org.skypro.be.quizmaster.service.question.dynamic.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.utils.RandomUtils;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MathMultipleChoiceStrategyTest {

    @Mock
    RandomMathQuestionGenerator generator;

    @InjectMocks
    MathMultipleChoiceStrategy out;

    RandomUtils randomUtils = new RandomUtils();

    @BeforeEach
    void setUp() {
        randomUtils.setRandom(new Random());
    }

    @Test
    void createQuestion_test() {
        when(generator.generate()).thenReturn(generator);
        when(generator.getDescription()).thenReturn("description");
        when(generator.toString()).thenReturn("question");
        when(generator.getResult()).thenReturn(1);

        String textQuestion = "Отметьте варианты в которых присутствует правильный ответ выражения:<br>description (question?)";

//        try (MockedStatic<RandomUtils> randomUtils = mockStatic(RandomUtils.class)) {
//            randomUtils.when(() -> RandomUtils.getRandomIntWithinRange(1, 1)).thenReturn(0);
//        }

        Question actual = out.createQuestion();
        int countAnswers = actual.getAnswers().size();

        assertNotNull(actual);
        assertEquals(textQuestion, actual.getTextQuestion());
        assertEquals(Section.MATH, actual.getSection());
        assertEquals(QuestionType.MULTIPLE_CHOICE, actual.getQuestionType());
        assertTrue(countAnswers >= 3 && countAnswers <= 5);
        assertTrue(actual.getAnswers().stream().anyMatch(Answer::isCorrect));
        assertTrue(actual.getAnswers().stream().filter(Answer::isCorrect).allMatch(ans -> ans.getTextAnswer().contains("1")));
    }

    @Test
    void getSection_test() {
        assertEquals(out.getSection(), Section.MATH);
    }

    @Test
    void getType_test() {
        assertEquals(out.getType(), QuestionType.MULTIPLE_CHOICE);
    }
}