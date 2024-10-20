package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.QuestionService;

public interface QuestionServiceFactory {

    QuestionService getService(Section section);

}
