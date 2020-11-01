package com.example.project.repositories;

import com.example.project.enums.RoleEnum;
import com.example.project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findByName(RoleEnum name);

}