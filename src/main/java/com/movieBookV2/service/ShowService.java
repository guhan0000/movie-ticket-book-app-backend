package com.movieBookV2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.dto.ShowRequest;
import com.movieBookV2.model.Movie;
import com.movieBookV2.model.Screen;
import com.movieBookV2.model.Seat;
import com.movieBookV2.model.SeatStatus;
import com.movieBookV2.model.SeatType;
import com.movieBookV2.model.Show;
import com.movieBookV2.model.ShowSeat;
import com.movieBookV2.model.ShowStatus;
import com.movieBookV2.repository.MovieRepository;
import com.movieBookV2.repository.ScreenRepository;
import com.movieBookV2.repository.SeatRepository;
import com.movieBookV2.repository.ShowRepository;
import com.movieBookV2.repository.ShowSeatRepository;

import jakarta.transaction.Transactional;

@Service
public class ShowService {
	@Autowired
	private	MovieRepository movieRepository;
	@Autowired
	private ScreenRepository screenRepository;
	@Autowired
	private ShowRepository showRepository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private ShowSeatRepository showSeatRepository;
	
//	show creation and 
	@Transactional
	public Show createShow(Long movieId,Long screenId,ShowRequest showRequest) {
		Movie movie=movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));
		Screen screen = screenRepository.findById(screenId).orElseThrow(()-> new RuntimeException("Screen not found"));
		Show show=new Show();
		show.setMovie(movie);
		show.setScreen(screen);
		show.setFormat(showRequest.getFormat());
		show.setLanguage(showRequest.getLanguage());
		show.setShowDate(showRequest.getShowDate());
		show.setShowTime(showRequest.getShowTime());
		show.setStatus(ShowStatus.ACTIVE);
		Show savedShow = showRepository.save(show);
		
//		auto generation of ShowSeat
		List<Seat> seats = seatRepository.findByScreen_ScreenId(screenId);
		List<ShowSeat>showSeats=new ArrayList<>();
		for(Seat seat:seats) {
			ShowSeat showSeat=new ShowSeat();
			showSeat.setSeat(seat);
			showSeat.setShow(savedShow);
//			if(seat.getSeatType().equals(SeatType.PREMIUM)) {
//				showSeat.setPrice(300d);
//			}
//			else {
//				showSeat.setPrice(120d);
//			}
			Double price=(seat.getSeatType().equals(SeatType.PREMIUM))?(300d):(120d);
			showSeat.setPrice(price);
			showSeat.setStatus(SeatStatus.AVAILABLE);
			showSeats.add(showSeat);
			showSeatRepository.saveAll(showSeats);
			
		}
		return savedShow;
	}

}
