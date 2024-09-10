package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestions(Section section);

    void addQuestion(Question question);
}
