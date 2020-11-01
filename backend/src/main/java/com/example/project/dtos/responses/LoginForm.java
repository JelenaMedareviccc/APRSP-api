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
public class LoginForm {
	private String username;
	private String password;
}
