package com.movieBookV2.dto;

import com.movieBookV2.model.PaymentMethod;

import lombok.Data;

@Data
public class PaymentRequest {
	private Long bookingId;
	private String transactionId;

}
