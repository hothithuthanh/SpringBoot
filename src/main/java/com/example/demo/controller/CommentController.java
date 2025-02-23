package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.PostDTO;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @GetMapping("/article/comments/{postId}")
    public String comments(@PathVariable Long postId, Model model,@ModelAttribute CommentDTO commentDTO) {
        List<CommentDTO> comments = commentService.getCommentByPostId(postId);
        PostDTO postDTO = postService.getPostById(postId);
        model.addAttribute("article", postDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("commentDTO", commentDTO);
        return "Post/PostDetail";
    }
    @PostMapping("/article/AddComment/{id}")
    public String addComment(@PathVariable Long id, Model model, @ModelAttribute CommentDTO commentDTO, Principal principal) {
        PostDTO postDTO = postService.getPostById(id);
        model.addAttribute("article", postDTO);
        commentDTO.setPostId(postDTO.getId());
        commentDTO.setAuthor(principal.getName());
        commentDTO.setCreatedAt(new Date());
        commentService.saveComment(commentDTO);
        return "redirect:/article/comments/" + id;
    }
}
