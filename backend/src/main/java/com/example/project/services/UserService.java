package com.example.project.services;

import com.example.project.dtos.AddAuthuserDto;
import com.example.project.dtos.GetAuthuserDto;
import com.example.project.dtos.responses.LoginUser;
import com.example.project.models.Client;
import com.example.project.models.Company;
import com.example.project.models.Receipt;


import net.minidev.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This interface is a service that allows us to define all the methods our user service implementation must have
 */
public interface UserService {
 
    boolean signup(AddAuthuserDto user);
    LoginUser signin(String username, String password);
	GetAuthuserDto findById (Integer id);
    List<GetAuthuserDto> findAll();
    GetAuthuserDto update(GetAuthuserDto userDto);
    void delete(Integer id);
    LoginUser changePassword(String username, String password);
    GetAuthuserDto changeUserToAdmin(Integer id);
    String refresh(String username);
    LoginUser comfirmRegistration(String token);
    
    boolean identifyUser(String username);


  
}

