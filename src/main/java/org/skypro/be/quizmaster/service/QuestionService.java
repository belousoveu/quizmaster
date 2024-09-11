package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestions();

    Question createQuestion();

    void addQuestion(Question question);
}
