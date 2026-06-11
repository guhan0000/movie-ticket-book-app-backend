package com.movieBookV2.controller;

import com.movieBookV2.repository.ShowSeatRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.dto.ShowRequest;
import com.movieBookV2.model.Show;
import com.movieBookV2.model.ShowSeat;
import com.movieBookV2.service.ShowService;

@RestController
@RequestMapping("/api/mba/show")
public class ShowController {
	private final ShowSeatRepository showSeatRepository;
	@Autowired
	private ShowService showService;
	ShowController(ShowSeatRepository showSeatRepository) {
		this.showSeatRepository = showSeatRepository;
	}
	@PostMapping("/create")
	public List<Show> createShow(@RequestParam Long movieId, @RequestParam Long screenId, @RequestBody ShowRequest showRequest) {
		return showService.createShow(movieId, screenId, showRequest);
	}
	@GetMapping("/calender/{movieId}")
	public Map<LocalDate,List<Show>> getShowsForNext8Days(@PathVariable Long movieId){
		return showService.getShowsForNext8Days(movieId);
	}
	@GetMapping("/get/{movieId}/")
	public Map<String, List<Show>> getShowsForMovieWithTheatres(@PathVariable Long movieId,@RequestParam LocalDate showDate){
		return showService.getShowsForMovieWithTheatres(movieId, showDate);
	}
	@GetMapping("/seats/{showId}")
	public List<ShowSeat> getAvailableSeats(@PathVariable Long showId) {
		return showService.getAvailableShowSeats(showId);
	}
	@GetMapping("/seats/count/{showId}")
	public Integer getAvailableSeatCount(@PathVariable Long showId) {
		return showService.getAvailableSeatCount(showId);
	}
}
