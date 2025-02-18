package com.example.demo.DTO;

import com.example.demo.model.User;

public class RegisterDTO {
     private String username;
    private String email;
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
    public String getPassword() {
        return password;
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

    }
}
