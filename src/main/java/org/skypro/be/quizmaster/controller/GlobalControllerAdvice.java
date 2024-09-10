package org.skypro.be.quizmaster.controller;

import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    SectionService sectionService;

//    @GetMapping("/")
//    public String viewStartPage(Model model) {
//        model.addAttribute("menuItems", sectionService.getSections());
//        return "index";
//    }
//
//    @GetMapping("/home")
//    public String viewHomePage(Model model) {
//        model.addAttribute("menuItems", sectionService.getSections());
//        return "home";
//    }

    @ModelAttribute
    public void getMenuItems(Model model) {
        List<Section> menuItems =sectionService.getSections();
        model.addAttribute("menuItems", menuItems);
    }
}
