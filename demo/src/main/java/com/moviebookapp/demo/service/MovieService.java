package com.moviebookapp.demo.service;

import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

//    add movie to db
    public Movie addMovie(Movie movie){
        return movieRepo.save(movie);
    }
//    get all movies
    public List<Movie> getAllMovies(){
        return movieRepo.findAllByOrderByMovieNameAsc();
    }
//    ticketprice filter using language
}
