package com.movieBookV2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.model.Theatre;
import com.movieBookV2.repository.TheatreRepository;

@Service
public class TheatreService {
	
	@Autowired
	private TheatreRepository theatreRepository;
	
	public Theatre addTheatre(Theatre theatre) {
		return theatreRepository.save(theatre);
	}
	public List<Theatre> getAllTheatres(){
		return theatreRepository.findAll();
	}

}
