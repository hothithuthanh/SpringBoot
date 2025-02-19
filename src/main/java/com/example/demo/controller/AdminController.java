package com.example.demo.controller;

import com.example.demo.DTO.PostDTO;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/posts")
    public String posts(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<PostDTO> posts = postService.findAll(page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts.getContent());
        return "posts";
    }

    @PostMapping("/admin/posts/{id}")
    public String acceptPost(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        postDTO.setAccept(true);
        postService.save(postDTO);
        return "redirect:/admin/posts";
    }

}
