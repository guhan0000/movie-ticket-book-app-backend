package com.moviebookapp.demo.service;

import com.moviebookapp.demo.model.User;
import com.moviebookapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User register(User user){
        if(!(user.getRole().equals("ADMIN"))){
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
}
