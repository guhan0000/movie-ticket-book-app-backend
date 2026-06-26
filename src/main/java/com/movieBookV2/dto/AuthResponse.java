package com.movieBookV2.dto;

import com.movieBookV2.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthResponse {
	private String token;
	private Long userId;
	private String name;
	private String email;
	private Role role;
	

}
