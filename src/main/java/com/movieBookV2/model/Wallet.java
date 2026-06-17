package com.movieBookV2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "wallets")
public class Wallet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long walletId;
	@OneToOne
	@JoinColumn(name = "user_id",unique = true)
	@JsonManagedReference
	private User user;
	private Double balance;

}
