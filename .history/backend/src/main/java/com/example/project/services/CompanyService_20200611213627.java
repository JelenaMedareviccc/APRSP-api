package com.example.project.services;

import java.util.List;
import com.example.project.models.Company;

public interface CompanyService extends AbstractService<Company, Integer>{
	
	List<Company> findAll();
	Company create(Company company);
	void delete (Integer id);
	Company update (Company company);
	Company findById (Integer id);	
    List<Company> findByNameContainingIgnoreCase (String prefix);
}
