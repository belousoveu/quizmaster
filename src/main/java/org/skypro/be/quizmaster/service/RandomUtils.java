package org.skypro.be.quizmaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    static final Random random = new Random();

    public static <T> T getRandomElement(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T t : iterable) {
            list.add(t);
        }
        return list.get(random.nextInt(list.size()));
    }

}
