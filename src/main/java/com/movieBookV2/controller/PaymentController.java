package com.movieBookV2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.dto.PaymentRequest;
import com.movieBookV2.dto.PaymentResponse;
import com.movieBookV2.service.PaymentService;

@RestController
@RequestMapping("/api/mba/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/pay")
	public PaymentResponse processPayment(@RequestBody PaymentRequest request) {
		return paymentService.processPayment(request);
	}
	@PutMapping("refund/{bookingId}")
	public PaymentResponse refundPayment(@PathVariable Long bookingId) {
		return paymentService.refundPayment(bookingId);
	}
	@GetMapping("/details/booking/{bookingId}")
	public PaymentResponse getPaymentDetails(@PathVariable Long bookingId) {
		return paymentService.getPaymentDetails(bookingId);
	}
}
