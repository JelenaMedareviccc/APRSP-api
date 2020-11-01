package com.example.project.services;

import java.util.List;

import com.example.project.dtos.responses.ReceiptResponse;
import com.example.project.models.Receipt;

public interface ReceiptService extends AbstractService<ReceiptResponse, Receipt, Integer>{
	
	List<ReceiptResponse> findAll();
	ReceiptResponse create(Receipt receipt);
	void delete (Integer id);
	ReceiptResponse update (Receipt receipt);
	ReceiptResponse findById (Integer id);
	List<ReceiptResponse> findByClientId (Integer clientId);
	double totalAmount(int id);
	List<ReceiptResponse> filterLastYear(Integer id);
	List<ReceiptResponse> filterLast365Days(Integer id);
	List<ReceiptResponse> filterBetweenTwoDates(Integer id, String startDate, String endDate);
	List<ReceiptResponse> findByAuthuserId(Integer userId);
	List<ReceiptResponse> filterByYear(Integer id, String year);
}
