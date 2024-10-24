package org.skypro.be.quizmaster.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.skypro.be.quizmaster.service.question.dynamic.MathQuestionService;
import org.skypro.be.quizmaster.service.question.manual.JavaQuestionService;
import org.skypro.be.quizmaster.service.question.manual.SpringQuestionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QuestionServiceFactoryImpTest {

    private final Map<String, QuestionService> serviceMap = new HashMap<>();

    QuestionServiceFactoryImp out;

    @BeforeEach
    void setUp() {
        serviceMap.put("javaQuestionService", new JavaQuestionService());
        serviceMap.put("springQuestionService", new SpringQuestionService());
        serviceMap.put("mathQuestionService", new MathQuestionService());

        out = new QuestionServiceFactoryImp(serviceMap);
    }

    @Test
    void getService_test() {

        QuestionService service = out.getService(Section.JAVA);
        assertNotNull(service);
        assertEquals("JavaQuestionService", service.getClass().getSimpleName());

        service = out.getService(Section.SPRING);
        assertNotNull(service);
        assertEquals("SpringQuestionService", service.getClass().getSimpleName());

        service = out.getService(Section.MATH);
        assertNotNull(service);
        assertEquals("MathQuestionService", service.getClass().getSimpleName());

        // Проверяем, что количество сервисов равно количеству разделов
        assertEquals(Section.values().length, out.getServiceMap().size());

    }
}