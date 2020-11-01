package com.example.project.services;

import com.example.project.dtos.responses.CompanyResponse;
import java.util.List;
import com.example.project.models.Company;

public interface CompanyService extends AbstractService<CompanyResponse, Company, Integer> {

    List<CompanyResponse> findAll();

    CompanyResponse create(Company company);

    void delete(Integer id);

    CompanyResponse update(Company company);

    CompanyResponse findById(Integer id);

    CompanyResponse findByEmail(String email);

    List<CompanyResponse> findByAuthuserId(Integer id);
}
