package org.skypro.be.quizmaster.converter;

import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;

public class ExamSettingMapper {

    public static ExamSetting toEntity(ExamSettingDto dto) {
        ExamSetting examSetting = new ExamSetting();
        examSetting.setNumberOfQuestions(dto.getNumberOfQuestions());
        examSetting.setSelectedSections(dto.getSelectedSections());
        examSetting.setSelectedTypes(dto.getSelectedTypes());
        return examSetting;
    }

    public static ExamSettingDto toDto(ExamSetting entity) {
        ExamSettingDto dto = new ExamSettingDto();
        dto.setNumberOfQuestions(entity.getNumberOfQuestions());
        dto.setSelectedSections(entity.getSelectedSections());
        dto.setSelectedTypes(entity.getSelectedTypes());
        return dto;
    }
}
