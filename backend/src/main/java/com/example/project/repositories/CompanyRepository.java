package com.example.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.models.Client;
import com.example.project.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findByEmail (String email);
	List<Company> findByAuthuserId(Integer id);


}
