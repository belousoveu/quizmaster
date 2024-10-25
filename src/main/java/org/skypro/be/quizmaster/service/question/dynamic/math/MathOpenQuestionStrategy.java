package org.skypro.be.quizmaster.service.question.dynamic.math;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.dynamic.QuestionCreationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Getter
public class MathOpenQuestionStrategy implements QuestionCreationStrategy {

    private final RandomMathQuestionGenerator questionGenerator;
    private final Section section = Section.MATH;
    private final QuestionType type = QuestionType.OPEN_QUESTION;

    public MathOpenQuestionStrategy(RandomMathQuestionGenerator questionGenerator) {
        this.questionGenerator = questionGenerator;
    }

    @Override
    public Question createQuestion() {
        RandomMathQuestionGenerator randomMathQuestion = questionGenerator.generate();
        Question question = new Question(Section.MATH);
        question.setQuestionType(QuestionType.OPEN_QUESTION);

        question.setTextQuestion("Напишите верный результат выражения:<br>"
                + randomMathQuestion.getDescription() + " (" + randomMathQuestion + "?)");
        Answer answer = new Answer();
        answer.setTextAnswer(String.valueOf(randomMathQuestion.getResult()));
        answer.setIsCorrect(true);
        question.setAnswers(List.of(answer));

        return question;
    }
}
