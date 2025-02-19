package com.example.demo.service;

import com.example.demo.DTO.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.responsive.PostRepository;
import com.example.demo.responsive.UserRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post save(PostDTO postDTO) {
        User user = userRepository.findByUsername(postDTO.getAuthor());
        Post post = postDTO.toPost(user);
        return postRepository.save(post);
    }

    public List<PostDTO> getAll() {
        return postRepository.findAll().stream().map(PostDTO::new).toList();
    }

    public Page<PostDTO> getPosts(int page) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return postRepository.findAllByisAccept(true, pageable).map(PostDTO::new);
    }
    public Page<PostDTO> findAll(int page){
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return postRepository.findAll(pageable).map(PostDTO::new);
    }
    public PostDTO getPostById(Long id) {
        return postRepository.findById(id).map(PostDTO::new).orElse(null);
    }

    public Page<PostDTO> getPostsByUserId(int page, Long id) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return postRepository.findAllByAuthor_IdAndIsAccept(id,true, pageable).map(PostDTO::new);
    }

    public Boolean deletePostById(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
