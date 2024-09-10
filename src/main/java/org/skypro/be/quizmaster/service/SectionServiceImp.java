package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class SectionServiceImp implements SectionService{

    @Autowired
    QuestionService questionService;

    public List<Question> getQuestions() {
        return null;
    }

    public List<Question> getQuestions(Section section) {
//        Section choiceSection = Section.valueOf(section.toUpperCase());
       // QuestionService questionService=section.getService();
        return questionService.getQuestions(section);
    }

    @Override
    public String getDescription(String section) {
        return Section.valueOf(section.toUpperCase()).getDescription();
    }

    @Override
    public void addQuestion(Question question) {
        questionService.addQuestion(question);
    }

    @Override
    public List<String> errors(Question question) {
        List<String> errors = new ArrayList<>();
        if (question.getTextQuestion() == null || question.getTextQuestion().trim().isEmpty()) {
            errors.add("Текст вопроса должен быть заполнен");
        }
        if (question.getAnswers().stream().noneMatch(Answer::isCorrect)) {
            errors.add("Должен быть хотя бы один правильный ответ");
        }
        if (question.getAnswers().stream().anyMatch(answer -> answer.getTextAnswer().trim().isEmpty()))  {
            errors.add("Поле ответ не должно быть пустым");
        }
        return errors;
    }

    @Override
    public List<Section> getSections() {
        return Arrays.asList(Section.values());
    }

    public  Question createQuestion(Section section) {
//        System.out.println("section = " + section);
//        Section choiceSection = Section.valueOf(section.toUpperCase());
        if (section.isAutomaticQuestionGenerated()) {
            throw new UnsupportedOperationException();
        }
        return new Question(section);
    };
}
