package org.skypro.be.quizmaster.converter;

import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.mapstruct.Mapper;
import org. mapstruct.factory.Mappers;

@Mapper
public interface ExamSettingMapper {
    ExamSettingMapper INSTANCE = Mappers.getMapper(ExamSettingMapper.class);

    ExamSettingDto toDto(ExamSetting entity);

}
