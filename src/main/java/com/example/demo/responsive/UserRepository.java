package com.example.demo.responsive;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByUsername(String username);
     Page<User> findAll(Pageable pageable);
//    Optional<User> existsByUsername(String username);
     User findByEmail(String email);
}

