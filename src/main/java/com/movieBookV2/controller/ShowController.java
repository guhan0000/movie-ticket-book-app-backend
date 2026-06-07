package com.movieBookV2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.dto.ShowRequest;
import com.movieBookV2.model.Show;
import com.movieBookV2.service.ShowService;

@RestController
@RequestMapping("/api/mba/show")
public class ShowController {
	@Autowired
	private ShowService showService;
	@PostMapping("/create")
	public Show createShow(@RequestParam Long movieId, @RequestParam Long screenId, @RequestBody ShowRequest showRequest) {
		return showService.createShow(movieId, screenId, showRequest);
	}
}
