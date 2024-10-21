package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.exception.InvalidQuestionServiceException;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QuestionServiceFactoryImp implements QuestionServiceFactory {

    private final Map<Section, QuestionService> serviceMap;

    @Autowired
    public QuestionServiceFactoryImp(Map<String, QuestionService> serviceMap) {
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
            throw new InvalidQuestionServiceException("Service is not annotated with @QuestionService" + service.getClass().getName());
        }
        return annotation.value();
    }

}
