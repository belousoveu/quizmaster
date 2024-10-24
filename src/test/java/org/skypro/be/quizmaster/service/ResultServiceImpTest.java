package org.skypro.be.quizmaster.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Result;
import org.skypro.be.quizmaster.repository.ResultsRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.skypro.be.quizmaster.model.QuestionType.*;
import static org.skypro.be.quizmaster.model.Section.*;
import static org.skypro.be.quizmaster.service.TestData.CORRECT_ANSWER;
import static org.skypro.be.quizmaster.service.TestData.INCORRECT_ANSWER;

@ExtendWith(MockitoExtension.class)
class ResultServiceImpTest {

    @Mock
    private ResultsRepository repository;

    @InjectMocks
    private ResultServiceImp out;

    private final List<Result> results = new ArrayList<>();

    private final List<ExamQuestion> examResults = new ArrayList<>();

    private final List<Question> questions = new ArrayList<>();

    private final Map<String, List<String>> answers = new HashMap<>();

    @BeforeEach
    void setUp() {
        results.add(new Result(1L, "Test1", LocalDateTime.now(), 10, 8));
        results.add(new Result(1L, "Test2", LocalDateTime.now(), 10, 7));
        results.add(new Result(1L, "Test3", LocalDateTime.now(), 10, 8));

        questions.add(TestData.getMockQuestion(JAVA, OPEN_QUESTION));
        questions.add(TestData.getMockQuestion(SPRING, SINGLE_CHOICE));
        questions.add(TestData.getMockQuestion(MATH, MULTIPLE_CHOICE));
        answers.put("answer_0", List.of(CORRECT_ANSWER.getTextAnswer()));
        answers.put("answer_1", List.of(INCORRECT_ANSWER.getTextAnswer()));
        answers.put("answer_2", List.of(CORRECT_ANSWER.getTextAnswer(), CORRECT_ANSWER.getTextAnswer()));
    }

    @Test
    void saveResult_test() {

        results.clear();
        examResults.add(TestData.getMockExamQuestion(JAVA, OPEN_QUESTION, true));
        examResults.add(TestData.getMockExamQuestion(SPRING, SINGLE_CHOICE, true));
        examResults.add(TestData.getMockExamQuestion(MATH, MULTIPLE_CHOICE, true));
        examResults.add(TestData.getMockExamQuestion(JAVA, MULTIPLE_CHOICE, false));
        examResults.add(TestData.getMockExamQuestion(SPRING, OPEN_QUESTION, false));

        LocalDateTime testTime = LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC);

        Result expected = new Result(1L, "Test1", testTime, 5, 3);

        when(repository.save(any(Result.class))).thenAnswer(i -> {
            Result result = i.getArgument(0);
            results.add(result);
            return result;
        });

        out.saveResult(1L, examResults, "Test1");
        Result actual = results.get(0);

        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getTotalQuestions(), actual.getTotalQuestions());
        assertEquals(expected.getCorrectAnswers(), actual.getCorrectAnswers());

    }

    @Test
    void getResult_test() {
        List<ExamQuestion> actual = out.getResult(questions, answers);

        assertNotNull(actual);
        assertEquals(questions.size(), actual.size());
        assertEquals(2, actual.stream().filter(ExamQuestion::isCorrect).count());
    }

    @Test
    void getUserResults_whenUserHasResults() {
        when(repository.findAllByUserId(1L)).thenReturn(results);
        List<Result> userResults = out.getUserResults(1L);
        assertNotNull(userResults);
        assertEquals(results.size(), userResults.size());
        assertIterableEquals(results, userResults);
    }

    @Test
    void getUserResults_whenUserHasNotResults() {
        when(repository.findAllByUserId(2L)).thenReturn(new ArrayList<>());
        List<Result> userResults = out.getUserResults(2L);
        assertNotNull(userResults);
        assertTrue(userResults.isEmpty());
    }
}