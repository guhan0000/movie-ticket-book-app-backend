package com.movieBookV2.service;

import com.movieBookV2.repository.ShowRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.dto.BookingRequest;
import com.movieBookV2.model.Booking;
import com.movieBookV2.model.BookingSeat;
import com.movieBookV2.model.BookingStatus;
import com.movieBookV2.model.SeatStatus;
import com.movieBookV2.model.Show;
import com.movieBookV2.model.ShowSeat;
import com.movieBookV2.model.User;
import com.movieBookV2.repository.BookingRepository;
import com.movieBookV2.repository.BookingSeatRepository;
import com.movieBookV2.repository.ShowSeatRepository;
import com.movieBookV2.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {
	
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private BookingSeatRepository bookingSeatRepository;
	@Autowired
	private ShowSeatRepository showSeatRepository;
	@Autowired
	private ShowRepository showRepository;
	@Autowired
	private UserRepository userRepository;
	

	BookingService(ShowRepository showRepository) {
		this.showRepository = showRepository;
	}
	
	@Transactional
	public Booking lockSeatsAndCreateBooking(BookingRequest request) {
//		validate all selected seats
		List<ShowSeat> selectedSeats = showSeatRepository.findAllById(request.getShowSeatIds());
		for(ShowSeat ss:selectedSeats) {
			if(!ss.getStatus().equals(SeatStatus.AVAILABLE)) {
				throw new RuntimeException("Seat "+ss.getSeat().getRowLabel()+ss.getSeat().getSeatNo()+" not available");
				}
			
		}
//		lock seats
		selectedSeats.forEach(ss->ss.setStatus(SeatStatus.LOCKED));
		showSeatRepository.saveAll(selectedSeats);
		
//		calculate toatl amount
		Double totalAmount = selectedSeats.stream().map(ss->ss.getPrice())
		.reduce(0.0d,(a,b)->a+b);
		User user=userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
		Show show= showRepository.findById(request.getShowId()).orElseThrow(()-> new RuntimeException("Show not found"));
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShow(show);
		booking.setStatus(BookingStatus.PENDING);
		booking.setBookedAt(LocalDateTime.now());
		booking.setBookingRef(genereateBookingRef());
		booking.setTotalAmount(totalAmount);
		Booking savedBooking = bookingRepository.save(booking);
		
//		BookingSeat records creation
		List<BookingSeat> bookingSeats = selectedSeats.stream().map(showSeat->{
			BookingSeat bookingSeat=new BookingSeat();
			bookingSeat.setBooking(savedBooking);
			bookingSeat.setShowSeat(showSeat);
			return bookingSeat;
		}).collect(Collectors.toList());
		bookingSeatRepository.saveAll(bookingSeats);
		return savedBooking;
	}
	
//	BookingRefCode Generation
	private String genereateBookingRef() {
		return UUID.randomUUID().toString().substring(0,8).toUpperCase();
		
	}

}
