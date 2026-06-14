package com.movieBookV2.service;

import com.movieBookV2.repository.ShowRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.dto.BookingRequest;
import com.movieBookV2.dto.BookingResponse;
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
		return "BK"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
		
	}
//	confirm Booking after Payment
	@Transactional
	public Booking confirmBooking(Long bookingId) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found"));
		List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking_BookingId(bookingId);
		bookingSeats.forEach(bs->bs.getShowSeat().setStatus(SeatStatus.BOOKED));
		List<ShowSeat> bookedShowSeats = bookingSeats.stream()
				.map(bs->bs.getShowSeat())
				.collect(Collectors.toList());
		showSeatRepository.saveAll(bookedShowSeats);
		booking.setStatus(BookingStatus.CONFIRMED);
		return bookingRepository.save(booking);
	}
//	cancel booking and release seats
	public Booking cancelBooking(Long bookingId) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found"));
		List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking_BookingId(bookingId);
		bookingSeats.forEach(bs->bs.getShowSeat().setStatus(SeatStatus.AVAILABLE));
		List<ShowSeat> bookedSeats = bookingSeats.stream().map(bs->bs.getShowSeat())
		.collect(Collectors.toList());
		showSeatRepository.saveAll(bookedSeats);
		booking.setStatus(BookingStatus.CANCELLED);
		return bookingRepository.save(booking);
	}
//	Get Booking Details
	public BookingResponse getBookingDetails(Long bookingId) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
		List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking_BookingId(bookingId);
		List<String>seats=new ArrayList<>();
		bookingSeats.forEach(bs->{seats.add(bs.getShowSeat().getSeat().getRowLabel()+"-"+bs.getShowSeat().getSeat().getSeatNo());
		});
		BookingResponse response=new BookingResponse();
		response.setBookingId(booking.getBookingId());
		response.setBookingRef(booking.getBookingRef());
		response.setMovieName(booking.getShow().getMovie().getMovieName());
		response.setShowDate(booking.getShow().getShowDate().toString());
		response.setStartTime(booking.getShow().getShowTime().toString());
		response.setBookingStatus(booking.getStatus().toString());
		response.setTheatreName(booking.getShow().getScreen().getTheatre().getTheatreName());
		response.setTotalAmount(booking.getTotalAmount());
		response.setSeats(seats);
		response.setSeatCount(seats.size());
		return response;
		
	}
//	get bookings done by user
	public List<Booking> getUserBookings(Long userId){
		return bookingRepository.findByUser_UserId(userId);
	}

}
