package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBookV2.model.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>{
	
}