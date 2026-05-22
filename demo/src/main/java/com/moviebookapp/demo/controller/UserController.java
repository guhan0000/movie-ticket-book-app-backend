package com.moviebookapp.demo.controller;

import com.moviebookapp.demo.model.User;
import com.moviebookapp.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mba/users")

public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public User register(@RequestBody  User user){
        return userService.register(user);
    }
}
