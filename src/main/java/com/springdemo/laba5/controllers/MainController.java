package com.springdemo.laba5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
@Controller
public class MainController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }



}
