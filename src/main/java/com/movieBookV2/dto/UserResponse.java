package com.movieBookV2.dto;

import lombok.Data;

@Data
public class UserResponse {
	
	private Long userId;
	private String custName;
	private String email;
	private Double walletBalance;

}
