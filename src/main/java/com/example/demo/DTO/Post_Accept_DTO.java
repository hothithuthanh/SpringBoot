package com.example.demo.DTO;

import com.example.demo.model.Post;
import com.example.demo.model.Post_Accept;
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
public class Post_Accept_DTO {
    private Long id;
    private String title;
    private String content;
    private Date createdAt ;
    private String author;

    public Post_Accept_DTO(Post_Accept post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.author = post.getAuthor().getUsername();
    }



    public Post_Accept toPost_Accept(User user){
        return Post_Accept.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createdAt(this.createdAt)
                .author(user)
                .build();
    }
}
