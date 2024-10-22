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
import java.util.stream.Collectors;

@Component
@Getter
public class MathMultipleChoiceStrategy implements QuestionCreationStrategy {
    private final RandomMathQuestionGenerator questionGenerator;
    private final Section section = Section.MATH;
    private final QuestionType type = QuestionType.MULTIPLE_CHOICE;

    public MathMultipleChoiceStrategy(RandomMathQuestionGenerator questionGenerator) {
        this.questionGenerator = questionGenerator;
    }


    @Override
    public Question createQuestion() {
        int numberOfAdditionalAnswers = RandomUtils.getRandomIntWithinRange(3, 1); // Добавляем от 2 до 4 неправильных ответов
        RandomMathQuestionGenerator randomMathQuestion = questionGenerator.generate();
        Question question = new Question(Section.MATH);


        question.setTextQuestion("Отметьте варианты в которых присутствует правильный ответ выражения:<br>"
                + randomMathQuestion.getDescription() + " (" + randomMathQuestion + "?)");

        Set<Answer> answers = new HashSet<>();
        answers.add(getCorrectAnswerSet(randomMathQuestion.getResult()));

        while (answers.size() <= numberOfAdditionalAnswers) {
            answers.add(generateRandomAnswer(new HashSet<>(), randomMathQuestion.getResult()));
        }

        List<Answer> answersList = new ArrayList<>(answers);
        Collections.shuffle(answersList);
        question.setAnswers(answersList);
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);

        return question;
    }

    private Answer getCorrectAnswerSet(int correctAnswer) {
        Set<Integer> answerSet = new HashSet<>();
        answerSet.add(correctAnswer);
        return generateRandomAnswer(answerSet, correctAnswer);
    }

    private Answer generateRandomAnswer(Set<Integer> answerSet, int correctAnswer) {
        int numberOfOptions = 4; // количество значений в каждом варианте ответа
        int range = 5; // разброс вариантов неправильных ответов относительно правильного
        while (answerSet.size() < numberOfOptions) {
            answerSet.add(RandomUtils.getRandomIntWithinRange(correctAnswer, range));
        }
        String valuesString = answerSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return new Answer(valuesString, answerSet.contains(correctAnswer));
    }
}
