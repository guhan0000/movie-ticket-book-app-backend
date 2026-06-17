package com.movieBookV2.service;

import java.time.LocalDateTime;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.dto.PaymentRequest;
import com.movieBookV2.model.Booking;
import com.movieBookV2.model.BookingStatus;
import com.movieBookV2.model.Payment;
import com.movieBookV2.model.PaymentMethod;
import com.movieBookV2.model.PaymentStatus;
import com.movieBookV2.model.Wallet;
import com.movieBookV2.model.WalletTransaction;
import com.movieBookV2.model.WalletTransactionType;
import com.movieBookV2.repository.BookingRepository;
import com.movieBookV2.repository.PaymentRepository;
import com.movieBookV2.repository.WalletRepository;
import com.movieBookV2.repository.WalletTransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private WalletTransactionRepository walletTransactionRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private WalletService walletService;
	@Autowired
	private BookingService bookingService;
	
	@Transactional
	private Boolean simulatePayment(Long bookingId) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found"));
		Wallet wallet = booking.getUser().getWallet();
		if(booking.getTotalAmount()>wallet.getBalance() || wallet.getBalance()<=0) {
			throw new RuntimeException("Insufficent Balance");
		}
		wallet.setBalance(wallet.getBalance()-booking.getTotalAmount());
		WalletTransaction walletTransaction=new WalletTransaction();
		walletTransaction.setAmount(booking.getTotalAmount());
		walletTransaction.setTransactionRef(walletService.gemnerateTransactionRef());
		walletTransaction.setTransactionTime(LocalDateTime.now());
		walletTransaction.setType(WalletTransactionType.DEBIT);
		walletTransaction.setWallet(wallet);
		walletRepository.save(wallet);
		walletTransactionRepository.save(walletTransaction);
		return true;
		
		
	}
	@Transactional
	public String processPayment(PaymentRequest request) {
		Booking booking=bookingRepository.findById(request.getBookingId()).orElseThrow(()->new RuntimeException("booking not found"));
		if(!booking.getStatus().equals(BookingStatus.PENDING)) {
			throw new RuntimeException("Booking is not in a PENDING state");
		}
		Payment payment = new Payment();
		payment.setBooking(booking);
		payment.setAmount(booking.getTotalAmount());
		payment.setMethod(PaymentMethod.WALLET);
		payment.setTransactionId(request.getTransactionId()!=null
				?request.getTransactionId()
				:"TXN-"+System.currentTimeMillis());
		payment.setPaidAt(LocalDateTime.now());
		Boolean isPaymentSuccess = simulatePayment(request.getBookingId());
		if(isPaymentSuccess) {
			payment.setStatus(PaymentStatus.SUCCESS);
			bookingService.confirmBooking(request.getBookingId());
			booking.setStatus(BookingStatus.CONFIRMED);
			paymentRepository.save(payment);
			return "Payment Success";
		}
		
		return "Payment Failed";
		
	}

}
