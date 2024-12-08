package com.springdemo.laba5.controllers;

import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.services.UserServiceIMPL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;

@Controller
public class MainController {
    private final UserServiceIMPL userService;
    public MainController(UserServiceIMPL userService) {
        super();
        this.userService = userService;

    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        model.addAttribute("user", user);
        return "profile";
    }


}
