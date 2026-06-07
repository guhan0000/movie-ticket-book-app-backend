package com.movieBookV2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.model.Screen;
import com.movieBookV2.model.Theatre;
import com.movieBookV2.repository.ScreenRepository;
import com.movieBookV2.repository.TheatreRepository;

@Service
public class ScreenService {
	@Autowired
	private ScreenRepository screenRepository;
	@Autowired
	private TheatreRepository theatreRepository;
	
	public Screen addScreen(Long theatreId, Screen screen) {
		Theatre theatre=theatreRepository.findById(theatreId).orElseThrow(()->new RuntimeException("Theatre not found"));
		screen.setTheatre(theatre);
		return screenRepository.save(screen);		
	}
	public List<Screen> getScreensByTheatre(Long theatreId){
		return screenRepository.findByTheatre_TheatreId(theatreId);
	}

}
