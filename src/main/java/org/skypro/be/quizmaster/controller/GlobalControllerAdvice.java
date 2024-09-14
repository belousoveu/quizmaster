package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.skypro.be.quizmaster.exception.GetListDynamicQuestionsException;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    SectionService sectionService;

    @ExceptionHandler(GetListDynamicQuestionsException.class)
    public String handleGetListDynamicQuestionsException(GetListDynamicQuestionsException e,  Model model, HttpServletRequest request) {
        String titleMessage = (String) request.getAttribute("title");
        model.addAttribute("dynamic", true);
        model.addAttribute("dynamicMessage", e.getMessage());
        model.addAttribute("title", titleMessage);
        List<Section> menuItems = sectionService.getSections();
        model.addAttribute("menuItems", menuItems);
        return "question/questions";
    }

    @ModelAttribute
    public void getMenuItems(Model model) {
        List<Section> menuItems = sectionService.getSections();
        model.addAttribute("menuItems", menuItems);
    }
}
