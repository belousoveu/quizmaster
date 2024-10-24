package org.skypro.be.quizmaster.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.dto.QuestionDto;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    Question toEntity(QuestionDto questionDto);

}
