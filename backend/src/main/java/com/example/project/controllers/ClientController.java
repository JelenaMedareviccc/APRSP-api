package com.example.project.controllers;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
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
import com.example.project.dtos.responses.ClientsPaymentResponse;
import com.example.project.dtos.responses.LoginUser;
import com.example.project.models.Client;
import com.example.project.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	private final ClientService clientService;
	
	public ClientController (ClientService clientService) {
		this.clientService = clientService;
	}

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<ClientResponse> getClients(){
		return clientService.findAll();
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public ClientResponse getClient(@PathVariable("id") Integer id) {
		return clientService.findById(id);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/company/{companyId}")
	public List<ClientResponse> findClientByCompany(@PathVariable Integer companyId){
		return clientService.findByCompanyId(companyId); 	
	}
	
	@CrossOrigin	
	@PostMapping
	public  ResponseEntity<ClientResponse> addClient(@Valid @RequestBody Client client){
		return ResponseEntity.ok(clientService.create(client)); 
	}
	
	@CrossOrigin
	@PutMapping
	public ClientResponse updateClient(@Valid @RequestBody Client client){
			return clientService.update(client);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable("id") Integer id){
			clientService.delete(id);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{id}")
	public List<ClientResponse> getClientByUserId(@PathVariable Integer id){
		return clientService.findByAuthuserId(id);

	}
	
	@CrossOrigin
	@GetMapping("/company/{companyid}/paymentPercentage")
	@ResponseStatus(HttpStatus.OK)
	public List<ClientsPaymentResponse> getPaymentPercentagePerYear(@PathVariable("companyid") Integer id, @RequestParam("year") String year) {
		return clientService.getPaymentPercentagePerYear(id, year);
	}
	
}
