package com.movieBookV2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.ShowSeat;
import com.movieBookV2.model.SeatStatus;


@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
		List<ShowSeat> findByShow_ShowIdAndStatus(Long showId, SeatStatus status);
		Integer countByShow_ShowIdAndStatus(Long show_ShowId, SeatStatus status);
}
