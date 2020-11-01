package com.example.project.services;

import java.util.List;

import com.example.project.dtos.responses.ItemResponse;
import com.example.project.dtos.responses.PaymentResponse;
import com.example.project.models.Item;

public interface ItemService extends AbstractService<ItemResponse, Item, Integer>{
	
	List<ItemResponse> findAll();
	ItemResponse create(Item item);
	void delete (Integer id);
	ItemResponse update (Item item);
	ItemResponse findById (Integer id);	
	List<ItemResponse> findByReceiptId(Integer receiptId);
	List<ItemResponse> findByAuthuserId(Integer userId);
}

