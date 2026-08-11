package com.movieBookV2.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	private void createShowSeat(Long screenId,Show show) {
		List<Seat> seats = seatRepository.findByScreen_ScreenId(screenId);
		List<ShowSeat>showSeats=new ArrayList<>();
		for(Seat seat:seats) {
			ShowSeat showSeat=new ShowSeat();
			showSeat.setSeat(seat);
			showSeat.setShow(show);
			Double price=(seat.getSeatType().equals(SeatType.PREMIUM))?(300d):(120d);
			showSeat.setPrice(price);
			showSeat.setStatus(SeatStatus.AVAILABLE);
			showSeats.add(showSeat);
			
		}
		showSeatRepository.saveAll(showSeats);
	}
//	show creation 
	@Transactional
	public List<Show> createShow(Long movieId,Long screenId,ShowRequest showRequest) {
		Movie movie=movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));
		Screen screen = screenRepository.findById(screenId).orElseThrow(()-> new RuntimeException("Screen not found"));
		LocalDate currentDate=showRequest.getShowDate();
		List<Show> shows=new ArrayList<>();
		while(!currentDate.isAfter(showRequest.getShowEndDate())) {
			Show show=new Show();
			show.setMovie(movie);
			show.setScreen(screen);
			show.setFormat(showRequest.getFormat());
			show.setLanguage(showRequest.getLanguage());
			show.setShowDate(currentDate);
			show.setShowTime(showRequest.getShowTime());
			show.setStatus(ShowStatus.ACTIVE);
			// persist each show so that showSeats can reference it
			Show savedShow = showRepository.save(show);
			createShowSeat(screenId, savedShow);
			shows.add(savedShow);
			currentDate=currentDate.plusDays(1); 		}
		// return the list of created shows (already saved)
		return shows;
	}
//	get 8 days calender for shows
		public Map<LocalDate, List<Show>> getShowsForNext8Days(Long movieId){
			LocalDate today=LocalDate.now();
			LocalDate after8Days=today.plusDays(7);
			LocalTime currentTime=LocalTime.now();
			List<Show> showsFor8Days = showRepository.findByMovie_MovieIdAndShowDateBetweenAndStatus(movieId,today, after8Days, ShowStatus.ACTIVE);
			// Cancel shows that are already in the past. For shows on today's date, cancel if show time is <= now.
			showsFor8Days.stream()
			.forEach(s->{
				if (s.getShowDate().isBefore(today) || (s.getShowDate().isEqual(today) && !currentTime.isBefore(s.getShowTime()))) {
						s.setStatus(ShowStatus.CANCELLED);
						showRepository.save(s);
				}
			});
			return showsFor8Days.stream()
					.filter(s->s.getStatus().equals(ShowStatus.ACTIVE))
					.collect(Collectors.groupingBy(Show::getShowDate));		
			}
		
//	Get theatres with shows on a date
		public Map<String, List<Show>> getShowsForMovieWithTheatres(Long movieId,LocalDate showDate){
			LocalDate today = LocalDate.now();
			LocalTime now = LocalTime.now();
			List<Show> shows = showRepository.findByMovie_MovieIdAndShowDateAndStatus(movieId, showDate,ShowStatus.ACTIVE);
			shows.stream()
					.forEach(s->{
						// cancel if the show date is before today, or if it's today and the show time has already passed
						if (s.getShowDate().isBefore(today) || (s.getShowDate().isEqual(today) && !now.isBefore(s.getShowTime()))) {
								s.setStatus(ShowStatus.CANCELLED);
								showRepository.save(s);
						}
						});
			return	shows.stream()
		 	.collect(Collectors.groupingBy(s->s.getScreen().getTheatre().getTheatreName()));	
		}
//	GET Available seats
		public List<ShowSeat> getAvailableShowSeats(Long showId){
			return showSeatRepository.findByShow_ShowIdAndStatus(showId,SeatStatus.AVAILABLE);
			
		}
//	Get Available seatCount
		public Integer getAvailableSeatCount(Long showId) {
			return showSeatRepository.countByShow_ShowIdAndStatus(showId,SeatStatus.AVAILABLE);
		}

}
