package org.skypro.be.quizmaster.service.question.dynamic.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomMathQuestionGeneratorTest {


    @Mock
    private Random random;
    @InjectMocks
    private RandomMathQuestionGenerator generator;


    @Test
    void Add_test() {
        when(random.nextInt(20)).thenReturn(5, 3);
        when(random.nextInt(5)).thenReturn(0); //Operator ADD

        RandomMathQuestionGenerator actual = generator.generate();

        assertEquals(8, actual.getResult());
        assertEquals("5 + 3 = ", actual.toString());
        assertEquals("Сумма чисел 5 и 3", actual.getDescription());
    }

    @Test
    void Subtract_test() {
        when(random.nextInt(20)).thenReturn(5, 3);
        when(random.nextInt(5)).thenReturn(1); //Operator SUBTRACT

        RandomMathQuestionGenerator actual = generator.generate();

        assertEquals(2, actual.getResult());
        assertEquals("5 - 3 = ", actual.toString());
        assertEquals("Разность чисел 5 и 3", actual.getDescription());
    }

    @Test
    void Multiply_test() {
        when(random.nextInt(20)).thenReturn(5, 3);
        when(random.nextInt(5)).thenReturn(2); //Operator MULTIPLY

        RandomMathQuestionGenerator actual = generator.generate();

        assertEquals(15, actual.getResult());
        assertEquals("5 * 3 = ", actual.toString());
        assertEquals("Произведение чисел 5 и 3", actual.getDescription());
    }

    @Test
    void Divide_test() {
        when(random.nextInt(20)).thenReturn(5, 3);
        when(random.nextInt(5)).thenReturn(3); //Operator DIVIDE

        RandomMathQuestionGenerator actual = generator.generate();

        assertEquals(1, actual.getResult());
        assertEquals("5 / 3 = ", actual.toString());
        assertEquals("Целочисленный результат от деления числа 5 на 3", actual.getDescription());
    }

    @Test
    void Modulo_test() {
        when(random.nextInt(20)).thenReturn(5, 3);
        when(random.nextInt(5)).thenReturn(4); //Operator MODULO

        RandomMathQuestionGenerator actual = generator.generate();

        assertEquals(2, actual.getResult());
        assertEquals("5 % 3 = ", actual.toString());
        assertEquals("Остаток от деления числа 5 на 3", actual.getDescription());
    }


}