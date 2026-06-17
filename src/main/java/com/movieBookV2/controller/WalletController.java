package com.movieBookV2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.model.Wallet;
import com.movieBookV2.service.WalletService;

@RestController
@RequestMapping("/api/mba/wallet")
public class WalletController {
		
	@Autowired
	private WalletService walletService;
	@PutMapping("/recharge/{userId}")
	public Wallet rechargeWallet(@PathVariable Long userId,@RequestParam Double amount) {
		return walletService.rechargeWallet(userId, amount);
	}
	
	
}
