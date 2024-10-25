package org.skypro.be.quizmaster.service.question.dynamic.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MathOpenQuestionStrategyTest {

    @Mock
    RandomMathQuestionGenerator generator;

    @InjectMocks
    MathOpenQuestionStrategy out;

    @Test
    void createQuestion_test() {
        when(generator.generate()).thenReturn(generator);
        when(generator.getDescription()).thenReturn("description");
        when(generator.toString()).thenReturn("question");
        when(generator.getResult()).thenReturn(1);

        String textQuestion = "Напишите верный результат выражения:<br>description (question?)";

        Question actual = out.createQuestion();

        assertNotNull(actual);
        assertEquals(actual.getTextQuestion(), textQuestion);
        assertEquals(actual.getSection(), Section.MATH);
        assertEquals(actual.getQuestionType(), QuestionType.OPEN_QUESTION);
        assertEquals(actual.getAnswers().size(), 1);
        assertEquals(actual.getAnswers().stream().filter(Answer::isCorrect).count(), 1);
        assertEquals(actual.getAnswers().stream().filter(Answer::isCorrect).findFirst().get().getTextAnswer(), "1");

    }

    @Test
    void getSection_test() {
        assertEquals(out.getSection(), Section.MATH);
    }

    @Test
    void getType_test() {
        assertEquals(out.getType(), QuestionType.OPEN_QUESTION);
    }
}