package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.*;
import org.skypro.be.quizmaster.service.questionService.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExaminerService {

    @Autowired
    SectionService sectionService;

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

        while (questions.size() < examSettings.getNumberOfQuestions()) {
            Section randomSection = getRandomSection(examSettings.getSelectedSections());
            QuestionService questionService=sectionService.getService(randomSection);
            List<QuestionType> types = examSettings.getSelectedTypes().entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();
            questions.add(questionService.getRandomQuestion(types));
        }

        for (Question question : questions) {
            System.out.println("question.getTextQuestion() = " + question.getTextQuestion());
            question.getAnswers().forEach(answer -> System.out.println("answer.getTextAnswer() = " + answer.getTextAnswer()+" "+answer.getIsCorrect()));

        }

        return questions.stream().toList();
    }

    private Section getRandomSection(Map<Section, Boolean> selectedSections) {
        if (selectedSections.size() == 1) {
            return selectedSections.keySet().iterator().next();
        }
        List<Section> sections = new ArrayList<>(selectedSections.keySet());
        Collections.shuffle(sections);
        return sections.get(0);
    }
}
