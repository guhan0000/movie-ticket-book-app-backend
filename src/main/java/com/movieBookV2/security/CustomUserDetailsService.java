package com.movieBookV2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movieBookV2.model.User;
import com.movieBookV2.repository.UserRepository;

//CustomUserDetailsService.java
@Service
public class CustomUserDetailsService implements UserDetailsService {

 @Autowired
 private UserRepository userRepo;

 @Override
 public UserDetails loadUserByUsername(String email) {
     User user = userRepo.findByEmail(email)
             .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));
     return new CustomUserDetails(user);
 }
}
