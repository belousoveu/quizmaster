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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceImpTest {

    @Mock
    private ResultsRepository repository;

    @InjectMocks
    private ResultServiceImp out;

    private final List<Result> results = new ArrayList<>();

    private final List<ExamQuestion> examResults = new ArrayList<>();

    @BeforeEach
    void setUp() {
        results.add(new Result(1L, "Test1", LocalDateTime.now(), 10, 8));
        results.add(new Result(1L, "Test2", LocalDateTime.now(), 10, 7));
        results.add(new Result(1L, "Test3", LocalDateTime.now(), 10, 8));
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


    @Test
    void saveResult() {
        examResults.add(new ExamQuestion(new Question()));
    }
}