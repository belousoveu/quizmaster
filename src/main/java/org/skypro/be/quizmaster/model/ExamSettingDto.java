package org.skypro.be.quizmaster.model;


import jakarta.validation.constraints.*;

import java.util.Map;

public class ExamSettingDto {

    @NotNull(message = "Необходимо заполнить поле")
    @Min(value = 3, message = "Число вопросов должно быть от 3 до 10")
    @Max(value = 10, message = "Число вопросов должно быть от 3 до 10")
    private int numberOfQuestions;

    @NotEmpty(message = "Нужно выбрать хотя бы один из разделов")
    private Map<Section, Boolean> selectedSections;
    @NotEmpty(message = "Нужно выбрать хотя бы один из типов вопросов")
    private Map<QuestionType, Boolean> selectedTypes;


    public ExamSettingDto() {
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
