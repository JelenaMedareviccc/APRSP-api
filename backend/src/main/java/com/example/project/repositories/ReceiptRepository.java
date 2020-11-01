package com.example.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.models.Client;
import com.example.project.models.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

	List<Receipt> findByClientClientId (Integer clientId);
    List<Receipt> findAllByClientCompanyAuthuserId( Integer id);

}
