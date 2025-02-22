package com.example.demo.controller;

import com.example.demo.DTO.PostDTO;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

import java.security.Principal;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    @GetMapping("/article")
    public String addPost(Model model) {
        model.addAttribute("article", new PostDTO());
        return "AddPost";
    }

    @PostMapping("/article")
    public String addPost(@Validated @ModelAttribute("article") PostDTO postDTO, Principal principal) {
        postDTO.setCreatedAt(new Date());
        postDTO.setAuthor(principal.getName());
        postDTO.setAccept(false);
        System.out.println(principal.getName());
        postService.save(postDTO);
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<PostDTO> posts = postService.getPosts(page);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "home";
    }

    @GetMapping("/myArticle")
    public String getPost(Model model, Principal principal, @RequestParam(defaultValue = "1") int page) {
        Long id = userService.getUserIdByPrincipal(principal);
        System.out.println("ID_USER: " + id);
        Page<PostDTO> posts = postService.getPostsByUserId(page, id);
        System.out.println("Post: " + posts.getContent());
        model.addAttribute("article", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "myArticle";
    }
    @GetMapping("/detailArticle/{id}")
    public String detailPost(Model model, @PathVariable("id") Long id) {
        PostDTO postDTO = postService.getPostById(id);
        model.addAttribute("article", postDTO);
        return "PostDetail";
    }
    @GetMapping("/updateArticle/{id}")
    public String updatePost(Model model, @PathVariable("id") Long id) {
        PostDTO postDTO = postService.getPostById(id);
        model.addAttribute("article", postDTO);
        return "updatePost";
    }

    @PostMapping("/updateArticle/{id}")
    public String updatePost(@Validated @ModelAttribute("article") PostDTO postDTO, @PathVariable("id") Long id, Principal principal) {
        postDTO.setAuthor(principal.getName());
        postDTO.setCreatedAt(new Date());
        System.out.println(postDTO.getId() + " " + postDTO.getAuthor());
        postService.save(postDTO);
        return "redirect:/home";
    }

    @GetMapping("/deleteArticle/{id}")
    public String deletePost(@PathVariable("id") Long id, HttpServletRequest request) {
        postService.deletePostById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/posts";
        } else {
            return "redirect:/home";
        }
    }
    
}
