package org.skypro.be.quizmaster.model.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;

import java.util.Map;

@Data
public class ExamSettingDto {

    @NotNull(message = "Необходимо заполнить поле")
    @Min(value = 3, message = "Число вопросов должно быть от 3 до 10")
    @Max(value = 10, message = "Число вопросов должно быть от 3 до 10")
    private int numberOfQuestions;

    @NotEmpty(message = "Нужно выбрать хотя бы один из разделов")
    private Map<Section, Boolean> selectedSections;
    @NotEmpty(message = "Нужно выбрать хотя бы один из типов вопросов")
    private Map<QuestionType, Boolean> selectedTypes;

}
