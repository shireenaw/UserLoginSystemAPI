package com.premiergaming.controller.web;

import com.premiergaming.model.dto.UsersDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @GetMapping("/createUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView createUser() {
//        ModelAndView mav = new ModelAndView("admin/createUser");
//        UsersDTO user = new UsersDTO();
//        mav.addObject(user);
        return new ModelAndView("admin/createUser");
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView adminDashboard() {
        return new ModelAndView("admin/dashboard");
    }

    @GetMapping("/view/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView adminView(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("admin/displayUser");
        mav.addObject("usersId",userId);
        return mav;
    }
}
