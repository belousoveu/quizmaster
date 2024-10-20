package org.skypro.be.quizmaster.service.question.dynamic.math;

import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.dynamic.QuestionCreationStrategy;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MathMultipleChoiceStrategy implements QuestionCreationStrategy {
    private final int numberOfOptions = 3; // количество значений в каждом варианте ответа
    private final int range = 5; // разброс вариантов неправильных ответов относительно правильного
    private final int numberOfAdditionalAnswers = RandomUtils.getRandomIntWithinRange(3, 1); // Добавляем от 2 до 4 неправильных ответов

    @Override
    public Question createQuestion() {

        RandomMathQuestionGenerator randomMathQuestion = new RandomMathQuestionGenerator();
        Question question = new Question(Section.MATH);
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);

        question.setTextQuestion("Отметьте варианты в которых присутствует правильный ответ выражения:<br>"
                + randomMathQuestion.description + " (" + randomMathQuestion + "?)");

        Set<Answer> answers = new HashSet<>();
        answers.add(getCorrectAnswerSet(randomMathQuestion.result));

        while (answers.size() <= numberOfAdditionalAnswers) {
            answers.add(generateRandomAnswer(new HashSet<>(), randomMathQuestion.result));
        }

        List<Answer> answersList = new ArrayList<>(answers);
        Collections.shuffle(answersList);
        question.setAnswers(answersList);

        return question;
    }

    private Answer getCorrectAnswerSet(int correctAnswer) {
        Set<Integer> answerSet = new HashSet<>();
        answerSet.add(correctAnswer);
        return generateRandomAnswer(answerSet, correctAnswer);
    }

    private Answer generateRandomAnswer(Set<Integer> answerSet, int correctAnswer) {
        while (answerSet.size() <= numberOfOptions) {
            answerSet.add(RandomUtils.getRandomIntWithinRange(correctAnswer, range));
        }
        String valuesString = answerSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return new Answer(valuesString, answerSet.contains(correctAnswer));
    }
}
