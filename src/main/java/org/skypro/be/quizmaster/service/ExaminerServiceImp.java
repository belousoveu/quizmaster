package org.skypro.be.quizmaster.service;

import lombok.extern.slf4j.Slf4j;
import org.skypro.be.quizmaster.exception.NotEnoughQuestionsException;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class ExaminerServiceImp implements ExaminerService {

    private final
    QuestionServiceFactory factory;

    private final
    QuestionRepository questionRepository;

    public ExaminerServiceImp(QuestionServiceFactory factory, QuestionRepository questionRepository) {
        this.factory = factory;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getQuestions(ExamSettingDto examSettings) {

        Set<Question> questions = new HashSet<>();
        List<Section> selectedSections = examSettings.getSelectedSections().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
        List<QuestionType> selectedTypes = examSettings.getSelectedTypes().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        if (!checkingEnoughQuestions(examSettings.getNumberOfQuestions(), selectedSections, selectedTypes)) {
            throw new NotEnoughQuestionsException(examSettings.getNumberOfQuestions(), selectedSections, selectedTypes);
        }

        while (questions.size() < examSettings.getNumberOfQuestions()) {
            Section randomSection = RandomUtils.getRandomElement(selectedSections);
            QuestionType randomType = RandomUtils.getRandomElement(selectedTypes);
            QuestionService questionService = factory.getService(randomSection);
            try {
                questions.add(questionService.getRandomQuestion(randomType));
            } catch (
                    NullPointerException ignored) {
                log.warn("Нет подходящего вопроса в БД. Раздел: {}, Тип вопроса: {}", randomSection, randomType);
            }
        }

        return questions.stream().toList();
    }


    @Override
    public String getExamDescription(ExamSettingDto examSettings) {
        if (examSettings.getSelectedSections().entrySet().stream().filter(Map.Entry::getValue).toList().size() > 1) {
            return "Комплексный тест";
        } else {
            return examSettings.getSelectedSections().keySet().stream().findFirst().get().getDescription();
        }
    }

    private boolean checkingEnoughQuestions(int numberOfQuestions,
                                            List<Section> selectedSections,
                                            List<QuestionType> selectedTypes) {
        if (selectedSections.stream().noneMatch(Section::getAutomaticQuestionGeneration)) {
            return numberOfQuestions <= questionRepository
                    .countBySectionAndQuestionType(selectedSections, selectedTypes);
        }
        return true;
    }
}
