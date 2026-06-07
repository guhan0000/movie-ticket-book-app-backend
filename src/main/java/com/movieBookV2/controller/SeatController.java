package com.movieBookV2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.movieBookV2.model.Seat;

import com.movieBookV2.service.SeatService;

@RestController
@RequestMapping("/api/mba/seat")
public class SeatController {
	@Autowired
	private SeatService seatService;
	
	@PostMapping("/add/{screenId}")
	public Seat addSeat(@PathVariable Long screenId, @RequestBody Seat seat ) {
		return seatService.addSeat(screenId, seat);
	}
	@PostMapping("/generate/{screenId}")
	public List<Seat> generateSeats(@PathVariable Long screenId,@RequestParam Integer rows,@RequestParam Integer seatsPerRow){
		return seatService.generateSeats(screenId, rows, seatsPerRow);
	}
	@GetMapping("/get/{screenId}/all")
	public List<Seat> getSeatsByScreen(@PathVariable Long screenId){
		return seatService.getSeatsByScreen(screenId);
	}
	@GetMapping("/count/{screenId}")
	public Integer getSeatCount(@PathVariable Long screenId) {
		return seatService.getSeatsNo(screenId);
	}
	

}
