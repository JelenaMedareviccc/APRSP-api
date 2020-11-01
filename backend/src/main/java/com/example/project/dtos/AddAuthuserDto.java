package com.example.project.dtos;

import java.util.List;

import com.example.project.models.Company;
import com.example.project.models.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a DTO (Data Transfer Object) for User model and it is used for adding users into the database
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddAuthuserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String contact;
    private Role role;
    private boolean enabled;

}
