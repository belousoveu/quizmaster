package org.skypro.be.quizmaster.service.questionService;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.stereotype.Service;

@Service
@QuestionServiceSection(Section.SPRING)
public class SpringQuestionService extends ManualQuestionService {
    public SpringQuestionService() {
        super(Section.SPRING);
    }

}
