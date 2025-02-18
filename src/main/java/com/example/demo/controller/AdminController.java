package com.example.demo.controller;

import com.example.demo.DTO.PostDTO;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @PostMapping("/admin/posts/accept")
    public String acceptPost(Model model, PostDTO postDTO) {
        postService.save(postDTO);
        return "redirect:/admin/posts";
    }
}
