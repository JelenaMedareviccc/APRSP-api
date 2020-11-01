package com.example.project.services;

import java.util.List;

import com.example.project.dtos.responses.PaymentResponse;
import com.example.project.models.Payment;

public interface PaymentService extends AbstractService<PaymentResponse, Payment, Integer>{
	
	List<PaymentResponse> findAll();
	PaymentResponse create(Payment payment);
	void delete (Integer id);
	PaymentResponse update (Payment payment);
	PaymentResponse findById (Integer id);	
	List<PaymentResponse> findByReceiptId(Integer receiptId);
	List<PaymentResponse> findByAuthuserId(Integer userId);
	List<PaymentResponse> findByCompanyIdForLast365(Integer companyId);
	

}
