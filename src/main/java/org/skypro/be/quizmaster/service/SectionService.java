package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;

import java.util.Collection;
import java.util.List;

public interface SectionService {


    String getDescription(String section);

    List<String> errors(Question question);

    List<Section> getSections();

    QuestionService getService(Section section);
}
