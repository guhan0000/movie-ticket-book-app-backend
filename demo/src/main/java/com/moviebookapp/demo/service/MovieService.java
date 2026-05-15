package com.moviebookapp.demo.service;

import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

//    add movie to db
    public Movie addMovie(Movie movie){
        return repository.save(movie);
    }
//    get all movies
    public List<Movie> getAllMovies(){
        return repository.findAllByOrderByMovieNameAsc();
    }
//    ticketprice filter using language
    public List<Movie>getMovieByTicketLanguageOrderByTicket(String language){
        return repository.findAllByLanguageOrderByTicketPriceDesc(language);
    }
}
