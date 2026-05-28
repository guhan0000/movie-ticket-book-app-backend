package com.moviebookapp.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long movieId;
    private String movieName;
    private String language;
    private Integer duration;
    private String posterUrl;
    private String genre;
    private String description;
    private LocalDate releaseDate;
    private Double rating;




}
