package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{postId}/comments")
    public String comments(@PathVariable Long postId, Model model) {
        List<CommentDTO> comments = commentService.getCommentByPostId(postId);
        model.addAttribute("comments", comments);
        return "PostDetail";
    }
    @PostMapping("/article/{id}/AddComment")
    public String addComment(@PathVariable Long id, Model model) {
        return "PostDetail";
    }
}
