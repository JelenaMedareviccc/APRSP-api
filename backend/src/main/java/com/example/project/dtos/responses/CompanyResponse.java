package com.example.project.dtos.responses;

import com.example.project.models.Authuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Integer companyId;

    private String name;

    private String pib;

    private String address;

    private String contact;

    private String email;

    private String accountNumber;
    
    private String currency;
    
    private Authuser authuser;
}
