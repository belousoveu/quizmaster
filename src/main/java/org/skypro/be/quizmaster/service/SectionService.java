package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.dto.QuestionDto;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.questionService.QuestionService;

import java.util.List;
import java.util.Map;

public interface SectionService {


//    String getDescription(String section);

    List<String> errors(QuestionDto question);

    List<Section> getSections();

    QuestionService getService(Section section);

}
