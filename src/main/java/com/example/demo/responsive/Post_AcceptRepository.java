package com.example.demo.responsive;


import com.example.demo.model.Post;
import com.example.demo.model.Post_Accept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Post_AcceptRepository extends JpaRepository<Post_Accept, Long> {
    Optional<Post_Accept> findById(Long id);

    Page<Post_Accept> findAll(Pageable pageable);
    Page<Post_Accept> findAllByAuthor_Id(Long id, Pageable pageable);
}
