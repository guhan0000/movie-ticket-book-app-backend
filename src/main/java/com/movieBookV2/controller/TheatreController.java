package com.movieBookV2.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.model.Theatre;
import com.movieBookV2.service.TheatreService;

@RestController
@RequestMapping("/api/mba/theatre")
public class TheatreController {
	
	@Autowired
	private TheatreService theatreService;
	
	@PostMapping("/add")
	public Theatre addTheatre(@RequestBody Theatre theatre) {
		return theatreService.addTheatre(theatre);
	}
	@GetMapping("/all")
	public List<Theatre> getAllTheatres(){
		return theatreService.getAllTheatres();
	}

}
