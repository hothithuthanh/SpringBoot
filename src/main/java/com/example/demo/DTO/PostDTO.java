package com.example.demo.DTO;

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
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Date createdAt ;
    private String author;
    private boolean isAccept;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.author = post.getAuthor().getUsername();
        this.isAccept = post.isAccept();
    }

    public Post toPost(User user){
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createdAt(this.createdAt)
                .isAccept(this.isAccept)
                .author(user)
                .build();
    }
}
