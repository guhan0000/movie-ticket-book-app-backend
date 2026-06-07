package com.movieBookV2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.Screen;
import com.movieBookV2.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{
	List<Seat> findByScreen_ScreenId(Long screen_ScreenId);
	Integer countByScreen_ScreenId(Long screenId);

}
