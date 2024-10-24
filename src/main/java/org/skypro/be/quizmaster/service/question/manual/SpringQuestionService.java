package org.skypro.be.quizmaster.service.question.manual;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.ManualQuestionService;
import org.springframework.stereotype.Service;

@Service
@QuestionServiceSection(Section.SPRING)
public class SpringQuestionService extends ManualQuestionService {
    public SpringQuestionService() {
        super(Section.SPRING);
    }

}
