package com.example.project.repositories;

import com.example.project.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
   
    ConfirmationToken findByConfirmationToken(String confirmationToken);

}