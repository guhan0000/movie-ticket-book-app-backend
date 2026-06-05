package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.ShowSeat;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

}
