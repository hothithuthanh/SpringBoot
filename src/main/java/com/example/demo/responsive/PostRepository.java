package com.example.demo.responsive;

import com.example.demo.model.Post;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
     Optional<Post> findById(Long id);

     Page<Post> findAll(Pageable pageable);
     Page<Post> findAllByAuthor_Id(Long id, Pageable pageable);
     Page<Post> findAllByisAccept(Boolean isAccept, Pageable pageable);
     Page<Post> findAllByAuthor_IdAndIsAccept(Long id, Boolean isAccept, Pageable pageable);
}
