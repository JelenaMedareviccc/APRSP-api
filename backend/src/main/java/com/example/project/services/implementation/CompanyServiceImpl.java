package com.example.project.services.implementation;

import com.example.project.dtos.responses.CompanyResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.exception.CustomException;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Company;
import com.example.project.repositories.CompanyRepository;
import com.example.project.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyResponse> findAll() {

        return mapToCompanyResponseList(companyRepository.findAll());
    }

    @Override
    public CompanyResponse create(Company company) {
    	try {
    		 return mapToCompanyResponse(companyRepository.save(company));
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException") && ((SQLException) e.getMostSpecificCause()).getSQLState().equals("23505"))
            	throw new  CustomException(e.getRootCause().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
            throw e;
         
        }
    	
       
    }

    @Override
    public void delete(Integer id) {

        Company company = companyRepository.findById(id)
            .orElseThrow(() -> new EntityDoesNotExist(id));

        companyRepository.delete(company);
    }

    @Override
    public CompanyResponse update(Company company) {

        companyRepository.findById(company.getCompanyId())
            .orElseThrow(() -> new EntityDoesNotExist(company.getCompanyId()));

        return mapToCompanyResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse findById(Integer id) {
    	Company  company = companyRepository.findById(id)
        .orElseThrow(() -> new EntityDoesNotExist(id));
    	
        return mapToCompanyResponse(company);
}

    @Override
    public CompanyResponse findByEmail(String email) {
        return mapToCompanyResponse(companyRepository.findByEmail(email));
    }

    @Override
    public List<CompanyResponse> findByAuthuserId(Integer id) {
        List<Company> companies = companyRepository.findByAuthuserId(id);

        return mapToCompanyResponseList(companies);
    }
    

    private List<CompanyResponse> mapToCompanyResponseList(List<Company> companies) {
        return companies.stream().map(this::mapToCompanyResponse).collect(Collectors.toList());
    }

    private CompanyResponse mapToCompanyResponse(Company company) {
        return CompanyResponse.builder()
            .companyId(company.getCompanyId())
            .name(company.getName())
            .accountNumber(company.getAccountNumber())
            .contact(company.getContact())
            .address(company.getAddress())
            .email(company.getEmail())
            .pib(company.getPib())
            .currency(company.getCurrency())
            .authuser(company.getAuthuser())
            .build();
    }



}
