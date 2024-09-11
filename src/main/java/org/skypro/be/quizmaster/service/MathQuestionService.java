package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.stereotype.Service;

@Service
@QuestionServiceSection(Section.MATH)
public class MathQuestionService extends DynamicQuestionService {
}
