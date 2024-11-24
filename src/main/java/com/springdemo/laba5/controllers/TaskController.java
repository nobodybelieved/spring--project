package com.springdemo.laba5.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.springdemo.laba5.entities.Task;
import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.services.TaskService;
import com.springdemo.laba5.services.UserService;
import com.springdemo.laba5.services.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller

public class TaskController {
    private final UserServiceIMPL userService;
    private final TaskService taskService;

    @Autowired
    public TaskController(UserServiceIMPL userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping("/main")
    public String showProfilePage(Model model, Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.findByEmail(email);

            model.addAttribute("user", user);
            return "main";
        } catch (Exception e) {
            System.err.println("Error in showProfilePage: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/tasks")
    public String showTasksPage(Model model, Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.findByEmail(email);

            List<Task> tasks = taskService.findTaskByUserId(user.getId());
            model.addAttribute("tasks", tasks);

            return "tasks"; // Страница с задачами
        } catch (Exception e) {
            System.err.println("Error retrieving tasks: " + e.getMessage());
            return "error";
        }
    }



    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
