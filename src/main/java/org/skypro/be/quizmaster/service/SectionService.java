package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;

import java.util.Collection;
import java.util.List;

public interface SectionService {

    Question createQuestion(Section section);

    List<Question> getQuestions(Section section);

    String getDescription(String section);

    void addQuestion(Question question);

    List<String> errors(Question question);

    List<Section> getSections();
}
