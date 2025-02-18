package com.example.demo.service;

import com.example.demo.DTO.PostDTO;
import com.example.demo.DTO.Post_Accept_DTO;
import com.example.demo.model.Post;
import com.example.demo.model.Post_Accept;
import com.example.demo.model.User;
import com.example.demo.responsive.PostRepository;
import com.example.demo.responsive.Post_AcceptRepository;
import com.example.demo.responsive.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Post_AcceptService {
    @Autowired
    private Post_AcceptRepository post_acceptRepository;

    @Autowired
    private UserRepository userRepository;

    public Post_Accept save(Post_Accept_DTO post_accept_DTO) {
        User user = userRepository.findByUsername(post_accept_DTO.getAuthor());
        Post_Accept post = post_accept_DTO.toPost_Accept(user);
        return post_acceptRepository.save(post);
    }

    public List<Post_Accept_DTO> getAll() {
        return post_acceptRepository.findAll().stream().map(Post_Accept_DTO::new).toList();
    }

    public Page<Post_Accept_DTO> getPosts(int page) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return post_acceptRepository.findAll(pageable).map(Post_Accept_DTO::new);
    }

    public Post_Accept_DTO getPostById(Long id) {
        return post_acceptRepository.findById(id).map(Post_Accept_DTO::new).orElse(null);
    }

    public Page<Post_Accept_DTO> getPostsByUserId(int page, Long id) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return post_acceptRepository.findAllByAuthor_Id(id, pageable).map(Post_Accept_DTO::new);
    }

    public Boolean deletePostById(Long id) {
        if (post_acceptRepository.existsById(id)) {
            post_acceptRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Post_Accept_DTO> findAll(){
        return post_acceptRepository.findAll().stream().map(Post_Accept_DTO::new).toList();
    }
}
