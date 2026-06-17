package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.Payment;
import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	Optional<Payment> findByTransactionId(String transactionId);
	Optional<Payment> findByBooking_BookingId(Long booking_BookingId);

}
