package com.example.project.services.implementation;

import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.project.services.CompanyService;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Company;
import java.util.Collections;

@Service
public class CompanyDetailsServiceImpl implements UserDetailsService {
    private final CompanyService companyService;

    public CompanyDetailsServiceImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityDoesNotExist {
        Company user = companyService.findByEmail(username);
        System.out.println("USER12345 " + user);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );
    }}