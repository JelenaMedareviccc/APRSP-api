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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dtos.responses.ClientResponse;
import com.example.project.dtos.responses.ReceiptResponse;
import com.example.project.models.Receipt;
import com.example.project.services.ReceiptService;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

	private final ReceiptService receiptService;
	
	public ReceiptController (ReceiptService receiptService) {
		this.receiptService = receiptService;
	}
	

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<ReceiptResponse> getReceipts(){
		return receiptService.findAll();
	}
	

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/receiptId/{id}")
	public ReceiptResponse getReceipt(@PathVariable ("id") Integer id) {
		return receiptService.findById(id);	
	}
	

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/client/{clientId}")
	public List<ReceiptResponse> findReceiptByClient (@PathVariable Integer clientId ){
		
		return receiptService.findByClientId(clientId);
		
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ReceiptResponse addReceipt(@Valid @RequestBody Receipt receipt){
			return receiptService.create(receipt);
	}
	

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public ReceiptResponse updateReceipt(@Valid @RequestBody Receipt receipt){
			return receiptService.update(receipt);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable("id") Integer id){
			receiptService.delete(id);
	}

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}/filteredReceiptsLastYear")
	public List<ReceiptResponse> filterReceiptLastYear(@PathVariable("id") Integer id) {
		return receiptService.filterLastYear(id);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}/filteredReceiptsLast365Days")
	public List<ReceiptResponse> filterReceiptLast365Days(@PathVariable("id") Integer id) {
		return receiptService.filterLast365Days(id);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("{id}/filterBetweenTwoDates")
	public List<ReceiptResponse> filterReceiptsBetweenTwoDates(@PathVariable("id") Integer id, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		return receiptService.filterBetweenTwoDates(id, startDate, endDate);

	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("{id}/filteredReceiptForSelectedYear")
	public List<ReceiptResponse> filteredReceiptForSelectedYear(@PathVariable("id") Integer id, @RequestParam("year") String year) {
		return receiptService.filterByYear(id, year);

	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{id}")
	public List<ReceiptResponse> getClientByUserId(@PathVariable Integer id){
		return receiptService.findByAuthuserId(id);

	}
	
	
	
	
}