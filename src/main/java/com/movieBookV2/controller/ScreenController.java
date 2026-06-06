package com.movieBookV2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.model.Screen;
import com.movieBookV2.service.ScreenService;

@RestController
@RequestMapping("/api/mba/screen")
public class ScreenController {
	@Autowired
	private ScreenService screenService;
	@PostMapping("/add/{theatreId}")
	public Screen addScreen(@PathVariable Long theatreId,@RequestBody Screen screen) {
		return screenService.addScreen(theatreId, screen);
	}
	@GetMapping("/{theatreId}/all")
	public List<Screen> getScreensByTheatre(@PathVariable Long theatreId){
		return screenService.getScreensByTheatre(theatreId);
	}
}
