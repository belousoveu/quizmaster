package org.skypro.be.quizmaster.controller;

import org.skypro.be.quizmaster.exception.GetListDynamicQuestionsException;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;

@ControllerAdvice
public class GlobalControllerAdvice {

    public GlobalControllerAdvice() {
    }

    @ExceptionHandler(GetListDynamicQuestionsException.class)
    public String handleGetListDynamicQuestionsException(GetListDynamicQuestionsException e, Model model) {
        model.addAttribute("dynamicMessage", e.getMessage());
        model.addAttribute("menuItems", Arrays.asList(Section.values()));
        return "question/questions";
    }

    @ModelAttribute
    public void getMenuItems(Model model) {
        model.addAttribute("menuItems", Arrays.asList(Section.values()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            Long userId = ((User) authentication.getPrincipal()).getId();
            model.addAttribute("userId", userId);
        }
    }
}
