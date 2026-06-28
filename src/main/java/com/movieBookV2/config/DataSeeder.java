package com.movieBookV2.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.movieBookV2.model.Role;
import com.movieBookV2.model.User;
import com.movieBookV2.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	public CommandLineRunner seedData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args->{
			if(!userRepository.findByEmail("admin@test.com").isPresent()) {
				User admin=new User();
				admin.setCustName("Admin");
				admin.setEmail("admin@test.com");
				admin.setPassword(passwordEncoder.encode("admin"));
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);
				System.out.println("Admin seeded at admin@test.com / admin");
			}
				
			
		};
	}

}
