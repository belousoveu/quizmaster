package org.skypro.be.quizmaster.model;

import lombok.Data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class ExamSetting {
    private int numberOfQuestions = 5;
    private Map<Section, Boolean> selectedSections = Arrays.stream(Section.values())
            .collect(Collectors.toMap(Function.identity(), section -> true,
                    (v1, v2) -> {
                        throw new IllegalStateException(String.format("Duplicate key %s", v2));
                    },
                    LinkedHashMap::new));
    private Map<QuestionType, Boolean> selectedTypes = Arrays.stream(QuestionType.values())
            .collect(Collectors.toMap(Function.identity(), type -> true,
                    (v1, v2) -> {
                        throw new IllegalStateException(String.format("Duplicate key %s", v2));
                    },
                    LinkedHashMap::new));

}
