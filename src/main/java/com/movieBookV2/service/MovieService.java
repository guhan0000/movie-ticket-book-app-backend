package com.movieBookV2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBookV2.model.Movie;
import com.movieBookV2.repository.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepo;
	
	public Movie addMovie(Movie movie){
        return movieRepo.save(movie);
    }
	public List<Movie> addMovies(List<Movie> movies){
        return movieRepo.saveAll(movies);
    }
	public List<Movie> getAllMovies(){
        return movieRepo.findAll();
    }
}
