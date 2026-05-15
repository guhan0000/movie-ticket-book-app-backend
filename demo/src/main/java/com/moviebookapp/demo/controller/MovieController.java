package com.moviebookapp.demo.controller;

import com.moviebookapp.demo.model.Movie;
import com.moviebookapp.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mba/movie")
public class MovieController {
    @Autowired
    private MovieService service;
    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie){
       return service.addMovie(movie);
    }

}
