package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Result;

import java.util.List;
import java.util.Map;

public interface ResultService {
    List<Result> getUserResults(Long userId);

    void saveResult(Long userId, List<ExamQuestion> examResults, String description);

    List<ExamQuestion> getResult(List<Question> questions, Map<String, List<String>> answers);

}
