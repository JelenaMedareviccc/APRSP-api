package com.example.project.dtos.responses;

import com.example.project.models.Authuser;
import com.example.project.utils.ApplicationConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUser {
	
	private Integer id;

	private String username;
	private String token;
	private Integer expiration = ApplicationConstants.expiration;

	public LoginUser(Integer userId, String username, String token) {
		super();
		this.username = username;
		this.token = token;
		this.id = userId;
	}


}
