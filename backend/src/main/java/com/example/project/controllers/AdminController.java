package com.example.project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dtos.GetAuthuserDto;
import com.example.project.services.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<GetAuthuserDto> getUsers(){
		return userService.findAll();
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/changeUserToAdmin/{id}")
    public GetAuthuserDto changeUserToAdmin(@PathVariable("id")Integer id) {
		return userService.changeUserToAdmin(id);
	}
   
}
