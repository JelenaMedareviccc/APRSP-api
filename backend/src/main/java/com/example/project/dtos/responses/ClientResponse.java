package com.example.project.dtos.responses;

import com.example.project.models.Authuser;
import com.example.project.models.Company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
	
    private Integer clientId;

    private String name;

    private String clientRegNumber;

    private String address;

    private String contact;

    private String email;

    private String accountNumber;
        
    private Company company;



}
