package com.movieBookV2.model;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentIdLong;
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;
	private Double amount;
	@Enumerated(EnumType.STRING)
	private PaymentMethod method;	// "WALLET"
	private String transactionId;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;	// SUCCESS", "FAILED", "REFUNDED"	
	private LocalDateTime paidAt;
	
	
	

}
