package com.example.demo.responsive;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByUsername(String username);
//    Optional<User> existsByUsername(String username);
}

