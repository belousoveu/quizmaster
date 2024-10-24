package org.skypro.be.quizmaster.service.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Component
public class RandomUtils {

    @Getter
    private static Random random;

    @Autowired
    public void setRandom(@Qualifier(value = "randomBean") Random random) {
        RandomUtils.random = random;
    }


    public static <T> T getRandomElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection is null or empty");
        }
        List<T> list = new ArrayList<>(collection);
        return list.get(random.nextInt(list.size()));
    }

    public static int getRandomIntWithinRange(int target, int deviation) {
        return random.nextInt(((target + deviation) - (target - deviation) + 1)) + (target - deviation);
    }

}
