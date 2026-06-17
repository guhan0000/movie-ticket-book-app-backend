package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBookV2.model.Wallet;
import java.util.List;
import java.util.Optional;


public interface WalletRepository extends JpaRepository<Wallet, Long> {
	 Optional<Wallet>findByUser_UserId(Long userId);
}
