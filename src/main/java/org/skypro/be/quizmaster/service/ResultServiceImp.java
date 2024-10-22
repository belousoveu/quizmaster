package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Result;
import org.skypro.be.quizmaster.repository.ResultsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResultServiceImp implements ResultService {

    private final ResultsRepository resultsRepository;

    public ResultServiceImp(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    @Override
    public List<Result> getUserResults(Long userId) {
        return resultsRepository.findAllByUserId(userId);
    }

    @Override
    public void saveResult(Long userId, List<ExamQuestion> examResults, String description) {
        Result result = new Result(userId,
                description,
                LocalDateTime.now(),
                examResults.size(),
                (int) examResults.stream().filter(ExamQuestion::getCorrect).count());
        resultsRepository.save(result);
    }
}
