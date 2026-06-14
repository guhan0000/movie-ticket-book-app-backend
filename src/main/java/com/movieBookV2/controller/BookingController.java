package com.movieBookV2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.dto.BookingRequest;
import com.movieBookV2.model.Booking;
import com.movieBookV2.service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/mba/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/create")
	public Booking createBooking(@RequestBody BookingRequest request) {
		return bookingService.lockSeatsAndCreateBooking(request);
	}
	

}
