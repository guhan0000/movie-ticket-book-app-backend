package com.movieBookV2.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingRequest {
	
	private Long userId;
	private Long showId;
	private List<Long>showSeatIds;
}
