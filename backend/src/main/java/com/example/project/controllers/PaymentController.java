package com.example.project.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dtos.responses.ClientResponse;
import com.example.project.dtos.responses.PaymentResponse;
import com.example.project.models.Payment;
import com.example.project.services.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<PaymentResponse> getPayments() {
		return paymentService.findAll();
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/paymentId/{id}")
	public PaymentResponse getPayment (@PathVariable("id") Integer id) {
		return paymentService.findById(id);
	}
	
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/receipt/{receiptId}")
	public List<PaymentResponse> findPaymentByReceipt (@PathVariable Integer receiptId){
		return paymentService.findByReceiptId(receiptId);
	
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PaymentResponse addPayment(@Valid @RequestBody Payment payment){
		return paymentService.create(payment);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public PaymentResponse updatePayment(@Valid @RequestBody Payment payment){
		return paymentService.update(payment);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deletePayment(@PathVariable("id") Integer id){
		paymentService.delete(id);
		
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{id}")
	public List<PaymentResponse> getPaymentByUserId(@PathVariable Integer id){
		return paymentService.findByAuthuserId(id);

	}
	
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/company/{companyId}/paymentsForLast365Days")
	public List<PaymentResponse> getPaymentByCompanyFor365(@PathVariable Integer companyId){
		return paymentService.findByCompanyIdForLast365(companyId);

	}
}
