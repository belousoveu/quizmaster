package org.skypro.be.quizmaster.converter;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionDto;

public class QuestionMapper {

    public static Question toEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTextQuestion(questionDto.getTextQuestion());
        question.setAnswers(questionDto.getAnswers());
        question.setSection(questionDto.getSection());
        return question;
    }

    public static QuestionDto toDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setTextQuestion(question.getTextQuestion());
        questionDto.setAnswers(question.getAnswers());
        questionDto.setSection(question.getSection());
        return questionDto;
    }
}
