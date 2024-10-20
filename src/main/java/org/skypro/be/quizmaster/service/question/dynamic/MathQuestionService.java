package org.skypro.be.quizmaster.service.question.dynamic;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.DynamicQuestionService;
import org.skypro.be.quizmaster.service.question.dynamic.math.MathMultipleChoiceStrategy;
import org.skypro.be.quizmaster.service.question.dynamic.math.MathOpenQuestionStrategy;
import org.skypro.be.quizmaster.service.question.dynamic.math.MathSingleChoiceStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@QuestionServiceSection(Section.MATH)
public class MathQuestionService extends DynamicQuestionService {

    private final Map<QuestionType, QuestionCreationStrategy> strategies;

    public MathQuestionService() {

        super(Section.MATH);
        strategies = Map.of(
                QuestionType.OPEN_QUESTION, new MathOpenQuestionStrategy(),
                QuestionType.MULTIPLE_CHOICE, new MathMultipleChoiceStrategy(),
                QuestionType.SINGLE_CHOICE, new MathSingleChoiceStrategy()
        );
    }

    @Override
    public Question getRandomQuestion(QuestionType randomType) {
        return strategies.get(randomType).createQuestion();
    }
}






