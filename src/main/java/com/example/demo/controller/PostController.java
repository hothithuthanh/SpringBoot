package com.example.demo.controller;

import com.example.demo.DTO.PostDTO;
import com.example.demo.DTO.Post_Accept_DTO;
import com.example.demo.service.PostService;
import com.example.demo.service.Post_AcceptService;
import com.example.demo.service.UserService;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private Post_AcceptService post_acceptService;

    @Autowired
    private UserService userService;
    @GetMapping("/article")
    public String addPost(Model model) {
        model.addAttribute("article", new PostDTO());
        return "AddPost";
    }

    @PostMapping("/article")
    public String addPost(@Validated @ModelAttribute("article") Post_Accept_DTO postDTO, Principal principal) {
        postDTO.setCreatedAt(new Date());
        postDTO.setAuthor(principal.getName());
        System.out.println(principal.getName());
        post_acceptService.save(postDTO);
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Post_Accept_DTO> posts = post_acceptService.getPosts(page);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "home";
    }

    @GetMapping("/myArticle")
    public String getPost(Model model, Principal principal, @RequestParam(defaultValue = "1") int page) {
        Long id = userService.getUserIdByPrincipal(principal);
        System.out.println("ID_USER: " + id);
        Page<Post_Accept_DTO> posts = post_acceptService.getPostsByUserId(page, id);
        System.out.println("Post: " + posts.getContent());
        model.addAttribute("article", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "myArticle";
    }
    @GetMapping("/detailArticle/{id}")
    public String detailPost(Model model, @PathVariable("id") Long id) {
        Post_Accept_DTO post_accept_dto = post_acceptService.getPostById(id);
        model.addAttribute("article", post_accept_dto);
        return "PostDetail";
    }
    @GetMapping("/updateArticle/{id}") 
    public String updatePost(Model model, @PathVariable("id") Long id) {
        Post_Accept_DTO post_accept_dto = post_acceptService.getPostById(id);
        model.addAttribute("article", post_accept_dto);
        return "updatePost";
    }

    @PostMapping("/updateArticle/{id}")
    public String updatePost(@Validated @ModelAttribute("article") Post_Accept_DTO post_accept_dto, @PathVariable("id") Long id, Principal principal) {
        post_accept_dto.setAuthor(principal.getName());
        post_accept_dto.setCreatedAt(new Date());
        System.out.println(post_accept_dto.getId() + " " + post_accept_dto.getAuthor());
        post_acceptService.save(post_accept_dto);
        return "redirect:/home";
    }

    @PostMapping("/deleteArticle/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        if (post_acceptService.deletePostById(id)) return "redirect:/home";
        return "redirect:/home";
    }
    
}
/*
*  @Autowired
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
        System.out.println(principal.getName());
//        postService.save(postDTO);
//        return "redirect:/home";
        return "redirect:/article";
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

    @PostMapping("/deleteArticle/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        if (postService.deletePostById(id)) return "redirect:/home";
        return "redirect:/home";
    }

* */