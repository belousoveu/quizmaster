package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.skypro.be.quizmaster.converter.QuestionMapper;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.dto.QuestionDto;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.questionService.QuestionService;
import org.skypro.be.quizmaster.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/question")
@SessionAttributes("targetView")
public class QuestionController {

    @Autowired
    private SectionService sectionService;

    private QuestionService questionService;

    @GetMapping("/{section}")
    public String getQuestionsBySection(@PathVariable("section") Section section, Model model, HttpServletRequest request) {
        request.setAttribute("sectionName", section.getName());
        questionService = sectionService.getService(section);
        model.addAttribute("section", section);
        model.addAttribute("title", "Раздел: "+section.getDescription());
        request.setAttribute("title", "Раздел: "+section.getDescription());
        model.addAttribute("questions", questionService.getQuestions());
        return "question/questions";
    }

    @GetMapping("/{section}/add")
    public String newQuestion(@PathVariable("section") Section section, Model model) {
        questionService = sectionService.getService(section);
        Question question = questionService.createQuestion();
        model.addAttribute("section", section.getName());
        model.addAttribute("title", "Создание нового вопроса: " + section.getDescription());
        model.addAttribute("question", question);
        return "question/question-form";
    }

    @GetMapping("/{section}/{id}/edit")
    public String editQuestion(@PathVariable("section") Section section, @PathVariable("id") Long id, Model model) {
        questionService = sectionService.getService(section);
        Question question = questionService.getQuestion(id);
        model.addAttribute("section", section.getName());
        model.addAttribute("title", "Редактирование вопроса: " + section.getDescription());
        model.addAttribute("question", question);
        return "question/question-form";
    }

    @GetMapping("/{section}/{id}/delete")
    public String deleteQuestion(@PathVariable("section") Section section, @PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes, Model model) {

        questionService = sectionService.getService(section);
        redirectAttributes.addFlashAttribute("deleteMessage", "Удален вопрос id=" + id);
        questionService.deleteQuestion(id);
        return "redirect:/question/" + section.getName();
    }

    @PostMapping("/{section}/save")
    public String addQuestion(@PathVariable("section") Section section, @ModelAttribute("question") QuestionDto questionDto,
                              RedirectAttributes redirectAttributes, Model model) {
        List<String> errors = sectionService.errors(questionDto);
        if (!errors.isEmpty()) { //TODO Переделать валидацию
            model.addAttribute("errors", errors);
            model.addAttribute("section", section.getName());
            model.addAttribute("title", "Добавить в раздел: " + section.getDescription());
            model.addAttribute("question", questionDto);
            return "question/question-form";
        }
        questionService = sectionService.getService(section);
        String updatedMessage = questionDto.getId()==null ? "Добавлен новый вопрос" : "Изменен вопрос id=" + questionDto.getId();
        redirectAttributes.addFlashAttribute("updateMessage", updatedMessage);
        Question question = QuestionMapper.toEntity(questionDto);
        questionService.saveQuestion(question);
        return "redirect:/question/" + section.getName();
    }


}
