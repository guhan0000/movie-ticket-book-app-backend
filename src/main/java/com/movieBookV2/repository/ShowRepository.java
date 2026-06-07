package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long>{
	
	
}
