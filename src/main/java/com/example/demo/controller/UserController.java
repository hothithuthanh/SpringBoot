package com.example.demo.controller;

import org.springframework.ui.Model;

import com.example.demo.DTO.RegisterDTO;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
   

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginRequest") LoginRequest loginRequest,  Model model) {
       
        if (userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            model.addAttribute("loginRequest", new LoginRequest());
            return "redirect:/home";
        }
        else
            return "login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDto", registerDTO);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerDto") RegisterDTO registerDTO, Model model,
            BindingResult result) {
        try {
            userService.saveUser(registerDTO);
            model.addAttribute("registerDto", new RegisterDTO());
            model.addAttribute("success", true);
            }
                 catch (Exception e) {
            result.addError(
                    new FieldError("registerDto", "username", e.getMessage()));
        }
            
        return "register";
    }

    @GetMapping("/profile/update")
    public String updateProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "updateProfile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Validated @ModelAttribute("user") RegisterDTO user, Model model) {
        userService.saveUser(user);
        return "myprofile";
    }
}

