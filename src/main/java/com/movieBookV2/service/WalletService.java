package com.movieBookV2.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.model.User;
import com.movieBookV2.model.Wallet;
import com.movieBookV2.model.WalletTransaction;
import com.movieBookV2.model.WalletTransactionType;
import com.movieBookV2.repository.UserRepository;
import com.movieBookV2.repository.WalletRepository;
import com.movieBookV2.repository.WalletTransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class WalletService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private WalletTransactionRepository walletTransactionRepository;
	
//	transaction ref generation
	protected String gemnerateTransactionRef() {
		return "W-TXN"+UUID.randomUUID()+System.currentTimeMillis();
	}
	
	@Transactional
	public Wallet rechargeWallet(Long userId, Double amount) {
		if(amount<=0) {
			throw new RuntimeException("amount should be greater than 0");
		}
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
//      Wallet wallet = user.getWallet();
//		Double balance = user.getWallet().getBalance();
		Wallet wallet=walletRepository.findById(user.getWallet().getWalletId()).orElseThrow(()->new RuntimeException("wallet not found"));
		wallet.setBalance(wallet.getBalance()+amount);
		WalletTransaction walletTransaction=new WalletTransaction();
		walletTransaction.setAmount(amount);
		walletTransaction.setTransactionRef(gemnerateTransactionRef());
		walletTransaction.setType(WalletTransactionType.CREDIT);
		walletTransaction.setTransactionTime(LocalDateTime.now());
		walletTransaction.setWallet(wallet);
		walletTransaction.setDescription("wallet recharged with Rs."+amount);
		walletTransactionRepository.save(walletTransaction);
		return walletRepository.save(wallet);
		
	}

}
