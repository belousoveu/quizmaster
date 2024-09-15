package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.skypro.be.quizmaster.exception.GetListDynamicQuestionsException;
import org.skypro.be.quizmaster.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    SectionService sectionService;

    @ExceptionHandler(GetListDynamicQuestionsException.class)
    public String handleGetListDynamicQuestionsException(GetListDynamicQuestionsException e, Model model, HttpServletRequest request) {
        model.addAttribute("dynamicMessage", e.getMessage());
        model.addAttribute("menuItems", sectionService.getSections());
        return "question/questions";
    }

    @ModelAttribute
    public void getMenuItems(Model model) {
        model.addAttribute("menuItems", sectionService.getSections());
    }
}
