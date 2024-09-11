package org.skypro.be.quizmaster.service.questionService;

import org.skypro.be.quizmaster.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestions();

    Question createQuestion();

    void saveQuestion(Question question);

    Question getQuestion(Long id);
}
