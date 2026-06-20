package com.movieBookV2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.Show;
import com.movieBookV2.model.ShowStatus;


@Repository
public interface ShowRepository extends JpaRepository<Show, Long>{

	List<Show> findByMovie_MovieIdAndShowDateBetween(Long movieId,LocalDate startDate, LocalDate endDate);
	List<Show> findByMovie_MovieIdAndShowDateBetweenAndStatus(Long movie_MovieId, LocalDate showDate,LocalDate endDate, ShowStatus status);
	List<Show> findByMovie_MovieIdAndShowDate(Long movieId,LocalDate showDate);
	List<Show> findByMovie_MovieIdAndShowDateAndStatus(Long movie_MovieId, LocalDate showDate, ShowStatus status);
}
