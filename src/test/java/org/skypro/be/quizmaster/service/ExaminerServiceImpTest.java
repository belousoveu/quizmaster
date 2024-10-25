package org.skypro.be.quizmaster.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.quizmaster.converter.ExamSettingMapper;
import org.skypro.be.quizmaster.exception.NotEnoughQuestionsException;
import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.skypro.be.quizmaster.service.utils.RandomUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImpTest {

    @Mock
    private QuestionServiceFactory factory;

    @Mock
    private QuestionRepository repository;

    @Mock
    private QuestionService questionService;

    private ExamSettingDto examSettingFullSection;
    private ExamSettingDto examSettingOneSection;
    private final RandomUtils randomUtils = new RandomUtils();


    @InjectMocks
    private ExaminerServiceImp out;

    @BeforeEach
    void setUp() {
        examSettingFullSection = ExamSettingMapper.INSTANCE.toDto(new ExamSetting());
        examSettingOneSection = ExamSettingMapper.INSTANCE.toDto(new ExamSetting());
        Map<Section, Boolean> sections = examSettingOneSection.getSelectedSections();
        sections.put(Section.MATH, false);
        sections.put(Section.SPRING, false);
        examSettingOneSection.setSelectedSections(sections);
        randomUtils.setRandom(new Random());
    }


    @Test
    void getQuestions_WhenNotEnoughQuestions() {
        when(repository.countBySectionAndQuestionType(anyList(), anyList())).thenReturn(0L);
        assertThrows(NotEnoughQuestionsException.class, () -> out.getQuestions(examSettingOneSection));
    }

    @Test
    void getQuestions_WhenEnoughQuestions() {
        List<Question> expected = new ArrayList<>();
        for (int i = 0; i < examSettingFullSection.getNumberOfQuestions(); i++) {
            Question question = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
            question.setId(i);
            expected.add(question);
        }
        AtomicLong id = new AtomicLong(0);
        when(factory.getService(any(Section.class))).thenReturn(questionService);
        when(questionService.getRandomQuestion(any(QuestionType.class))).thenAnswer(i -> {
            Question question = TestData.getMockQuestion(Section.JAVA, QuestionType.OPEN_QUESTION);
            question.setId(id.getAndIncrement());
            return question;
        });

        // Пояснение: видимо из-за многопоточности возвращаемый список заполняется вперемешку, поэтому для
        // сравнения с ожидаемым результатом приходится использовать сортировку
        //
        List<Question> actual = new ArrayList<>(out.getQuestions(examSettingFullSection));
        actual.sort(Comparator.comparing(Question::getId));

        assertNotNull(actual);
        assertIterableEquals(expected, actual);

    }


    @Test
    void getExamDescription_testWhenFewSections() {
        assertEquals("Комплексный тест", out.getExamDescription(examSettingFullSection));
    }

    @Test
    void getExamDescription_testWhenOneSection() {
        assertEquals(Section.JAVA.getDescription(), out.getExamDescription(examSettingOneSection));
    }
}