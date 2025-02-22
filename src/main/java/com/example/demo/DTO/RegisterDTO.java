package com.example.demo.DTO;

import com.example.demo.model.User;
import lombok.Getter;

public class RegisterDTO {
    private Long id;
    private String nameRole;
     private String username;
    private String email;
    @Getter
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public RegisterDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public RegisterDTO() {
    }
    public RegisterDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.id = user.getId();
        this.nameRole = user.getRole().getNameRole();
    }
}
