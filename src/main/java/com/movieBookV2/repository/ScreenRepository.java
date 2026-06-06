package com.movieBookV2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.Screen;
import com.movieBookV2.model.Theatre;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
	List<Screen> findByTheatre_TheatreId(Long theatreId);

}
