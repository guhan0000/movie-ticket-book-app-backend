package com.moviebookapp.demo.controller;

import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mba/movie")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie){
       return movieService.addMovie(movie);
    }
    @GetMapping("/all")
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }
    @GetMapping("/language")
    public List<Movie> getMovieByTicketLanguageOrderByTicket(@RequestParam String language){
        return movieService.getMovieByTicketLanguageOrderByTicket(language);
    }

}
