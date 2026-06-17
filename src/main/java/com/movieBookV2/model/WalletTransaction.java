package com.movieBookV2.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "wallet_transactions")
public class WalletTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long walletTransactionId;
	@Column(name = "transaction_ref", unique = true)
	private String transactionRef;
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;
	private Double amount;
	@Enumerated(EnumType.STRING)
	private WalletTransactionType type;
	private String description;
	private LocalDateTime transactionTime;

}
