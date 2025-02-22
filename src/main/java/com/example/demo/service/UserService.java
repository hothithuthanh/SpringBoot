package com.example.demo.service;

import com.example.demo.DTO.PostDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.responsive.RoleRepository;
import com.example.demo.responsive.UserRepository;

import java.security.Principal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public User saveUser(RegisterDTO registerDTO, String roleName) {
    
        Role role = roleRepository.findByNameRole(roleName);

            var bCryptEncoder = new BCryptPasswordEncoder();
            User newUser = new User();
            newUser.setUsername(registerDTO.getUsername());
            newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            newUser.setRole(role);
            newUser.setEmail(registerDTO.getEmail());
           return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getNameRole())
                .build();
    }
    
    public Long getUserIdByPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        return user != null ? user.getId() : null;
    }

    public RegisterDTO getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        return new RegisterDTO(user);
    }
    public Page<RegisterDTO> findAll(int page){
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return userRepository.findAll(pageable).map(RegisterDTO::new);
    }
    public Boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
