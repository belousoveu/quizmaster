package org.skypro.be.quizmaster.service.question.dynamic.math;

import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.dynamic.QuestionCreationStrategy;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MathSingleChoiceStrategy implements QuestionCreationStrategy {

    private final int range = 5; // разброс вариантов неправильных ответов относительно правильного
    private final int numberOfIncorrectAnswers = RandomUtils.getRandomIntWithinRange(3, 1); // Добавляем от 2 до 4 неправильных ответов

    @Override
    public Question createQuestion() {
        RandomMathQuestionGenerator randomMathQuestion = new RandomMathQuestionGenerator();
        Question question = new Question(Section.MATH);
        question.setQuestionType(QuestionType.SINGLE_CHOICE);

        question.setTextQuestion("Выберете вариант с правильным ответом:<br>"
                + randomMathQuestion.description + " (" + randomMathQuestion + "?)");

        Set<Answer> answers = new HashSet<>();
        answers.add(new Answer(String.valueOf(randomMathQuestion.result), true));

        while (answers.size() <= numberOfIncorrectAnswers) {
            int incorrectResult = RandomUtils.getRandomIntWithinRange(randomMathQuestion.result, range);
            if (incorrectResult != randomMathQuestion.result) {
                answers.add(new Answer(String.valueOf(incorrectResult), false));
            }
        }
        List<Answer> answersList = new ArrayList<>(answers);
        Collections.shuffle(answersList);

        question.setAnswers(answersList);
        return question;
    }
}
