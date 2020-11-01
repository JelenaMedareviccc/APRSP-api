package com.example.project.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dtos.AddAuthuserDto;
import com.example.project.dtos.GetAuthuserDto;
import com.example.project.dtos.responses.CompanyResponse;
import com.example.project.dtos.responses.LoginForm;
import com.example.project.dtos.responses.LoginUser;
import com.example.project.models.Client;
import com.example.project.models.Company;
import com.example.project.models.Receipt;
import com.example.project.models.Authuser;
import com.example.project.services.CompanyService;
import com.example.project.services.UserService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("user")
public class UserController {
	
	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@CrossOrigin
	@PostMapping("/signup")
	public ResponseEntity<Boolean> register (@Valid @RequestBody AddAuthuserDto user){
		//LoginUser logUser =  userService.signup(user);
		//LoginUser loguser = new LoginUser(user.getUsername(), newToken.getAsString("token"));
		System.out.println("USAO U REGISTER");
		boolean sentMail = userService.signup(user);
		
		return ResponseEntity.ok(sentMail);
	}
	

	@CrossOrigin
	@PostMapping("/signin")
	public ResponseEntity<LoginUser> login (@Valid @RequestBody LoginForm login){
		LoginUser logUser = userService.signin(login.getUsername(), login.getPassword());
		return ResponseEntity.ok(logUser);
	}
	
	@CrossOrigin
	@GetMapping("/userId/{id}")
	public ResponseEntity<GetAuthuserDto> getUser(@PathVariable ("id") Integer id) {
		return ResponseEntity.ok(userService.findById(id));	
	}
	

	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public GetAuthuserDto updateUser(@Valid @RequestBody GetAuthuserDto user){
			return userService.update(user);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Integer id){
			userService.delete(id);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{username}")
	public String refreshToken(@PathVariable("username") String username){
			return userService.refresh(username);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getChangePasswordCode")
	public boolean getChangePasswordCode(@RequestParam("username") String username){
			return userService.identifyUser(username);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/changePassword")
	public LoginUser changedPassword(@Valid @RequestBody String password, @RequestParam("token") String token ){
			return userService.changePassword(password, token);
	} 
	
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/comfirmRegistration")
	public LoginUser comfirmRegistration(@RequestParam("token") String token){
			System.out.println("USAO OVDJE");
			return userService.comfirmRegistration(token);
	} 
	



	
}
