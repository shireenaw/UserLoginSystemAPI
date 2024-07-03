package com.premiergaming.controller.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/user/")
public class UserController {
    @GetMapping("/home")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ModelAndView getUser() {

        return new ModelAndView("user/home");
    }
}
