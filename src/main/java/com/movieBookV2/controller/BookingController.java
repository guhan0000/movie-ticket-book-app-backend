package com.movieBookV2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.dto.BookingRequest;
import com.movieBookV2.dto.BookingResponse;
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
	@PostMapping("/confirm/{bookingId}")
	public Booking confirmBooking(@PathVariable Long bookingId) {
		return bookingService.confirmBooking(bookingId);
	}
	@PostMapping("/cancel/{bookingId}")
	public Booking cancelBooking(@PathVariable Long bookingId) {
		return bookingService.cancelBooking(bookingId);
	}
	@GetMapping("/get/{bookingId}")
	public BookingResponse getBookingDetails(@PathVariable Long bookingId) {
		return bookingService.getBookingDetails(bookingId);
	}
	@GetMapping("/get/user/{userId}/all")
	public List<Booking> getUserBookings(@PathVariable Long userId){
		return bookingService.getUserBookings(userId);
	}
	

}
