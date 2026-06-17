package com.movieBookV2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.dto.UserResponse;
import com.movieBookV2.model.User;
import com.movieBookV2.model.Wallet;
import com.movieBookV2.repository.UserRepository;
import com.movieBookV2.repository.WalletRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	
	public UserResponse registerUser(User user) {
		Wallet wallet=new Wallet();
		wallet.setBalance(0.0d);
		wallet.setUser(user);
		user.setWallet(wallet);
		User savedUser = userRepository.save(user);
		UserResponse response=new UserResponse();
		response.setCustName(savedUser.getCustName());
		response.setEmail(savedUser.getEmail());
		response.setUserId(savedUser.getUserId());
		response.setWalletBalance(savedUser.getWallet().getBalance());
		return response;
	}
	
	
}
