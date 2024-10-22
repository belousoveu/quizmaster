package org.skypro.be.quizmaster.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomUtilsTest {

    private Random random;
    private final RandomUtils out = new RandomUtils();

    @BeforeEach
    public void setUp() {
        random = Mockito.mock(Random.class);
        out.setRandom(random);
    }

    @Test
    public void getRandomElement_withNullCollection() {
        assertThrows(IllegalArgumentException.class, () -> RandomUtils.getRandomElement(null));
    }

    @Test
    public void getRandomElement_withEmptyCollection() {
        List<String> emptyList = List.of();
        assertThrows(IllegalArgumentException.class, () -> RandomUtils.getRandomElement(emptyList));
    }

    @Test
    public void getRandomElement_withNotEmptyCollection() {
        List<String> expectedList = List.of("a", "b", "c");
        when(random.nextInt(3)).thenReturn(1);
        assertEquals(expectedList.get(1), RandomUtils.getRandomElement(expectedList));
    }


    @Test
    public void getRandomIntWithinRange_withCorrectParameters() {
        int target = 100;
        int deviation = 10;
        when(random.nextInt(anyInt())).thenReturn(5);
        assertEquals(95, RandomUtils.getRandomIntWithinRange(target, deviation));
    }

    @Test
    public void getRandomIntWithinRange_withZeroDeviation() {
        int target = 100;
        int deviation = 0;
        when(random.nextInt(anyInt())).thenReturn(0);
        assertEquals(100, RandomUtils.getRandomIntWithinRange(target, deviation));
    }

    @Test
    public void getRandomIntWithinRange_withNegativeDeviation() {
        int target = 100;
        int deviation = -10;
        doThrow(IllegalArgumentException.class).when(random).nextInt(-19);
        assertThrows(IllegalArgumentException.class, () -> RandomUtils.getRandomIntWithinRange(target, deviation));

    }

    @Test
    public void getRandomIntWithinRange_withNegativeTarget() {
        int target = -100;
        int deviation = 10;
        when(random.nextInt(anyInt())).thenReturn(5);
        assertEquals(-105, RandomUtils.getRandomIntWithinRange(target, deviation));
    }

}