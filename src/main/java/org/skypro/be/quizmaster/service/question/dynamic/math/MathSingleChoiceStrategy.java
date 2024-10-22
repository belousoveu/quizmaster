package org.skypro.be.quizmaster.service.question.dynamic.math;

import lombok.Getter;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.dynamic.QuestionCreationStrategy;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
public class MathSingleChoiceStrategy implements QuestionCreationStrategy {

    private final RandomMathQuestionGenerator questionGenerator;
    private final Section section = Section.MATH;
    private final QuestionType type = QuestionType.SINGLE_CHOICE;

    public MathSingleChoiceStrategy(RandomMathQuestionGenerator questionGenerator) {
        this.questionGenerator = questionGenerator;
    }

    @Override
    public Question createQuestion() {
        int range = 5; // разброс вариантов неправильных ответов относительно правильного
        int numberOfIncorrectAnswers = RandomUtils.getRandomIntWithinRange(3, 1); // добавляем от 2 до 4 вариантов ответов

        RandomMathQuestionGenerator randomMathQuestion = questionGenerator.generate();
        Question question = new Question(Section.MATH);
        question.setQuestionType(QuestionType.SINGLE_CHOICE);

        question.setTextQuestion("Выберете вариант с правильным ответом:<br>"
                + randomMathQuestion.getDescription() + " (" + randomMathQuestion + "?)");

        Set<Answer> answers = new HashSet<>();
        answers.add(new Answer(String.valueOf(randomMathQuestion.getResult()), true));

        while (answers.size() <= numberOfIncorrectAnswers) {
            int incorrectResult = RandomUtils.getRandomIntWithinRange(randomMathQuestion.getResult(), range);
            if (incorrectResult != randomMathQuestion.getResult()) {
                answers.add(new Answer(String.valueOf(incorrectResult), false));
            }
        }
        List<Answer> answersList = new ArrayList<>(answers);
        Collections.shuffle(answersList);

        question.setAnswers(answersList);
        return question;
    }
}
