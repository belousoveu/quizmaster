package org.skypro.be.quizmaster.service.utils;

import java.util.*;

public class RandomUtils {

    static final Random random = new Random();

    public static <T> T getRandomElement(Collection<T> collection) {
        if (collection != null) {
            List<T> list = new ArrayList<>(collection);
            return list.get(random.nextInt(list.size()));
        }
        throw new NullPointerException();
    }

    public static int getRandomIntWithinRange(int target, int deviation) {
        return random.nextInt(((target + deviation) - (target - deviation) + 1)) + (target - deviation);
    }

    public static Set<Integer> getRandomIntSetWithinRange(int target, int deviation, int size) {
        Set<Integer> resultSet = new HashSet<>();
        resultSet.add(target);
        while (resultSet.size() <= size) {
            resultSet.add(getRandomIntWithinRange(target, deviation));
        }
        return resultSet;
    }
}
