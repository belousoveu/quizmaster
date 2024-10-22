package org.skypro.be.quizmaster.service.question.dynamic.math;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Getter
@Component
public class RandomMathQuestionGenerator {

    private final Random random;
    private int value1;
    private int value2;
    private Operator operator;
    private int result;
    private String description;

    public RandomMathQuestionGenerator(@Qualifier(value = "randomBean") Random random) {
        this.random = random;
        log.info("Random question generator initialized with randomBean {}", random);
    }

    public RandomMathQuestionGenerator generate() {
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
        return this;
    }

    public String toString() {
        return value1 + " " + operator.getSymbol() + " " + value2 + " = ";
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

