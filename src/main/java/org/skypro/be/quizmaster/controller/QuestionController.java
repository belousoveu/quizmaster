package org.skypro.be.quizmaster.controller;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.QuestionServiceImp;
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

    @GetMapping("/{section}")
    public String getQuestionsBySection(@PathVariable("section") Section section, Model model) {
        model.addAttribute("section", section);
        model.addAttribute("title", "Раздел: "+section.getDescription());
        model.addAttribute("questions", sectionService.getQuestions(section));
        return "question/questions";
    }

    @GetMapping("/{section}/add")
    public String newQuestion(@PathVariable("section") Section section, Model model) {
        Question question = sectionService.createQuestion(section);
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
        sectionService.addQuestion(question);
        return "redirect:/question/" + section.getName();
    }


}
