package com.example.demo.service;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.responsive.CommentRepository;
import com.example.demo.responsive.PostRepository;
import com.example.demo.responsive.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment saveComment(CommentDTO commentDTO) {
        User user = userRepository.findByUsername(commentDTO.getAuthor());
        Post post = postRepository.findById(commentDTO.getPostId()).get();
        Comment comment = commentDTO.ToComment(user, post);
        return commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).get();
        List<Comment> comments = commentRepository.findByPost(post);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOS.add(new CommentDTO(comment));
        }
        return commentDTOS;
    }

    public Boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
