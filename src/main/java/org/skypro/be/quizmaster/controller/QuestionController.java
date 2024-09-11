package org.skypro.be.quizmaster.controller;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.QuestionService;
import org.skypro.be.quizmaster.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private SectionService sectionService;

    private QuestionService questionService;

    @GetMapping("/{section}")
    public String getQuestionsBySection(@PathVariable("section") Section section, Model model) {
        questionService = sectionService.getService(section);
        model.addAttribute("section", section);
        model.addAttribute("title", "Раздел: "+section.getDescription());
        model.addAttribute("questions", questionService.getQuestions());
        return "question/questions";
    }

    @GetMapping("/{section}/add")
    public String newQuestion(@PathVariable("section") Section section, Model model) {
        questionService = sectionService.getService(section);
        Question question = questionService.createQuestion();
        model.addAttribute("section", section.getName());
        model.addAttribute("title", "Добавить вопрос в раздел: " + section.getDescription());
        model.addAttribute("question", question);
        return "question/addQuestion";
    }

    @PostMapping("/{section}/add")
    public String addQuestion(@PathVariable("section") Section section, @ModelAttribute("question") Question question, Model model) {
        List<String> errors = sectionService.errors(question);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("section", section.getName());
            model.addAttribute("title", "Добавить в раздел: " + section.getDescription());
            model.addAttribute("question", question);
            return "question/addQuestion";
        }
        questionService = sectionService.getService(section);
        questionService.addQuestion(question);
        return "redirect:/question/" + section.getName();
    }


}
