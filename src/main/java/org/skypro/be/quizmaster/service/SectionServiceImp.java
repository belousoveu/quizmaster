package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.dto.QuestionDto;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.questionService.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SectionServiceImp implements SectionService {

    private final Map<Section, QuestionService> serviceMap;

    @Autowired
    public SectionServiceImp(Map<String, QuestionService> serviceMap) {
        this.serviceMap = serviceMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> getSection(e.getValue()),
                        Map.Entry::getValue
                ));
    }

    public QuestionService getService(Section section) {
        return serviceMap.get(section);
    }


    private Section getSection(QuestionService service) {
        QuestionServiceSection annotation = service.getClass().getAnnotation(QuestionServiceSection.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Service is not annotated with @QuestionService"); //TODO: Сделать отдельный класс. Ошибка не должна появляться
        }
        return annotation.value();
    }


    @Override
    public List<String> errors(QuestionDto question) {
        List<String> errors = new ArrayList<>();
        if (question.getTextQuestion() == null || question.getTextQuestion().trim().isEmpty()) {
            errors.add("Текст вопроса должен быть заполнен");
        }
        if (question.getAnswers().stream().noneMatch(Answer::isCorrect)) {
            errors.add("Должен быть хотя бы один правильный ответ");
        }
        if (question.getAnswers().stream().anyMatch(answer -> answer.getTextAnswer().trim().isEmpty())) {
            errors.add("Поле ответ не должно быть пустым");
        }
        return errors;
    }

    @Override
    public List<Section> getSections() {
        return Arrays.asList(Section.values());
    }

}
