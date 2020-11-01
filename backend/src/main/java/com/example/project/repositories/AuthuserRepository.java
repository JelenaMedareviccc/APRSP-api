package com.example.project.repositories;

import com.example.project.models.Authuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;


public interface AuthuserRepository extends JpaRepository<Authuser, Integer> {
    
    Collection<Authuser> findByusernameContainingIgnoreCase(String username);

   Authuser findByUsername(String username);
    
    Authuser  findByid(Integer id);
  
    Authuser findByEmailIgnoreCase(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
  

    @Modifying
    @Query(value = "update authuser set password = ?1 where id = ?2", nativeQuery = true)
    void executeUpdate(String password, Integer id);
}