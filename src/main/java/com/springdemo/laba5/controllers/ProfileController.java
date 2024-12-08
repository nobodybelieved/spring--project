package com.springdemo.laba5.controllers;

import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.services.EmailService;
import com.springdemo.laba5.services.UserServiceIMPL;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
@Controller
public class ProfileController {

    @Value("${upload.dir}")
    private String uploadDir;

    private final EmailService emailService;
    private final UserServiceIMPL userService;

    @Autowired
    public ProfileController( EmailService emailService,UserServiceIMPL userService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @PostMapping("/profile/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "User not found.");
            return "redirect:/profile";
        }
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No file selected.");
            return "redirect:/profile";
        }

        try {
            Path path = Paths.get("C:/Users/megam/IdeaProjects/laba5/src/main/resources/static/images");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName = file.getOriginalFilename();
            File targetFile = new File(path.toFile(), fileName);

            if (targetFile.exists()) {
                String imageLink = "/images/" + fileName;
                user.setImageLink(imageLink);
            } else {
                file.transferTo(targetFile);
                String imageLink = "/images/" + fileName;
                user.setImageLink(imageLink);
            }

            userService.save_image(user);

            String subject = "Ваша аватарка изменена";
            String text = "Здравствуйте! Ваша аватарка была успешно обновлена.";
            emailService.sendEmail(user.getEmail(), subject, text);

            redirectAttributes.addFlashAttribute("message", "File uploaded and email sent successfully.");
            return "redirect:/profile";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload the file.");
            return "redirect:/profile";
        }
    }



    @GetMapping("/profile/change-password")
    public String changePasswordPage() {
        return "change-password";
    }


    @PostMapping("/profile/send-code")
    public String sendCode(Principal principal, HttpSession session, Model model) {
        String email = principal.getName();
        String code = generateRandomCode();
        session.setAttribute("confirmationCode", code);
        emailService.sendCode(email, code);
        model.addAttribute("codeSent", true);
        return "change-password";
    }

    @PostMapping("/profile/verify-code")
    public String verifyCode(@RequestParam("code") String code, HttpSession session, Model model) {
        String storedCode = (String) session.getAttribute("confirmationCode");
        if (storedCode != null && storedCode.equals(code)) {
            model.addAttribute("codeVerified", true);
        } else {
            model.addAttribute("codeError", true);
        }
        return "change-password";
    }

    @PostMapping("/profile/process-change-password")
    public String processChangePassword(@RequestParam("newPassword") String newPassword,
                                        Principal principal,
                                        HttpSession session,
                                        Model model) {
        String email = principal.getName();
        userService.updatePassword(email, newPassword);
        session.removeAttribute("confirmationCode");
        return "redirect:/login";
    }

    private String generateRandomCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

}
