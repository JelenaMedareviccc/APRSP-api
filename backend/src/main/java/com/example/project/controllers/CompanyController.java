package com.example.project.controllers;


import java.util.Collection;
import java.util.List;

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

import com.example.project.dtos.responses.CompanyResponse;
import com.example.project.models.Company;
import com.example.project.services.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<CompanyResponse> getCompanies(){
		return companyService.findAll();
	}

	@CrossOrigin
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CompanyResponse getCompany(@PathVariable("id") Integer id) {
		return companyService.findById(id);
	}

	
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompanyResponse addCompany (@Valid @RequestBody Company company){
		return companyService.create(company);
	}

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public CompanyResponse updateCompany(@Valid @RequestBody Company company){
			return companyService.update(company);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyResponse> deleteCompany(@PathVariable("id") Integer id){
			companyService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
	}

	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{id}")
	public List<CompanyResponse> findCompanyByAuthuser(@PathVariable Integer id){
		return companyService.findByAuthuserId(id);
	}


}
 