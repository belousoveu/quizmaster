package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.*;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.service.questionService.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerService {

    @Autowired
    SectionService sectionService;

    @Autowired
    QuestionRepository questionRepository;

    public ExamSetting getDefaultSettings() {
        return new ExamSetting();
    }

    public List<Question> getQuestions(ExamSettingDto examSettings) {
        List<Question> result = new ArrayList<>();
        System.out.println("examSettings.getNumberOfQuestions() = " + examSettings.getNumberOfQuestions());
        examSettings.getSelectedSections().forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        examSettings.getSelectedTypes().forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        Set<Question> questions = new HashSet<>();
        Question arithmeticQuestion = new Question(Section.MATH);
        System.out.println("arithmeticQuestion.getId() = " + arithmeticQuestion.getId());
        System.out.println("arithmeticQuestion.getSection() = " + arithmeticQuestion.getSection());

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
            QuestionService questionService=sectionService.getService(randomSection);
            try {
                questions.add(questionService.getRandomQuestion(randomType));
            } catch (IllegalArgumentException ignored) { //TODO: создать отдельный класс ошибок для случая если подходящего вопроса нет в базе
            }
        }

        for (Question question : questions) {
            System.out.println("question.getTextQuestion() = " + question.getTextQuestion());
            question.getAnswers().forEach(answer -> System.out.println("answer.getTextAnswer() = " + answer.getTextAnswer()+" "+answer.getIsCorrect()));
        }

        return questions.stream().toList();
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
