package com.example.demo.DTO;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String content;
    private Date createdAt;
    private String author;
    private Long postId;

    public CommentDTO(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.author = comment.getAuthor().getUsername();
        this.postId = comment.getPost().getId();
    }

    public Comment ToComment(User user, Post post) {
        return Comment.builder()
                .content(this.content)
                .createdAt(this.createdAt)
                .author(user)
                .post(post)
                .build();
    }
}
