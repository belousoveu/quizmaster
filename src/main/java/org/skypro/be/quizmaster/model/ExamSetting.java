package org.skypro.be.quizmaster.model;

import java.util.Arrays;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExamSetting {
    private int numberOfQuestions = 5;
    private Map<Section, Boolean> selectedSections = Arrays.stream(Section.values())
            .collect(Collectors.toMap(Function.identity(), section -> true,
                    (v1, v2) -> { throw new IllegalStateException(String.format("Duplicate key %s", v2)); },
                    LinkedHashMap::new));
    private Map<QuestionType, Boolean> selectedTypes = Arrays.stream(QuestionType.values())
            .collect(Collectors.toMap(Function.identity(), type -> true,
                    (v1, v2) -> { throw new IllegalStateException(String.format("Duplicate key %s", v2)); },
                    LinkedHashMap::new));


    public ExamSetting() {
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Map<Section, Boolean> getSelectedSections() {
        return selectedSections;
    }

    public void setSelectedSections(Map<Section, Boolean> selectedSections) {
        this.selectedSections = selectedSections;
    }

    public Map<QuestionType, Boolean> getSelectedTypes() {
        return selectedTypes;
    }

    public void setSelectedTypes(Map<QuestionType, Boolean> selectedTypes) {
        this.selectedTypes = selectedTypes;
    }

}
