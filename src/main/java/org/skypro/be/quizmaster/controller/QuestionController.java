package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.skypro.be.quizmaster.converter.QuestionMapper;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.model.dto.QuestionDto;
import org.skypro.be.quizmaster.service.QuestionServiceFactory;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionServiceFactory factory;

    public QuestionController(QuestionServiceFactory factory) {
        this.factory = factory;
    }

    @GetMapping("/{section}")
    public String getQuestionsBySection(@PathVariable("section") Section section, Model model, HttpServletRequest request) {
        QuestionService questionService = factory.getService(section);
        model.addAttribute("section", section);
        request.setAttribute("title", "Раздел: " + section.getDescription());
        request.setAttribute("types", questionService.getQuestionTypesStatistics());
        model.addAttribute("questions", questionService.getQuestions());
        return "question/questions";
    }

    @GetMapping("/{section}/add")
    public String newQuestion(@PathVariable("section") Section section, Model model) {
        QuestionService questionService = factory.getService(section);
        Question question = questionService.createQuestion();
        model.addAttribute("section", section.getName());
        model.addAttribute("title", "Создание нового вопроса: " + section.getDescription());
        model.addAttribute("question", question);
        return "question/question-form";
    }

    @GetMapping("/{section}/{id}/edit")
    public String editQuestion(@PathVariable("section") Section section, @PathVariable("id") Long id, Model model) {
        QuestionService questionService = factory.getService(section);
        Question question = questionService.getQuestion(id);
        model.addAttribute("section", section.getName());
        model.addAttribute("title", "Редактирование вопроса: " + section.getDescription());
        model.addAttribute("question", question);
        return "question/question-form";
    }

    @GetMapping("/{section}/{id}/delete")
    public String deleteQuestion(@PathVariable("section") Section section, @PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {

        QuestionService questionService = factory.getService(section);
        redirectAttributes.addFlashAttribute("deleteMessage", "Удален вопрос id=" + id);
        questionService.deleteQuestion(id);
        return "redirect:/question/" + section.getName();
    }

    @PostMapping("/{section}/save")
    public String addQuestion(@PathVariable("section") Section section,
                              @Valid @ModelAttribute("question") QuestionDto questionDto, BindingResult result,
                              RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            String title = questionDto.getId() == 0 ? "Создание нового вопроса: " : "Редактирование вопроса: ";
            model.addAttribute("title", title + section.getDescription());
            model.addAttribute("question", questionDto);
            model.addAttribute("section", section.getName());
            return "question/question-form";
        }

        QuestionService questionService = factory.getService(section);
        String updatedMessage = questionDto.getId() == 0 ? "Добавлен новый вопрос" : "Изменен вопрос id=" + questionDto.getId();
        redirectAttributes.addFlashAttribute("updateMessage", updatedMessage);
        Question question = QuestionMapper.INSTANCE.toEntity(questionDto);
        questionService.saveQuestion(question);
        return "redirect:/question/" + section.getName();
    }
}
