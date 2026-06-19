package com.movieBookV2.service;

import com.movieBookV2.repository.BookingSeatRepository;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Service;

import com.movieBookV2.dto.PaymentRequest;
import com.movieBookV2.dto.PaymentResponse;
import com.movieBookV2.model.Booking;
import com.movieBookV2.model.BookingSeat;
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
	private  BookingSeatRepository bookingSeatRepository;
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
	private static final double PER_TRANSACTION_LIMIT=3000.0;

	
	
	private String runPreChecks(Booking booking) {
		Wallet wallet = booking.getUser().getWallet();
		if(booking.getTotalAmount()>PER_TRANSACTION_LIMIT) {
			return "Transaction Limit Exceeds";
		}
		if(!booking.getStatus().equals(BookingStatus.PENDING)) {
			return "Booking is not in PENDING state";
		}
		if(booking.getTotalAmount()>wallet.getBalance() || wallet.getBalance()<=0) {
			return "Insufficent Balance";
		}
		return null;
	}
	@Transactional
	private Boolean simulatePayment(Long bookingId) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found"));
		if(runPreChecks(booking)!=null) {
			throw new RuntimeException(runPreChecks(booking));
		}
		Wallet wallet = booking.getUser().getWallet();
		wallet.setBalance(wallet.getBalance()-booking.getTotalAmount());
		WalletTransaction walletTransaction=new WalletTransaction();
		walletTransaction.setAmount(booking.getTotalAmount());
		walletTransaction.setTransactionRef(walletService.generateTransactionRef());
		walletTransaction.setTransactionTime(LocalDateTime.now());
		walletTransaction.setType(WalletTransactionType.DEBIT);
		walletTransaction.setWallet(wallet);
		walletRepository.save(wallet);
		walletTransactionRepository.save(walletTransaction);
		return true;
		
		
	}
	@Transactional
	public PaymentResponse processPayment(PaymentRequest request) {
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
			return buildPaymentResponse(booking, payment);
		}
		payment.setStatus(PaymentStatus.FAILED);
		bookingService.cancelBooking(request.getBookingId());
		paymentRepository.save(payment);
		return buildPaymentResponse(booking, payment);
		
	}
	
	@Transactional
	public PaymentResponse refundPayment(Long bookingId) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found"));
		Payment payment = paymentRepository.findByBooking_BookingId(bookingId).orElseThrow(()->new RuntimeException("Payment not found"));
		if(!payment.getStatus().equals(PaymentStatus.SUCCESS)) {
			throw new RuntimeException("Payment is not in SUCCESS state");
		}
		Payment refundPayment=new Payment();
		refundPayment.setStatus(PaymentStatus.REFUNDED);
		refundPayment.setAmount(payment.getAmount());
		refundPayment.setBooking(booking);
		refundPayment.setMethod(PaymentMethod.WALLET);
		refundPayment.setTransactionId("TXN-"+System.currentTimeMillis());
		refundPayment.setPaidAt(LocalDateTime.now());
		bookingService.cancelBooking(bookingId);
		
		Wallet wallet = booking.getUser().getWallet();
		Double refudAmount=payment.getAmount();
		wallet.setBalance(wallet.getBalance()+refudAmount);
		WalletTransaction walletTransaction=new WalletTransaction();
		walletTransaction.setAmount(refudAmount);
		walletTransaction.setDescription("wallet refunded");
		walletTransaction.setTransactionRef(walletService.generateTransactionRef());
		walletTransaction.setTransactionTime(LocalDateTime.now());
		walletTransaction.setType(WalletTransactionType.REFUND);
		walletTransaction.setWallet(wallet);
		paymentRepository.save(refundPayment);
		walletRepository.save(wallet);
		walletTransactionRepository.save(walletTransaction);
		return buildPaymentResponse(booking, refundPayment);
		
	}
	private PaymentResponse buildPaymentResponse(Booking booking, Payment payment) {
		
		List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking_BookingId(booking.getBookingId());
		List<String> seatWithLabels = bookingSeats.stream().map(bs->bs.getShowSeat().getSeat().getRowLabel()+"-"+bs.getShowSeat().getSeat().getSeatNo())
		.collect(Collectors.toList());
		PaymentResponse response=new PaymentResponse();
		response.setBookingRef(booking.getBookingRef());
		response.setBookingStatus(booking.getStatus());
		response.setMovieName(booking.getShow().getMovie().getMovieName());
		response.setPaidAt(payment.getPaidAt());
		response.setPaymentStatus(payment.getStatus());
		response.setSeatCount(bookingSeats.size());
		response.setSeats(seatWithLabels);
		response.setShowDate(booking.getShow().getShowDate().toString());
		response.setStartTime(booking.getShow().getShowTime().toString());
		response.setTheatreName(booking.getShow().getScreen().getTheatre().getTheatreName());
		response.setTotalAmount(payment.getAmount());
		response.setTransactionId(payment.getTransactionId());
		return response;
	}
	public PaymentResponse getPaymentDetails(Long bookingId) {
		Payment payment=paymentRepository.findByBooking_BookingId(bookingId).orElseThrow(()->new RuntimeException("payment not available"));
		return buildPaymentResponse(payment.getBooking(), payment);
		}
}
