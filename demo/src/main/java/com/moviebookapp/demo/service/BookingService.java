package com.moviebookapp.demo.service;

import com.moviebookapp.demo.model.Booking;
import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.model.User;
import com.moviebookapp.demo.repository.BookingRepository;
import com.moviebookapp.demo.repository.MovieRepository;
import com.moviebookapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;

    public Map<Object,Object> bookTicket(Long movieId,Integer noOfTickets,Long userId){
//        Optional<Movie> movieOptional=movieRepository.findById(movieId);
//        if(!movieOptional.isPresent()) {
//            throw new RuntimeException("movie is not available");
//        }
//            Movie movie=movieOptional.get();
        Movie movie=movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found"));
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
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
                booking.setUser(user);

                booking.setTotalAmount(totalAmount);
                booking.setBookingCode(generateBookingCode());

                Map<Object,Object>map=new LinkedHashMap<>();
                map.put("customerName",user.getCustName());
                map.put("movieName",movie.getMovieName());
                map.put("noOfTickets",noOfTickets);
                map.put("totalAmount",totalAmount);
                bookingRepository.save(booking);
                movieRepository.save(movie);
                return  map;
    }
    public String cancelTicket(String bookingCode){
        Optional<Booking> bookingOptional=bookingRepository.findByBookingCode(bookingCode);
        if(bookingOptional.isPresent()){
            Booking booking=bookingOptional.get();
            Movie movie=movieRepository.findById(booking.getMovie().getMovieId()).orElseThrow(()->new RuntimeException("movie not found"));
            movie.setAvailableSeats(movie.getAvailableSeats()+booking.getNoOfTickets());
            bookingRepository.delete(booking);
            movieRepository.save(movie);
            return "Tickets Cancelled";
        }
        else{
            throw new RuntimeException("booking not available");
        }

    }
    private String generateBookingCode() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
