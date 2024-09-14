package org.skypro.be.quizmaster.service.questionService;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@QuestionServiceSection(Section.MATH)
public class MathQuestionService extends DynamicQuestionService {

    public MathQuestionService() {
        super(Section.MATH);
    }

    @Override
    public Question getRandomQuestion(QuestionType randomType) {
        Question question = new Question(Section.MATH);
        return switch (randomType) {
            case OPEN_QUESTION -> CreateOpenAnswer(question);
            case MULTIPLE_CHOICE -> CreateMultipleChoice(question);
            case SINGLE_CHOICE -> CreateSingleChoice(question);
        };
    }

    private Question CreateOpenAnswer(Question question) {
        RandomMathTest randomMathTest = new RandomMathTest();
        question.setTextQuestion("Напишите верный результат выражения:\n"
                + randomMathTest.description + "\n" + randomMathTest + " ?");
        Answer answer = new Answer();
        answer.setTextAnswer(String.valueOf(randomMathTest.result));
        answer.setIsCorrect(true);
        question.setAnswers(List.of(answer));

        return question;
    }

    private Question CreateSingleChoice(Question question) {
        RandomMathTest randomMathTest = new RandomMathTest();
        question.setTextQuestion("Выберете вариант с правильным ответом: \n"
                + randomMathTest.description + "\n" + randomMathTest + " ?");
        Answer correctAnswer = new Answer();
        correctAnswer.setTextAnswer(String.valueOf(randomMathTest.result));
        correctAnswer.setIsCorrect(true);
        List<Answer> answers = new ArrayList<>();
        answers.add(correctAnswer);
        // Добавляем от 2 до 4 неправильных ответов
        int numberOfIncorrectAnswers = getRandomIntWithinRange(3, 1);
        int range = 5; // разброс вариантов неправильных ответов относительно правильного
        while (answers.size() <= numberOfIncorrectAnswers) {
            int incorrectResult = getRandomIntWithinRange(randomMathTest.result, range);
            if (incorrectResult != randomMathTest.result) {
                Answer incorrectAnswer = new Answer();
                incorrectAnswer.setTextAnswer(String.valueOf(incorrectResult));
                incorrectAnswer.setIsCorrect(false);
                answers.add(incorrectAnswer);
            }
        }
        Collections.shuffle(answers);
        question.setAnswers(answers);
        return question;
    }

    private Question CreateMultipleChoice(Question question) {
        RandomMathTest randomMathTest = new RandomMathTest();
        question.setTextQuestion("Отметьте варианты в которых присутствует правильный ответ выражения: \n"
                + randomMathTest.description + "\n" + randomMathTest + " ?");
        int numberOfOptions = 3; // количество значений в каждом варианте ответа
        int range = 5; // разброс вариантов неправильных ответов относительно правильного

        List<Answer> answers = new ArrayList<>();

        Answer correctAnswer = new Answer();
        Set<Integer> answerSet = new HashSet<>();
        answerSet.add(randomMathTest.result);
        while (answerSet.size() <= numberOfOptions) {
            answerSet.add(getRandomIntWithinRange(randomMathTest.result, range));
        }
        String valuesString = answerSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        correctAnswer.setTextAnswer(valuesString);
        correctAnswer.setIsCorrect(true);
        answers.add(correctAnswer);

        int numberOfIncorrectAnswers = getRandomIntWithinRange(3, 1);

        while (answers.size() <= numberOfIncorrectAnswers) {
            Set<Integer> incorrectAnswerSet = new HashSet<>();
            while (incorrectAnswerSet.size() <= numberOfOptions) {
                incorrectAnswerSet.add(getRandomIntWithinRange(randomMathTest.result, range));
            }
            Answer incorrectAnswer = new Answer();
            incorrectAnswer.setTextAnswer(incorrectAnswerSet.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "))
            );
            incorrectAnswer.setIsCorrect(incorrectAnswerSet.contains(randomMathTest.result));
            answers.add(incorrectAnswer);
        }

        Collections.shuffle(answers);
        question.setAnswers(answers);

        return question;
    }

    private int getRandomIntWithinRange(int target, int deviation) {
        Random random = new Random();
        return random.nextInt(((target + deviation) - (target - deviation) + 1)) + (target - deviation);
    }

    private Set<Integer> getRandomIntSetWithinRange(int target, int deviation, int size) {
        Set<Integer> resultSet = new HashSet<>();
        resultSet.add(target);
        while (resultSet.size() <= size) {
            resultSet.add(getRandomIntWithinRange(target, deviation));
        }
        return resultSet;
    }


    private static class RandomMathTest {
        int value1;
        int value2;
        Operator operator;
        int result;
        String description;

        private RandomMathTest() {
            Random random = new Random();
            operator = Operator.values()[random.nextInt(Operator.values().length)];
            value1 = random.nextInt(20);
            value2 = random.nextInt(20);
            // Для операций деления и остатка исключаем значение 0, для второго операнда
            // во избежание сложностей с написанием правильных ответов
            if (operator == Operator.MOD || operator == Operator.DIV) {
                while (value2 == 0) {
                    value2 = random.nextInt(20);
                }
            }
            result = operator.calculate(value1, value2);
            description = operator.getDescription(value1, value2);
        }

        public String toString() {
            return value1 + " " + operator.getSymbol() + " " + value2 + " = ";
        }
    }

    private enum Operator {
        ADD("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        MOD("%");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        private String getSymbol() {
            return symbol;
        }

        private int calculate(int value1, int value2) {
            return switch (this) {
                case ADD -> value1 + value2;
                case SUB -> value1 - value2;
                case MUL -> value1 * value2;
                case DIV -> value1 / value2;
                case MOD -> value1 % value2;
            };
        }

        private String getDescription(int value1, int value2) {
            return switch (this) {
                case ADD -> String.format("Сумма чисел %d и %d", value1, value2);
                case SUB -> String.format("Разность чисел %d и %d", value1, value2);
                case MUL -> String.format("Произведение чисел %d и %d", value1, value2);
                case DIV -> String.format("Целочисленный результат от деления числа %d на %d", value1, value2);
                case MOD -> String.format("Остаток от деления числа %d на %d", value1, value2);
            };
        }
    }
}

