package com.example.project.services;

import java.util.List;

import com.example.project.dtos.responses.ClientResponse;
import com.example.project.dtos.responses.ClientsPaymentResponse;
import com.example.project.models.Client;

public interface ClientService extends AbstractService<ClientResponse, Client, Integer>{
	
	List<ClientResponse> findAll();
	ClientResponse create(Client client);
	void delete (Integer id);
	ClientResponse update (Client client);
	ClientResponse findById (Integer id);
    List<ClientResponse> findByNameContainingIgnoreCase (String prefix);
    List<ClientResponse> findByCompanyId (Integer companyId);
    List<ClientResponse> findByAuthuserId(Integer userId);
    List<ClientsPaymentResponse> getPaymentPercentagePerYear(Integer companyid, String year);
}
