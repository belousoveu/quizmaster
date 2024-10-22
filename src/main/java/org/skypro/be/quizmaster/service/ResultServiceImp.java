package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Result;
import org.skypro.be.quizmaster.repository.ResultsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImp implements ResultService {

    private final ResultsRepository resultsRepository;

    public ResultServiceImp(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    @Override
    public List<ExamQuestion> getResult(List<Question> questions, Map<String, List<String>> answers) {
        List<ExamQuestion> examQuestions = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            ExamQuestion examQuestion = new ExamQuestion(questions.get(i));
            examQuestion.updateUserAnswers(answers.get("answer_" + i));
            examQuestion.validateAnswer();
            examQuestions.add(examQuestion);
        }
        return examQuestions;
    }

    @Override
    public List<Result> getUserResults(Long userId) {
        return resultsRepository.findAllByUserId(userId);
    }

    @Override
    public void saveResult(Long userId, List<ExamQuestion> examResults, String description) {
        LocalDateTime now = LocalDateTime.now();
        Result result = new Result(userId,
                description,
                now,
                examResults.size(),
                (int) examResults.stream().filter(ExamQuestion::getCorrect).count());
        resultsRepository.save(result);
    }
}
