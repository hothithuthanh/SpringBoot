package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
   

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginRequest") LoginRequest loginRequest,  Model model) {
       
        if (userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            model.addAttribute("loginRequest", new LoginRequest());
            return "redirect:/home";
        }
        else
            return "user/login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDto", registerDTO);
        model.addAttribute("success", false);
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerDto") RegisterDTO registerDTO, Model model,
            BindingResult result) {
        try {
            userService.saveUser(registerDTO, "USER");
            model.addAttribute("registerDto", new RegisterDTO());
            model.addAttribute("success", true);
            }
                 catch (Exception e) {
            result.addError(
                    new FieldError("registerDto", "username", e.getMessage()));
        }
            
        return "user/register";
    }

    @GetMapping("/profile")
    public String show(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "user/myprofile";
    }

    @PostMapping("/profile")
    public String updateProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "user/updateProfile";
    }

    @PostMapping("/profile/update")
    public ResponseEntity<Map<String, Object>> updateProfile(@Validated @RequestBody RegisterDTO user) {
        try {
            userService.saveUser(user, "USER");
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/deleteUser/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
    @PostMapping("/checkPassword")
    public ResponseEntity<Map<String, Boolean>> checkPassword(@RequestBody Map<String, String> request, Principal principal) {
        boolean isValid = userService.updatePassword(principal.getName(), request.get("password_old"));
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", isValid);
        return ResponseEntity.ok(response);
    }

}

