package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.*;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.repository.ResultsRepository;
import org.skypro.be.quizmaster.service.questionService.QuestionService;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExaminerService {

    @Autowired
    SectionService sectionService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResultsRepository resultsRepository;

    public ExamSetting getDefaultSettings() {
        return new ExamSetting();
    }

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
            throw new IllegalArgumentException("Not enough questions for the exam"); //TODO: добавить отдельное исключение и обработать его.
            // Не хватает вопросов в базе
        }

        while (questions.size() < examSettings.getNumberOfQuestions()) {
            Section randomSection = RandomUtils.getRandomElement(selectedSections);
            QuestionType randomType = RandomUtils.getRandomElement(selectedTypes);
            QuestionService questionService = sectionService.getService(randomSection);
            try {
                questions.add(questionService.getRandomQuestion(randomType));
            } catch (
                    IllegalArgumentException ignored) { //TODO: создать отдельный класс ошибок для случая если подходящего вопроса нет в базе
            }
        }

        return questions.stream().toList();
    }

    public List<ExamQuestion> getResult(List<Question> questions, Map<String, List<String>> answers) {
        List<ExamQuestion> examQuestions = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            ExamQuestion examQuestion = new ExamQuestion(questions.get(i));
            examQuestion.updateUserAnswers(answers.get("answer_" + i));
            examQuestion.validateAnswer();
            examQuestions.add(examQuestion);
        }
        return examQuestions;
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

    public List<Result> getUserResults(Long userId) {
        return resultsRepository.findAllByUserId(userId);
    }

    public String getExamDescription(ExamSettingDto examSettings) {
        if (examSettings.getSelectedSections().entrySet().stream().filter(Map.Entry::getValue).toList().size() > 1) {
            return "Комплексный тест";
        } else {
            return examSettings.getSelectedSections().keySet().stream().findFirst().get().getDescription();
        }
    }

    public void saveResult(Long userId, List<ExamQuestion> examResults, String description) {
        Result result = new Result(userId,
                description,
                LocalDateTime.now(),
                (long) examResults.size(),
                examResults.stream().filter(ExamQuestion::getCorrect).count());
        resultsRepository.save(result);
    }
}
