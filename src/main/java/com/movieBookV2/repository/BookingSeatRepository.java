package com.movieBookV2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.BookingSeat;

@Repository
public interface BookingSeatRepository  extends JpaRepository<BookingSeat, Long>{
	List<BookingSeat> findByBooking_BookingId(Long booking_BookingId);
}
