package com.example.project.services.implementation;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Company;
import com.example.project.repositories.CompanyRepository;
import com.example.project.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	private final CompanyRepository companyRepository;
	
	public CompanyServiceImpl (CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@Override
	public List<Company> findAll() {
		
		return companyRepository.findAll();
	}

	@Override
	public Company create(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public void delete(Integer id) {
		
		Company company = companyRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		
		companyRepository.delete(company);
	}

	@Override
	public Company update(Company company) {
		
		companyRepository.findById(company.getCompanyId())
				.orElseThrow(()-> new EntityDoesNotExist(company.getCompanyId()));
		
		return companyRepository.save(company);
	}

	@Override
	public Company findById(Integer id) {
		
		return companyRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
	}

	@Override
	public Company findByEmail(String email) {
		return companyRepository.findByEmail(email)
				.orElseThrow(()-> new EntityDoesNotExist(id));
	}

	@Override
	public List<Company> findByNameContainingIgnoreCase(String prefix) {
		return companyRepository.findByNameContainingIgnoreCase(prefix);
	}

	
}
