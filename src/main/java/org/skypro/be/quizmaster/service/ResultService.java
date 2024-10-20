package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Result;

import java.util.List;

public interface ResultService {
    List<Result> getUserResults(Long userId);

    void saveResult(Long userId, List<ExamQuestion> examResults, String description);

}
