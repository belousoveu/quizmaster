package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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
            throw new IllegalArgumentException("Not enough questions for the exam"); //TODO: добавить отдельное исключение и обработать его.
            // Не хватает вопросов в базе
        }

        while (questions.size() < examSettings.getNumberOfQuestions()) {
            Section randomSection = RandomUtils.getRandomElement(selectedSections);
            QuestionType randomType = RandomUtils.getRandomElement(selectedTypes);
            QuestionService questionService = factory.getService(randomSection);
            try {
                questions.add(questionService.getRandomQuestion(randomType));
            } catch (
                    IllegalArgumentException ignored) { //TODO: создать отдельный класс ошибок для случая если подходящего вопроса нет в базе
            }
        }

        return questions.stream().toList();
    }

    @Override
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
