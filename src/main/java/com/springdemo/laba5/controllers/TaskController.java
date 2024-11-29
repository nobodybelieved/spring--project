package com.springdemo.laba5.controllers;

import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import com.springdemo.laba5.entities.Category;
import com.springdemo.laba5.repositories.CategoryRepository;
import com.springdemo.laba5.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller

public class TaskController {
    private final UserServiceIMPL userService;
    private final TaskService taskService;
    private final CategoryService categoryService;

    @Autowired
    public TaskController(UserServiceIMPL userService, TaskService taskService, CategoryService categoryService) {
        this.userService = userService;
        this.taskService = taskService;
        this.categoryService = categoryService;
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

//    @GetMapping("/tasks")
//    public String showTasksPage(Model model, Principal principal) {
//        try {
//            String email = principal.getName();
//            User user = userService.findByEmail(email);
//
//            List<Task> tasks = taskService.findTaskByUserId(user.getId());
//            model.addAttribute("tasks", tasks);
//
//            return "tasks";
//        } catch (Exception e) {
//            System.err.println("Error retrieving tasks: " + e.getMessage());
//            return "error";
//        }
//    }


    @GetMapping("/tasks")
    public String showTasksPage(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false) Long categoryId,
                                Model model, Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.findByEmail(email);

            Pageable pageable = PageRequest.of(page, size);
            Page<Task> taskPage;

            if (title != null && !title.isEmpty() && categoryId != null) {
                taskPage = taskService.findByUserIdAndTitleContainingAndCategoryId(user.getId(), title, categoryId, pageable);
            } else if (title != null && !title.isEmpty()) {
                taskPage = taskService.findByUserIdAndTitleContaining(user.getId(), title, pageable);
            } else if (categoryId != null) {
                taskPage = taskService.findByUserIdAndCategoryId(user.getId(), categoryId, pageable);
            } else {
                taskPage = taskService.findTaskByUserId(user.getId(), pageable);
            }

            model.addAttribute("taskPage", taskPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", taskPage.getTotalPages());
            model.addAttribute("totalItems", taskPage.getTotalElements());

            model.addAttribute("title", title);
            model.addAttribute("categoryId", categoryId);

            List<Map<String, Object>> categories = new ArrayList<>();
            categories.add(Map.of("id", 1L, "name", "Easy"));
            categories.add(Map.of("id", 2L, "name", "Hard"));
            model.addAttribute("categories", categories);

            return "tasks";
        } catch (Exception e) {
            System.err.println("Error retrieving tasks: " + e.getMessage());
            return "error";
        }
    }



//    @GetMapping("/tasks")
//    public String showTasksPage(@RequestParam(defaultValue = "0") int page,
//                                @RequestParam(defaultValue = "5") int size,
//                                Model model, Principal principal) {
//        try {
//            String email = principal.getName();
//            User user = userService.findByEmail(email);
//            Pageable pageable = PageRequest.of(page, size);
//            Page<Task> taskPage = taskService.findTaskByUserId(user.getId(), pageable);
//
//            model.addAttribute("taskPage", taskPage);
//            model.addAttribute("currentPage", page);
//            model.addAttribute("totalPages", taskPage.getTotalPages());
//            model.addAttribute("totalItems", taskPage.getTotalElements());
//
//            return "tasks";
//        } catch (Exception e) {
//            System.err.println("Error retrieving tasks: " + e.getMessage());
//            return "error";
//        }
//    }





    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task, Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            task.setUser(user);

            if (task.getStatus() == null || task.getStatus().isEmpty()) {
                task.setStatus("Pending");
            }

            if (task.getCategory() == null || task.getCategory().getId() == null) {
                task.setCategory(categoryService.findById(1L));
            }

            LocalDate localDate = LocalDate.now().plusDays(1);
            Date dueDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            task.setDueDate(dueDate);

            taskService.save(task);

            return "redirect:/tasks";
        } catch (Exception e) {
            System.err.println("Error saving task: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping("/tasks/new")
    public String showNewTaskForm(Model model) {

        Task task = new Task();
        model.addAttribute("task", task);
        return "new-task";
    }




    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
