package com.movieBookV2.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.movieBookV2.model.BookingStatus;
import com.movieBookV2.model.PaymentStatus;

import lombok.Data;

@Data
public class PaymentResponse {
	
	private String transactionId;
	private PaymentStatus paymentStatus;
	private String bookingRef;
	private BookingStatus bookingStatus;
	private Double totalAmount;
	private String movieName;
    private String theatreName;
    private String showDate;
    private String startTime;
    private List<String> seats;
    private Integer seatCount;
    private LocalDateTime paidAt;
}
