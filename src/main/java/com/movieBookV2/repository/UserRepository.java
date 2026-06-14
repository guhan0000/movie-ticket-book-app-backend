package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBookV2.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
