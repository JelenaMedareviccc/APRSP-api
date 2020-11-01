package com.example.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.models.Authuser;
import com.example.project.models.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	List<Client> findByCompanyCompanyId(Integer companyId);
	List<Client> findByNameContainingIgnoreCase(String name);
	
    List<Client> findAllByCompanyAuthuserId( Integer id);

	

}
