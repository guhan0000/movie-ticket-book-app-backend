package com.movieBookV2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.model.Movie;
import com.movieBookV2.service.MovieService;

@RestController
@RequestMapping("/api/mba/movie")
public class MovieController {
		
	 @Autowired
	    private MovieService movieService;
	    @PostMapping("/add")
	    public Movie addMovie(@RequestBody Movie movie){
//	    	System.out.println(movie.getMovieName());
	       return movieService.addMovie(movie);
	    }
	    @GetMapping("/all")
	    public List<Movie> getAllMovies(){
	        return movieService.getAllMovies();
	    }
}
