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
class MathSingleChoiceStrategyTest {

    @Mock
    private RandomMathQuestionGenerator generator;

    @InjectMocks
    private MathSingleChoiceStrategy out;

    private final RandomUtils randomUtils = new RandomUtils();

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

        String textQuestion = "Выберете вариант с правильным ответом:<br>description (question?)";

        Question actual = out.createQuestion();
        int countAnswers = actual.getAnswers().size();

        assertNotNull(actual);
        assertEquals(textQuestion, actual.getTextQuestion());
        assertEquals(Section.MATH, actual.getSection());
        assertEquals(QuestionType.SINGLE_CHOICE, actual.getQuestionType());
        assertTrue(countAnswers >= 3 && countAnswers <= 5);
        assertEquals(1, actual.getAnswers().stream().filter(Answer::isCorrect).count());
        assertEquals("1", actual.getAnswers().stream().filter(Answer::isCorrect).findFirst().get().getTextAnswer());

    }

    @Test
    void getSection_test() {
        assertEquals(out.getSection(), Section.MATH);
    }

    @Test
    void getType_test() {
        assertEquals(out.getType(), QuestionType.SINGLE_CHOICE);
    }
}