package com.movieBookV2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBookV2.model.Movie;
import com.movieBookV2.service.MovieService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
	    @PostMapping("/add-all")
	    public List<Movie> addMovieList(@RequestBody List<Movie> movies){
	    	return movieService.addMovieList(movies);
	    }
	    @DeleteMapping("/delete/{movieId}")
	    public String deleteMovie(@PathVariable Long movieId) {
	    	return movieService.deleteMovie(movieId);
	    }
}
