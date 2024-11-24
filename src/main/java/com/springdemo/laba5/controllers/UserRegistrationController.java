package com.springdemo.laba5.controllers;

import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.services.UserService;
import com.springdemo.laba5.services.UserServiceIMPL;
import com.springdemo.laba5.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;
    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;

    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userDto) {
        userService.save(userDto);
        return "redirect:/registration?success";
    }

}