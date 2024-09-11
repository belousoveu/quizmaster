package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.stereotype.Service;

@Service
@QuestionServiceSection(Section.JAVA)
public class JavaQuestionService extends ManualQuestionService {
    public JavaQuestionService() {
        super(Section.JAVA);
    }


}
