package com.movieBookV2.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingResponse {
	
	private Long bookingId;
	private String bookingRef;
	private String bookingStatus;
	private Double totalAmount;
	private String movieName;
	private String showDate;
	private String startTime;
	private String theatreName;
	private List<String> seats;
	private Integer seatCount;
	

}
