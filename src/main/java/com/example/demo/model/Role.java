package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    private int id;

    private String nameRole;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<User> users;
}
