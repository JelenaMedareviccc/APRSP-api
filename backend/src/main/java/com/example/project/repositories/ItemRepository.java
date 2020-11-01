package com.example.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.models.Item;
import com.example.project.models.Payment;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	List<Item> findByReceiptReceiptId (Integer receiptId);	
    List<Item> findAllByReceiptClientCompanyAuthuserId( Integer id);

}
