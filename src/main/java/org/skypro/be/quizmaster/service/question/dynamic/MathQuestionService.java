package org.skypro.be.quizmaster.service.question.dynamic;

import lombok.Getter;
import lombok.Setter;
import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.DynamicQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
@Getter
@QuestionServiceSection(Section.MATH)
public class MathQuestionService extends DynamicQuestionService {

    @Autowired
    private List<QuestionCreationStrategy> strategies;

    public MathQuestionService() {
        super(Section.MATH);
    }

    @Override
    public Question getRandomQuestion(QuestionType randomType) {
        QuestionCreationStrategy strategy = strategies.stream().filter(s -> s.getSection().equals(Section.MATH))
                .filter(s -> s.getType().equals(randomType))
                .findFirst().orElseThrow();
        return strategy.createQuestion();
    }
}






