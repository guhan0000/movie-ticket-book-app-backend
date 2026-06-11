package com.movieBookV2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.model.Screen;
import com.movieBookV2.model.Seat;
import com.movieBookV2.model.SeatType;
import com.movieBookV2.repository.ScreenRepository;
import com.movieBookV2.repository.SeatRepository;

@Service
public class SeatService {
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private ScreenRepository screenRepository;
	
	public Seat addSeat(Long screenId,Seat seat) {
		Screen screen=screenRepository.findById(screenId).orElseThrow(()->new RuntimeException("Screen not Found"));
		seat.setScreen(screen);
		return seatRepository.save(seat);
		
	}
	public List<Seat> generateSeats(Long screenId,Integer rows,Integer seatsPerRow){
		Screen screen=screenRepository.findById(screenId).orElseThrow(()->new RuntimeException("Screen not Found"));
		if(rows*seatsPerRow>screen.getTotalSeats()) {
			throw new RuntimeException("total seats in "+screen.getScreenName()+" are "+screen.getTotalSeats());
		}
		List<Seat> seats= new ArrayList<>();
		List<String> rowLabel= Arrays.asList("A","B","C","D","E","F","G","H","I","J");
		for(Integer r=0;r<rows;r++) {
			for(Integer s=1;s<=seatsPerRow;s++) {
				Seat seat=new Seat();
				seat.setRowLabel(rowLabel.get(r));
				seat.setSeatNo(s);
				seat.setScreen(screen);
				seat.setSeatType((r>=rows-2)?SeatType.PREMIUM:SeatType.REGULAR);
				seats.add(seat);
			}
		}
		return seatRepository.saveAll(seats);
	}
	public List<Seat> getSeatsByScreen(Long screenId){
		return seatRepository.findByScreen_ScreenId(screenId);
	}
	public Integer getSeatsNo(Long screenId){
		return seatRepository.countByScreen_ScreenId(screenId);	
	}
}
