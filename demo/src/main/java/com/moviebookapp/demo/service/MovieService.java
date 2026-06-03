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
//    bulk add
    public List<Movie> addMovies(List<Movie> movies){
        return movieRepo.saveAll(movies);
    }
//    delete movie
    public String deleteMovie(Long movieId){
        Movie movie= movieRepo.findById(movieId).orElseThrow(()->new RuntimeException("Movie Not Found"));
        movieRepo.deleteById(movieId);
        return "Movie Deleted Successfully";
    }
}
