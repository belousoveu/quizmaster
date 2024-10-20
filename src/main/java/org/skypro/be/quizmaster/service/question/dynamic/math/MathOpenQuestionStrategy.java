package org.skypro.be.quizmaster.service.question.dynamic.math;

import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.dynamic.QuestionCreationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MathOpenQuestionStrategy implements QuestionCreationStrategy {

    @Override
    public Question createQuestion() {
        RandomMathQuestionGenerator randomMathQuestion = new RandomMathQuestionGenerator();
        Question question = new Question(Section.MATH);
        question.setQuestionType(QuestionType.OPEN_QUESTION);

        question.setTextQuestion("Напишите верный результат выражения:<br>"
                + randomMathQuestion.description + " (" + randomMathQuestion + "?)");
        Answer answer = new Answer();
        answer.setTextAnswer(String.valueOf(randomMathQuestion.result));
        answer.setIsCorrect(true);
        question.setAnswers(List.of(answer));

        return question;
    }
}
