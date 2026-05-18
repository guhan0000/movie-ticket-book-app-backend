package com.moviebookapp.demo.service;

import com.moviebookapp.demo.model.Booking;
import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.repository.BookingRepository;
import com.moviebookapp.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    MovieRepository movieRepository;

    public Map<Object,Object> bookTicket(String custName, Long movieId, Integer noOfTickets){
//        Optional<Movie> movieOptional=movieRepository.findById(movieId);
//        if(!movieOptional.isPresent()) {
//            throw new RuntimeException("movie is not available");
//        }
//            Movie movie=movieOptional.get();
        Movie movie=movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));

            if(movie.getAvailableSeats()<=0){
                throw new RuntimeException("No seats available");
            }
            if(movie.getAvailableSeats()<noOfTickets){
                throw new RuntimeException("No of seats availabnle: "+movie.getAvailableSeats());
            }
                movie.setAvailableSeats(movie.getAvailableSeats()-noOfTickets);
                Double totalAmount= (double) (noOfTickets*movie.getTicketPrice());
                Booking booking=new Booking();
                booking.setMovie(movie);
                booking.setNoOfTickets(noOfTickets);
                booking.setCustomerName(custName);
                booking.setTotalAmount(totalAmount);

                Map<Object,Object>map=new LinkedHashMap<>();
                map.put("customerName",custName);
                map.put("movieName",movie.getMovieName());
                map.put("noOfTickets",noOfTickets);
                map.put("totalAmount",totalAmount);
                bookingRepository.save(booking);
                movieRepository.save(movie);
                return  map;
    }

}
