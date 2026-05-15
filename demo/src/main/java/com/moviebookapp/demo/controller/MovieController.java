package com.moviebookapp.demo.controller;

import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mba/movie")
public class MovieController {
    @Autowired
    private MovieService service;
    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie){
       return service.addMovie(movie);
    }
    @GetMapping("/all")
    public List<Movie> getAllMovies(){
        return service.getAllMovies();
    }
    @GetMapping("/language")
    public List<Movie> getMovieByTicketLanguageOrderByTicket(@RequestParam String language){
        return service.getMovieByTicketLanguageOrderByTicket(language);
    }

}
